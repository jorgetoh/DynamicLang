package me.jorgetoh.dynamiclang.managers;

import me.jorgetoh.dynamiclang.DynamicLang;
import me.jorgetoh.dynamiclang.util.HFile;
import me.jorgetoh.dynamiclang.util.RegisteredPlugin;
import org.bukkit.entity.Player;

public class Messenger {
    private final DynamicLang plugin;
    public Messenger(DynamicLang plugin) {
        this.plugin = plugin;
    }


    private String getMessage(Player player, String pluginName, String messageKey) {
        RegisteredPlugin registeredPlugin = plugin.getRegisteredPlugins().getRegisteredPluginsMap().get(pluginName);

        if (registeredPlugin == null) {
            plugin.getLogger().info("Tried to send a message but '"+pluginName+"' is not registered. key:" + messageKey);
            return null;
        }

        String playerLang = DynamicLang.hPlayers.get(player.getUniqueId());
        HFile langFile = registeredPlugin.getLangFiles().get(playerLang);
        if (langFile == null) {
            playerLang = plugin.getLangEquivalences().getPlayerLangEquivalence(player.getUniqueId());
            if (playerLang == null) {
                playerLang = plugin.getDefaultLang();
            }
            langFile = registeredPlugin.getLangFiles().get(playerLang);
        }

        if (langFile == null) {
            plugin.getLogger().info("Could not find the lang file for the language '"+langFile+"' plugin: '"+pluginName+"' . key:" + messageKey);
            return null;
        }

        return langFile.getConfig().getString("messages."+messageKey);
    }
}
