package me.jorgetoh.dynamiclang.util;

import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemUtil {

    private final DynamicLang plugin;
    private final NamespacedKey PLUGIN_NAME_KEY;
    private final NamespacedKey ITEM_KEY;

    public ItemUtil(DynamicLang plugin) {
        this.plugin = plugin;
        PLUGIN_NAME_KEY = new NamespacedKey(plugin, "plugin_name");
        ITEM_KEY = new NamespacedKey(plugin, "item_key");
    }

    public ItemStack addCustomData(ItemStack item, String pluginName, String itemKey) {
        if (item == null || item.getType() == Material.AIR) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();

            container.set(PLUGIN_NAME_KEY, PersistentDataType.STRING, pluginName);
            container.set(ITEM_KEY, PersistentDataType.STRING, itemKey);
            item.setItemMeta(meta);
        }
        return item;
    }

    public boolean hasCustomData(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            return container.has(PLUGIN_NAME_KEY, PersistentDataType.STRING) &&
                    container.has(ITEM_KEY, PersistentDataType.STRING);
        }
        return false;
    }

    public String getPluginName(ItemStack item) {
        return getString(item, PLUGIN_NAME_KEY);
    }

    public String getItemKey(ItemStack item) {
        return getString(item, ITEM_KEY);
    }

    private String getString(ItemStack item, NamespacedKey itemKey) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            return container.get(itemKey, PersistentDataType.STRING);
        }
        return null;
    }

    public String[] getCustomData(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer container = meta.getPersistentDataContainer();

            String pluginName = container.get(PLUGIN_NAME_KEY, PersistentDataType.STRING);
            String itemKey = container.get(ITEM_KEY, PersistentDataType.STRING);

            if (pluginName != null && itemKey != null) {
                return new String[] { pluginName, itemKey };
            }
        }
        return null;
    }


    public ItemStack translateItem(ItemStack itemStack, Player player) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return itemStack;
        if (!hasCustomData(itemStack)) return itemStack;

        String[] customData = getCustomData(itemStack);
        HFile hFile = plugin.getFileUtil().getPlayerLangFile(player, customData[0]);
        if (hFile == null) return itemStack;

        ItemStack temporalItem = itemStack.clone();
        String itemName = hFile.getConfig().getString("items."+customData[1]+".name");
        List<String> itemLore = hFile.getConfig().getStringList("items."+customData[1]+".lore");

        setDisplayName(temporalItem, itemName);
        setLore(temporalItem, itemLore);

        return temporalItem;
    }

    public void setDisplayName(ItemStack itemStack, String name) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            itemStack.setItemMeta(meta);
        }
    }


    public void setLore(ItemStack itemStack, List<String> lore) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> formattedLore = lore.stream()
                    .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                    .toList();
            meta.setLore(formattedLore);
            itemStack.setItemMeta(meta);
        }
    }

}
