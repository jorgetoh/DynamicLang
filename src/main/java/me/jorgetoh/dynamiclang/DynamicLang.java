package me.jorgetoh.dynamiclang;

import me.jorgetoh.dynamiclang.util.RegisteredPlugins;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicLang extends JavaPlugin {

    private RegisteredPlugins registeredPlugins;
    private String defaultLang;

    @Override
    public void onEnable() {
        registeredPlugins = new RegisteredPlugins(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadPluginConfig() {
        saveDefaultConfig();

        defaultLang = getConfig().getString("default-lang");
    }

    public RegisteredPlugins getRegisteredPlugins() {
        return registeredPlugins;
    }

    public String getDefaultLang() {
        return defaultLang;
    }
}
