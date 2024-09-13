package me.jorgetoh.dynamiclang;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private RegisteredPlugins registeredPlugins;

    @Override
    public void onEnable() {
        registeredPlugins = new RegisteredPlugins(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
