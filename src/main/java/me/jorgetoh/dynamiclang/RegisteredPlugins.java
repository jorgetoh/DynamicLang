package me.jorgetoh.dynamiclang;

import me.jorgetoh.dynamiclang.util.RegisteredPlugin;

import java.util.HashMap;

public class RegisteredPlugins {

    private final Main plugin;
    private final HashMap<String, RegisteredPlugin> registeredPluginsMap;

    public RegisteredPlugins(Main plugin) {
        this.plugin = plugin;
        registeredPluginsMap = new HashMap<>();
    }

}
