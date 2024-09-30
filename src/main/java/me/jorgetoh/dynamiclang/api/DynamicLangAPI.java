package me.jorgetoh.dynamiclang.api;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public interface DynamicLangAPI {

    void sendMessage(CommandSender sender, String messageKey);
    void sendMessage(CommandSender sender, String messageKey, String... args);
    void sendGlobalMessage(String messageKey);
    void sendGlobalMessage(String messageKey, String... args);
    ItemStack getTranslateableItem(String itemKey, ItemStack itemStack);

    Object formatMessage(CommandSender sender, String messageKey);
    Object formatMessage(CommandSender sender, String messageKey, String... args);

    void register(String pluginName);

}
