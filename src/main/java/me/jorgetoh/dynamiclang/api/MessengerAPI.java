package me.jorgetoh.dynamiclang.api;

import org.bukkit.entity.Player;

public interface MessengerAPI {

    void sendMessage(Player player, String pluginName, String messageKey);
    void sendMessage(Player player, String pluginName, String messageKey, String... args);

    void sendGlobalMessage(String pluginName, String messageKey);
    void sendGlobalMessage(String pluginName, String messageKey, String... args);

    void register(String pluginName);

}
