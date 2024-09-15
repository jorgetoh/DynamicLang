package me.jorgetoh.dynamiclang.util;

import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.entity.Player;

import java.io.File;

public class FileUtil {

    private final DynamicLang plugin;
    public FileUtil(DynamicLang plugin) {
        this.plugin = plugin;
    }

    public HFile getPlayerLangFile(Player player, String pluginName) {
        RegisteredPlugin registeredPlugin = plugin.getRegisteredPlugins().getRegisteredPluginsMap().get(pluginName);

        if (registeredPlugin == null) {
            plugin.getLogger().info("Tried to get a message but '" + pluginName + "' is not registered.");
            return null;
        }

        String playerLang = player.getLocale().toLowerCase();
        HFile langFile = registeredPlugin.getLangFiles().get(playerLang);
        if (langFile == null) {
            playerLang = plugin.getLangEquivalences().getLangEquivalence(playerLang);
            if (playerLang == null) {
                playerLang = plugin.getDefaultLang();
            }
            langFile = registeredPlugin.getLangFiles().get(playerLang);
        }

        if (langFile == null) {
            plugin.getLogger().info("Could not find the lang file for the language '" + playerLang + "' plugin: '" + pluginName + "'.");
        }

        return langFile;
    }

}
