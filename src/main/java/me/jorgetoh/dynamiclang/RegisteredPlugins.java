package me.jorgetoh.dynamiclang;

import me.jorgetoh.dynamiclang.util.RegisteredPlugin;

import java.util.HashMap;

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

}
