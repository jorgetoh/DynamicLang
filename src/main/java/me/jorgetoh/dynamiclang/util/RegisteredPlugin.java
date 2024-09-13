package me.jorgetoh.dynamiclang.util;

import java.util.HashSet;

public class RegisteredPlugin {

    private final String name;
    private final HashSet<String> itemKeys;


    public RegisteredPlugin(String name) {
        this.name = name;
        itemKeys = new HashSet<>();
    }

    public HashSet<String> getItemKeys() {
        return itemKeys;
    }

    public void addItemKey(String itemKey) {
        itemKeys.add(itemKey);
    }
}
