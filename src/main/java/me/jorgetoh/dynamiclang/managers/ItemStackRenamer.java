package me.jorgetoh.dynamiclang.managers;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import me.jorgetoh.dynamiclang.DynamicLang;
import me.jorgetoh.dynamiclang.util.HFile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;

public class ItemStackRenamer {

    private final DynamicLang plugin;
    private final HashMap<String, String> itemKeys;

    public ItemStackRenamer(DynamicLang plugin) {
        this.plugin = plugin;

        itemKeys = new HashMap<>();
    }

    public void initializeItemRenamer() {
        loadItemKeys();

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(getSetSlotPacket());
        manager.addPacketListener(getWindowItem());
    }


    private PacketAdapter getSetSlotPacket() {
        return new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                StructureModifier<ItemStack> itemStackStructureModifier = packet.getItemModifier();

                for(int i = 0; i < itemStackStructureModifier.size(); i++) {
                    ItemStack itemStack = itemStackStructureModifier.read(i);

                    if (itemStack == null) continue;

                    plugin.getLogger().info(itemStack.getType().toString());
                    if (itemStack.getItemMeta() != null) {
                        if (itemStack.getItemMeta().hasDisplayName()) {
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName("PRUEBA ! ! !");
                            itemStack.setItemMeta(itemMeta);
                            itemStackStructureModifier.write(i, itemStack);
                        }
                    }
                }
            }
        };
    }

    private PacketAdapter getWindowItem() {
        return new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.WINDOW_ITEMS ) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                StructureModifier<ItemStack> itemStackStructureModifier = packet.getItemModifier();

                for(int i = 0; i < itemStackStructureModifier.size(); i++) {
                    ItemStack itemStack = itemStackStructureModifier.read(i);

                    if (itemStack == null) continue;

                    plugin.getLogger().info(itemStack.getType().toString());
                    if (itemStack.getItemMeta() != null) {
                        if (itemStack.getItemMeta().hasDisplayName()) {
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setDisplayName("PRUEBA ! ! !");
                            itemStack.setItemMeta(itemMeta);
                            itemStackStructureModifier.write(i, itemStack);
                        }
                    }
                }
            }
        };
    }

    public void loadItemKeys() {
        itemKeys.clear();

        plugin.getRegisteredPlugins().getRegisteredPluginsMap().forEach((pluginName, registeredPlugin) -> {
            HFile hFile = registeredPlugin.getDefaultLang();
            Objects.requireNonNull(hFile.getConfig().getConfigurationSection("items")).getKeys(false).forEach(itemKey -> {
                if (itemKeys.containsKey(itemKey)) {
                    plugin.getLogger().info("The plugin '"+pluginName+"' tried to register the key '"+itemKey+"' but its already registered by the plugin '"+itemKeys.get(itemKey)+"'");
                } else {
                    itemKeys.put(itemKey, pluginName);
                }
            });
        });
        plugin.getLogger().info("Registered a total of "+itemKeys.size()+" items.");
    }
}
