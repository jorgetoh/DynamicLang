package me.jorgetoh.dynamiclang;

import me.jorgetoh.dynamiclang.api.DynamicLangAPI;
import me.jorgetoh.dynamiclang.listeners.PlayerLocaleListener;
import me.jorgetoh.dynamiclang.managers.ItemStackRenamer;
import me.jorgetoh.dynamiclang.managers.LangCommand;
import me.jorgetoh.dynamiclang.managers.Messenger;
import me.jorgetoh.dynamiclang.util.FileUtil;
import me.jorgetoh.dynamiclang.util.ItemUtil;
import me.jorgetoh.dynamiclang.util.LangEquivalences;
import me.jorgetoh.dynamiclang.util.RegisteredPlugins;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class DynamicLang extends JavaPlugin {


    private String defaultLang;
    //public static HashMap<UUID, String> hPlayers = new HashMap<>();


    private RegisteredPlugins registeredPlugins;
    private ItemStackRenamer itemStackRenamer;
    private LangEquivalences langEquivalences;
    private ItemUtil itemUtil;
    private FileUtil fileUtil;


    @Override
    public void onEnable() {
        loadPluginConfig();

        itemUtil = new ItemUtil(this);
        fileUtil = new FileUtil(this);
        langEquivalences = new LangEquivalences(this);
        registeredPlugins = new RegisteredPlugins(this);
        itemStackRenamer = new ItemStackRenamer(this);

        if (getConfig().getBoolean("item-rename")) {
            itemStackRenamer.initializeItemRenamer();
        }

        new PlayerLocaleListener(this);

        getServer().getServicesManager().register(DynamicLangAPI.class, new Messenger(this), this, ServicePriority.Normal);
        this.getCommand("language").setExecutor(new LangCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadPluginConfig() {
        saveDefaultConfig();

        defaultLang = getConfig().getString("default-lang");
    }

    public RegisteredPlugins getRegisteredPlugins() {
        return registeredPlugins;
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public LangEquivalences getLangEquivalences() {
        return langEquivalences;
    }

    public ItemUtil getItemUtil() {
        return itemUtil;
    }

    public FileUtil getFileUtil() {
        return fileUtil;
    }
}
