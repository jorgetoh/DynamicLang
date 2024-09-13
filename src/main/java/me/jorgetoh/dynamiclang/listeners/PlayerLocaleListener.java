package me.jorgetoh.dynamiclang.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLocaleListener implements Listener {

    public PlayerLocaleListener(DynamicLang plugin) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(localeSettings(plugin));

        //I think for now, I want to keep track of languages of disconnected players.
        //plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private PacketAdapter localeSettings(DynamicLang plugin) {
        return new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.SETTINGS) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.SETTINGS) {
                    DynamicLang.hPlayers.put(event.getPlayer().getUniqueId(), event.getPacket().getStrings().readSafely(0));
                }
            }
        };
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        DynamicLang.hPlayers.remove(event.getPlayer().getUniqueId());
    }

}
