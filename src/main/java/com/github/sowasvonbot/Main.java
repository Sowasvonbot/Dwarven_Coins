package com.github.sowasvonbot;

import com.github.sowasvonbot.updater.CommandExecutor;
import com.github.sowasvonbot.updater.VersionComparator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override public void onEnable() {
        instance = this;
        getLogger().info("onEnable is called!");
        getServer().addRecipe(Coin.getRecipe());

        new CommandExecutor().registerCommands();

        getServer().getPluginManager().registerEvents(new BasicPlayerListener(), this);

        Main.getMainLogger().info(VersionComparator.newVersionAvailable() ?
                "New version available" :
                "Plugin version is up to date");
    }

    @Override public void onDisable() {
        getLogger().info("onDisable is called!");
        getServer().removeRecipe(Coin.getRecipe().getKey());
    }

    public static Logger getMainLogger() {
        return instance.getLogger();
    }
}
