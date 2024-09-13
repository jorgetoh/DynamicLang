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
    private final String name;
    private final HashSet<String> itemKeys;
    private final HashMap<String, HFile> langFiles;


    public RegisteredPlugin(DynamicLang plugin, String pluginName) throws Exception {
        this.plugin = plugin;
        this.name = pluginName;
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
        Plugin registered = plugin.getServer().getPluginManager().getPlugin(name);
        if (registered == null) {
            plugin.getLogger().info("The plugin '"+name+"' is not registered properly.");
            return;
        }

        File registeredFolder = registered.getDataFolder();
        File langFolder = new File(registeredFolder, "lang");
        if (!langFolder.exists()) {
            plugin.getLogger().info("The lang folder for the plugin '"+name+"' does not exist.");
            return;
        }
        if (!langFolder.isDirectory()) {
            plugin.getLogger().info("The lang file '"+name+"' is not a directory.");
            return;
        }

        File[] files = langFolder.listFiles();
        if (files == null) {
            plugin.getLogger().info("The lang folder for the plugin '"+name+"' is empty.");
            return;
        }

        for (File file : files) {
            if (!file.isFile()) {
                plugin.getLogger().info("Something went wrong loading the lang file '"+file.getName()+"' for the plugin '"+name+"'.");
                continue;
            }

            YamlConfiguration config = new YamlConfiguration();
            try {
                config.load(file);
            } catch (IOException | InvalidConfigurationException e) {
                plugin.getLogger().info("IOException | InvalidConfigurationException lang file: '"+file.getName()+"' plugin: '"+name+"'.");
                continue;
            }
            HFile hFile = new HFile(file, config);
            langFiles.put(name, hFile);
        }
    }

}
