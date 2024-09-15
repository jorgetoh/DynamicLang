package me.jorgetoh.dynamiclang.util;

import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

}
