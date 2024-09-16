package me.jorgetoh.dynamiclang.api;

import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.plugin.Plugin;

public class DynamicLangAccessorImplementation implements DynamicLangAccessor {

    private final DynamicLang plugin;
    public DynamicLangAccessorImplementation(DynamicLang plugin) {
        this.plugin = plugin;
    }

    @Override
    public DynamicLangAPI get(Plugin caller) {
        return new MessengerImplementation(plugin, caller);
    }

}
