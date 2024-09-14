package me.jorgetoh.dynamiclang.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.jorgetoh.dynamiclang.DynamicLang;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLocaleListener implements Listener {

    private final DynamicLang plugin;

    //All this is no longer required because player.getLocale actually works fine even on player join lol

    public PlayerLocaleListener(DynamicLang plugin) {
        this.plugin = plugin;

        //ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        //manager.addPacketListener(localeSettings(plugin));

        //I think for now, I want to keep track of languages of disconnected players.
        //plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private PacketAdapter localeSettings(DynamicLang plugin) {
        return new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.SETTINGS) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                //DynamicLang.hPlayers.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocale());
            }
        };
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        //DynamicLang.hPlayers.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        //DynamicLang.hPlayers.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocale());
    }

}
