package com.github.sowasvonbot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;

        switch (command.getName()) {
            default:
                ((Player) sender).chat(String.format("You typed %s", command.getName()));
        }
        return true;
    }
}
