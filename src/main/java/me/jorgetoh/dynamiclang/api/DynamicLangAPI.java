package me.jorgetoh.dynamiclang.api;

import org.bukkit.entity.Player;

public interface DynamicLangAPI {

    void sendMessage(Player player, String messageKey);
    void sendMessage(Player player, String messageKey, String... args);
    void sendGlobalMessage(String messageKey);
    void sendGlobalMessage(String messageKey, String... args);

    void register(String pluginName);

}
