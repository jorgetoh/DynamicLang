package me.jorgetoh.dynamiclang.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HFiles {

    private final JavaPlugin plugin;
    private final HashMap<String, HFiler> configFiles;
    private final String[] configNames;

    public HFiles(JavaPlugin plugin, String... fileNames) {
        this.plugin = plugin;
        configFiles = new HashMap<>();
        this.configNames = fileNames;

        plugin.getLogger().info("Trying to load "+fileNames.length+" config file(s)...");
    }

    public void load() {
        for (String name : configNames) {
            if (loadFile(name)) {
                plugin.getLogger().info("The file "+name+".yml was loaded successfully.");
            } else {
                plugin.getLogger().info("Something went wrong loading "+name+".yml");
            }
        }
    }


    private boolean loadFile(String name) {
        File configFile = new File(plugin.getDataFolder(), name+".yml");

        if(!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource(name+".yml", false);
        }

        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            return false;
        }
        HFiler hFiler = new HFiler(configFile, config);
        configFiles.put(name, hFiler);
        return true;
    }

    public void reloadFiles() {
        for (String name : configFiles.keySet()) {
            HFiler hFiler = configFiles.get(name);
            YamlConfiguration config = new YamlConfiguration();
            try {
                config.load(hFiler.getFile());
            } catch (IOException | InvalidConfigurationException e) {
                plugin.getLogger().info("Something went wrong reloading "+name+".yml");
            }
            hFiler.setConfig(config);
        }
    }

    public void reloadFile(String fileName) {
        HFiler hFiler = configFiles.get(fileName);
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(hFiler.getFile());
        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().info("Something went wrong reloading "+fileName+".yml");
        }
        hFiler.setConfig(config);
    }

    public String getString(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a string from a file that is not loaded. ("+fileName+".yml)");
            return null;
        }
        String lineString = hFiler.getConfig().getString(line);
        if (lineString == null) {
            plugin.getLogger().info("Can't retrieve the string '"+line+"' from "+fileName+".yml");
            return null;
        }
        return lineString;
    }


    public boolean getBoolean(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a string from a file that is not loaded. ("+fileName+".yml)");
            return false;
        }
        return hFiler.getConfig().getBoolean(line);
    }

    public double getDouble(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a string from a file that is not loaded. ("+fileName+".yml)");
            return 0;
        }
        return hFiler.getConfig().getDouble(line);
    }

    public boolean contains(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a string from a file that is not loaded. ("+fileName+".yml)");
            return false;
        }
        return hFiler.getConfig().contains(line);
    }

    public int getInt(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a int from a file that is not loaded. ("+fileName+".yml)");
            return 0;
        }
        return hFiler.getConfig().getInt(line);
    }
    public List<String> getStringList(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a List<String> from a file that is not loaded. ("+fileName+".yml)");
            return null;
        }
        return hFiler.getConfig().getStringList(line);
    }

    public ItemStack getItemStack(String fileName, String line) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a List<String> from a file that is not loaded. ("+fileName+".yml)");
            return null;
        }
        return hFiler.getConfig().getItemStack(line);
    }
    public Set<String> getSections(String fileName, String section) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a Set<String> from a file that is not loaded. ("+fileName+".yml)");
            return null;
        }
        return hFiler.getConfig().getConfigurationSection(section).getKeys(false);
    }

    public Location getLocation(String fileName, String line) {
        String[] value = getString(fileName, line).split(",");

        World world = Bukkit.getWorld(value[0]);
        double x = Double.parseDouble(value[1]);
        double y = Double.parseDouble(value[2]);
        double z = Double.parseDouble(value[3]);
        double yaw = Double.parseDouble(value[4]);
        double pitch = Double.parseDouble(value[5]);

        return new Location(world, x, y, z, (float) yaw, (float) pitch);
    }
    public FileConfiguration getConfig(String fileName) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a List<String> from a file that is not loaded. ("+fileName+".yml)");
            return null;
        }
        return hFiler.getConfig();
    }
    public File getFile(String fileName) {
        HFiler hFiler = configFiles.get(fileName);
        if (hFiler == null) {
            plugin.getLogger().info("Can't retrieve a List<String> from a file that is not loaded. ("+fileName+".yml)");
            return null;
        }
        return hFiler.getFile();
    }
    private class HFiler {
        private final File file;
        private FileConfiguration config;

        public HFiler(File file, FileConfiguration config) {
            this.file = file;
            this.config = config;
        }

        public File getFile() {
            return file;
        }

        public FileConfiguration getConfig() {
            return config;
        }

        public void setConfig(FileConfiguration config) {
            this.config = config;
        }
    }

}
