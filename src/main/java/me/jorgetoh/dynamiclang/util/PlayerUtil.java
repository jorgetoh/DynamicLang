package me.jorgetoh.dynamiclang.util;

import me.jorgetoh.dynamiclang.DynamicLang;

import java.util.UUID;

public class PlayerUtil {

    private final DynamicLang plugin;

    public PlayerUtil(DynamicLang plugin) {
        this.plugin = plugin;
    }

    /*private String getPlayerLang(UUID playerId, String pluginName) {

        String registeredLang = DynamicLang.hPlayers.get(playerId);
        if (registeredLang == null) {
            registeredLang = plugin.getDefaultLang();
        }

        return "";
    }*/

}
