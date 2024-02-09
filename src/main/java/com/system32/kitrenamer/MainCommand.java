package com.system32.kitrenamer;

import com.system32.kitrenamer.Menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player player)) return false;
        FileConfiguration config = KitRenamer.getInstance().getConfig();
            if(!player.hasPermission("kitrenamer.use")){
                Utils.playerMessage(player, "&cYou don't have permission to use this command.");
                return false;
            };
        MainMenu.openMainMenu(player);
        return true;
    }

}
