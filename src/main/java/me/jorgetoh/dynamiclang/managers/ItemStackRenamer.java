package me.jorgetoh.dynamiclang.managers;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemStackRenamer extends PacketAdapter implements Listener {

    private final DynamicLang plugin;

    public ItemStackRenamer(DynamicLang plugin) {
        super(plugin, ListenerPriority.NORMAL,
                PacketType.Play.Server.SET_SLOT,
                PacketType.Play.Server.WINDOW_ITEMS,
                PacketType.Play.Server.ENTITY_EQUIPMENT
        );

        this.plugin = plugin;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;

        if (event.getPacketType() == PacketType.Play.Server.SET_SLOT) {
            modifyItemStack(packet.getItemModifier(), player);
        }

        if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
            modifyItemStackList(packet.getItemListModifier(), player);
        }

        if (event.getPacketType() == PacketType.Play.Server.ENTITY_EQUIPMENT) {
            modifyEntityEquipment(packet, player);
        }
    }

    private void modifyItemStack(StructureModifier<ItemStack> itemStackModifier, Player player) {
        for (int i = 0; i < itemStackModifier.size(); i++) {
            ItemStack itemStack = itemStackModifier.read(i);

            if (itemStack == null) continue;
            ItemStack translated = plugin.getItemUtil().translateItem(itemStack, player);
            itemStackModifier.write(i, translated);
        }
    }

    private void modifyItemStackList(StructureModifier<List<ItemStack>> itemStackListModifier, Player player) {
        List<ItemStack> itemStackList = itemStackListModifier.read(0);
        for (int i = 0; i < itemStackList.size(); i++) {
            ItemStack itemStack = itemStackList.get(i);
            if (itemStack != null) {
                ItemStack translated = plugin.getItemUtil().translateItem(itemStack, player);
                itemStackList.set(i, translated);
            }
        }
        itemStackListModifier.write(0, itemStackList);
    }

    private void modifyEntityEquipment(PacketContainer packet, Player player) {
        List<Pair<EnumWrappers.ItemSlot, ItemStack>> equipmentList = packet.getSlotStackPairLists().read(0);
        for (int i = 0; i < equipmentList.size(); i++) {
            Pair<EnumWrappers.ItemSlot, ItemStack> equipment = equipmentList.get(i);
            ItemStack itemStack = equipment.getSecond();
            if (itemStack != null) {
                ItemStack translated = plugin.getItemUtil().translateItem(itemStack, player);
                equipmentList.set(i, new Pair<>(equipment.getFirst(), translated));
            }
        }
        packet.getSlotStackPairLists().write(0, equipmentList);
    }


    /*
    I update the inventory 1 tick after changing language or gamemode so the player
    always have the right item data, but I'm pretty sure there is a better way of achieving this.
    */
    @EventHandler
    public void playerLocaleEvent(PlayerLocaleChangeEvent event) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            event.getPlayer().updateInventory();
        });

    }
    @EventHandler
    public void playerLocaleEvent(PlayerGameModeChangeEvent event) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            event.getPlayer().updateInventory();
        });

    }


    /*private PacketAdapter getSetSlotPacket() {
        return new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.SET_SLOT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                StructureModifier<ItemStack> itemStackStructureModifier = packet.getItemModifier();

                for(int i = 0; i < itemStackStructureModifier.size(); i++) {
                    ItemStack itemStack = itemStackStructureModifier.read(i);
                    if (itemStack == null) continue;
                    ItemStack translated = ((DynamicLang) plugin).getItemUtil().translateItem(itemStack, event.getPlayer());
                    itemStackStructureModifier.write(i, translated);
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
                    ItemStack translated = ((DynamicLang) plugin).getItemUtil().translateItem(itemStack, event.getPlayer());
                    itemStackStructureModifier.write(i, translated);
                }
            }
        };
    }*/





    /*public void loadItemKeys() {
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
    }*/
}
