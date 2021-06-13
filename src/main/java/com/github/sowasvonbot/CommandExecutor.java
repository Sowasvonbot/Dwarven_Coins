package com.github.sowasvonbot;

import com.github.sowasvonbot.updater.Updater;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        switch (command.getName()) {
            case "update":
                Updater.checkForUpdate(false);
                break;
            case "force_update":
                Updater.checkForUpdate(true);
                break;
            default:
                ((Player) sender).chat(String.format("You typed %s", command.getName()));
        }
        return true;
    }
}
