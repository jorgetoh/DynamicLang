package me.jorgetoh.dynamiclang.util;

import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class RegisteredPlugin {

    private final DynamicLang plugin;
    private final String pluginName;
    private final HashSet<String> itemKeys;
    private final HashMap<String, HFile> langFiles;


    public RegisteredPlugin(DynamicLang plugin, String pluginName) throws Exception {
        this.plugin = plugin;
        this.pluginName = pluginName;
        itemKeys = new HashSet<>();
        langFiles = new HashMap<>();

        loadFiles();
        if (langFiles.isEmpty()) {
            throw new Exception("Something went wrong registering a plugin.");
        }
        if (!langFiles.containsKey(plugin.getDefaultLang())) {
            throw new Exception("The plugin is missing the default language. '"+plugin.getDefaultLang()+"'");
        }
    }

    public HFile getDefaultLang() {
        return langFiles.get(plugin.getDefaultLang());
    }

    public void loadFiles() {
        Plugin registered = plugin.getServer().getPluginManager().getPlugin(pluginName);
        if (registered == null) {
            plugin.getLogger().info("The plugin '"+pluginName+"' is not registered properly.");
            return;
        }

        File registeredFolder = registered.getDataFolder();
        File langFolder = new File(registeredFolder, "lang");
        if (!langFolder.exists()) {
            plugin.getLogger().info("The lang folder for the plugin '"+pluginName+"' does not exist.");
            return;
        }
        if (!langFolder.isDirectory()) {
            plugin.getLogger().info("The lang file '"+langFolder.getName()+"' is not a directory.");
            return;
        }

        File[] files = langFolder.listFiles();
        if (files == null) {
            plugin.getLogger().info("The lang folder for the plugin '"+pluginName+"' is empty.");
            return;
        }

        for (File file : files) {
            if (!file.isFile()) {
                plugin.getLogger().info("Something went wrong loading the lang file '"+file.getName()+"' for the plugin '"+pluginName+"'.");
                continue;
            }

            YamlConfiguration config = new YamlConfiguration();
            try {
                config.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                plugin.getLogger().info("IOException | InvalidConfigurationException lang file: '"+file.getName()+"' plugin: '"+pluginName+"'.");
                continue;
            }
            HFile hFile = new HFile(file, config);
            String fileNameNoExtension = file.getName().substring(0, file.getName().lastIndexOf('.'));
            plugin.getLogger().info("Registered file: " + fileNameNoExtension);
            langFiles.put(fileNameNoExtension, hFile);
        }
    }

    public HashMap<String, HFile> getLangFiles() {
        return langFiles;
    }
}
