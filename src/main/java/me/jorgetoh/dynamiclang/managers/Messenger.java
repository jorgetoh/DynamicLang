package me.jorgetoh.dynamiclang.managers;

import me.jorgetoh.dynamiclang.DynamicLang;
import me.jorgetoh.dynamiclang.api.MessengerAPI;
import me.jorgetoh.dynamiclang.util.HFile;
import me.jorgetoh.dynamiclang.util.RegisteredPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messenger implements MessengerAPI {
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
            plugin.getLogger().info("Could not find the lang file for the language '"+langFile+"' plugin: '"+pluginName+"' . key:" + messageKey);
            return null;
        }

        return langFile.getConfig().getString("messages."+messageKey);
    }

    @Override
    public void sendMessage(Player player, String pluginName, String messageKey) {
        String message = getMessage(player, pluginName, messageKey);
        if (message == null) return;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void sendMessage(Player player, String pluginName, String messageKey, String... args) {
        String message = getMessage(player, pluginName, messageKey);
        if (message == null) return;

        for (int i = 0; i < args.length; i++) {
            String placeholder = "%v" + (i + 1);
            message = message.replace(placeholder, args[i]);
        }

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void sendGlobalMessage(String pluginName, String messageKey) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            sendMessage(player, pluginName, messageKey);
        });
    }

    @Override
    public void sendGlobalMessage(String pluginName, String messageKey, String... args) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            sendMessage(player, pluginName, messageKey, args);
        });
    }

    @Override
    public void register(String pluginName) {
        plugin.getLogger().info("The plugin '"+pluginName+"' is trying to connect to the API...");
        plugin.getRegisteredPlugins().registerPlugin(pluginName);

    }

}
