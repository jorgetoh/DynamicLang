package me.jorgetoh.dynamiclang.api;

import me.jorgetoh.dynamiclang.DynamicLang;
import me.jorgetoh.dynamiclang.util.HFile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MessengerImplementation implements DynamicLangAPI {
    private final DynamicLang plugin;
    private final Plugin caller;


    public MessengerImplementation(DynamicLang plugin, Plugin caller) {
        this.plugin = plugin;
        this.caller = caller;
    }




    private Object getMessage(Player player, String pluginName, String messageKey) {
        HFile langFile = plugin.getFileUtil().getPlayerLangFile(player, pluginName);

        if (langFile == null) return null;

        if (langFile.getConfig().isList("messages." + messageKey)) {
            return langFile.getConfig().getStringList("messages." + messageKey);
        } else if (langFile.getConfig().isString("messages." + messageKey)) {
            return langFile.getConfig().getString("messages." + messageKey);
        } else {
            plugin.getLogger().info("Message key '" + messageKey + "' not found in language file for plugin '" + pluginName + "'.");
            return null;
        }
    }



    private void sendMessage(Player player, String pluginName, String messageKey) {
        Object message = getMessage(player, pluginName, messageKey);
        if (message == null) return;
        if (message instanceof String singleMessage) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', singleMessage));
        } else if (message instanceof List) {
            List<String> messageList = (List<String>) message;
            messageList.forEach(msg -> player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg)));
        }
    }

    private void sendMessage(Player player, String pluginName, String messageKey, String... args) {
        Object message = getMessage(player, pluginName, messageKey);
        if (message == null) return;
        if (message instanceof String singleMessage) {
            for (int i = 0; i < args.length; i++) {
                String placeholder = "%v" + (i + 1);
                singleMessage = singleMessage.replace(placeholder, args[i]);
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', singleMessage));
        } else if (message instanceof List) {
            List<String> messageList = (List<String>) message;
            for (String msg : messageList) {
                for (int i = 0; i < args.length; i++) {
                    String placeholder = "%v" + (i + 1);
                    msg = msg.replace(placeholder, args[i]);
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }

    private void sendGlobalMessage(String pluginName, String messageKey) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            sendMessage(player, pluginName, messageKey);
        });
    }

    private void sendGlobalMessage(String pluginName, String messageKey, String... args) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            sendMessage(player, pluginName, messageKey, args);
        });
    }

    @Override
    public void sendMessage(Player player, String messageKey) {
        sendMessage(player, caller.getName(), messageKey);
    }

    @Override
    public void sendMessage(Player player, String messageKey, String... args) {
        sendMessage(player, caller.getName(), messageKey, args);
    }

    @Override
    public void sendGlobalMessage(String messageKey) {
        sendGlobalMessage(caller.getName(), messageKey);
    }

    @Override
    public void sendGlobalMessage(String messageKey, String... args) {
        sendGlobalMessage(caller.getName(), messageKey, args);
    }

    @Override
    public ItemStack getTranslateableItem(Player player, String itemKey, ItemStack itemStack) {
        return plugin.getItemUtil().addCustomData(itemStack.clone(), caller.getName(), itemKey);
    }

    @Override
    public void register(String pluginName) {
        plugin.getLogger().info("The plugin '"+pluginName+"' is trying to connect to the API...");
        plugin.getRegisteredPlugins().registerPlugin(pluginName);

    }


    /*private String getCallingPluginName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace) {
            try {
                Class<?> _class = Class.forName(element.getClassName());

                if (_class.getPackage().getName().equals(getClass().getPackage().getName())) {
                    continue;
                }

                Plugin callingPlugin = getPluginByClassLoader(_class.getClassLoader());
                if (callingPlugin != null) {
                    return callingPlugin.getName();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private Plugin getPluginByClassLoader(ClassLoader classLoader) {
        try {
            Class<?> pluginClassLoader = Class.forName("org.bukkit.plugin.java.PluginClassLoader");

            if (pluginClassLoader.isInstance(classLoader)) {
                Field pluginField = pluginClassLoader.getDeclaredField("plugin");
                pluginField.setAccessible(true);

                return (Plugin) pluginField.get(classLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
