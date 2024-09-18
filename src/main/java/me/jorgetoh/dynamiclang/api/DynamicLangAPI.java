package me.jorgetoh.dynamiclang.api;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface DynamicLangAPI {

    void sendMessage(CommandSender sender, String messageKey);
    void sendMessage(CommandSender sender, String messageKey, String... args);
    void sendGlobalMessage(String messageKey);
    void sendGlobalMessage(String messageKey, String... args);
    ItemStack getTranslateableItem(Player player, String itemKey, ItemStack itemStack);

    void register(String pluginName);

}
