package me.jorgetoh.dynamiclang.util;

import me.jorgetoh.dynamiclang.DynamicLang;
import me.jorgetoh.dynamiclang.util.RegisteredPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RegisteredPlugins {

    private final DynamicLang plugin;
    private final HashMap<String, RegisteredPlugin> registeredPluginsMap;

    public RegisteredPlugins(DynamicLang plugin) {
        this.plugin = plugin;
        registeredPluginsMap = new HashMap<>();
    }


    public void registerPlugin(String pluginName) {
        try {
            RegisteredPlugin registeredPlugin = new RegisteredPlugin(plugin, pluginName);
            registeredPluginsMap.put(pluginName, registeredPlugin);
        } catch (Exception exception) {
            plugin.getLogger().info(exception.getMessage());
        }
    }

    public void reloadRegisteredPlugin(String pluginName) {
        if (!registeredPluginsMap.containsKey(pluginName)) {
            plugin.getLogger().info("Can not reload this plugin. The plugin '"+pluginName+"' is not yet registered.");
            return;
        }

        registeredPluginsMap.remove(pluginName);
        registerPlugin(pluginName);
    }

    public void reloadAllRegisteredPlugins() {
        Set<String> pluginNames = new HashSet<>(registeredPluginsMap.keySet());
        registeredPluginsMap.clear();
        pluginNames.forEach(this::registerPlugin);
    }

    public HashMap<String, RegisteredPlugin> getRegisteredPluginsMap() {
        return registeredPluginsMap;
    }
}
