package me.jorgetoh.dynamiclang.util;

import com.comphenix.protocol.PacketType;
import me.jorgetoh.dynamiclang.DynamicLang;

import java.util.*;

public class LangEquivalences {

    private final HashMap<String, String> langEquivalences;
    private final DynamicLang plugin;

    public LangEquivalences(DynamicLang plugin) {
        this.plugin = plugin;
        langEquivalences = new HashMap<>();

        loadLangEquivalences();
    }

    private void loadLangEquivalences() {
        Objects.requireNonNull(plugin.getConfig().getConfigurationSection("lang-equivalences")).getKeys(false).forEach(langKey -> {
            List<String> stringList = plugin.getConfig().getStringList("lang-equivalences."+langKey);
            stringList.forEach(langItem -> {
                langEquivalences.put(langItem.toLowerCase(), langKey.toLowerCase());
            });
        });
    }

    public String getLangEquivalence(String lang) {
        return langEquivalences.get(lang);
    }

    // null means no language equivalence available.
    /*public String getPlayerLangEquivalence(UUID playerId) {
        String playerLang = DynamicLang.hPlayers.get(playerId);
        if (playerLang == null) return null;

        String langEquivalence = langEquivalences.get(playerLang);

        // If we find a lang equivalence for the player we update it, so we don't have to check for this again until he updates his language.
        if (langEquivalence != null) {
            DynamicLang.hPlayers.put(playerId, langEquivalence);
        }

        return langEquivalence;
    }*/

}
