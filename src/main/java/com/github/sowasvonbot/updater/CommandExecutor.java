package com.github.sowasvonbot.updater;

import com.github.sowasvonbot.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

    public void registerCommands() {
        Main.getInstance().getCommand("update").setExecutor(this);
        Main.getInstance().getCommand("force_update").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MessageTarget target;
        if (sender instanceof Player)
            target = MessageTarget.fromPlayer((Player) sender);
        else
            target = MessageTarget.fromPlugin(Main.getInstance());

        boolean force = false;
        switch (command.getName()) {
            case "force_update":
                force = true;
            case "update":
                Updater.checkForUpdate(target, force);
                break;
        }
        return true;
    }

}
