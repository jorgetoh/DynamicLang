package me.jorgetoh.dynamiclang.managers;

import me.jorgetoh.dynamiclang.DynamicLang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LangCommand implements CommandExecutor {

    private final DynamicLang plugin;

    public LangCommand(DynamicLang plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {

            sender.sendMessage("You can only use this command as a player.");
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage("Your current language is: " + player.getLocale());
        return true;
    }
}
