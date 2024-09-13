package me.jorgetoh.dynamiclang;

import me.jorgetoh.dynamiclang.listeners.PlayerLocaleListener;
import me.jorgetoh.dynamiclang.managers.ItemStackRenamer;
import me.jorgetoh.dynamiclang.util.LangEquivalences;
import me.jorgetoh.dynamiclang.util.RegisteredPlugins;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public final class DynamicLang extends JavaPlugin {


    private String defaultLang;
    public static HashMap<UUID, String> hPlayers = new HashMap<>();


    private RegisteredPlugins registeredPlugins;
    private ItemStackRenamer itemStackRenamer;
    private LangEquivalences langEquivalences;


    @Override
    public void onEnable() {
        loadPluginConfig();

        langEquivalences = new LangEquivalences(this);
        registeredPlugins = new RegisteredPlugins(this);
        itemStackRenamer = new ItemStackRenamer(this);
        if (getConfig().getBoolean("item-rename")) {
            itemStackRenamer.initializeItemRenamer();
        }

        new PlayerLocaleListener(this);
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

    public LangEquivalences getLangEquivalences() {
        return langEquivalences;
    }
}
