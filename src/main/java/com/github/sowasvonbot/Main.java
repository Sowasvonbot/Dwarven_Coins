package com.github.sowasvonbot;

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
        CommandExecutor commandExecutor = new CommandExecutor();
        this.getCommand("test").setExecutor(commandExecutor);
        getServer().getPluginManager().registerEvents(new BasicPlayerListener(), this);
    }

    @Override public void onDisable() {
        getLogger().info("onDisable is called!");
        getServer().removeRecipe(Coin.getRecipe().getKey());
    }

    public static Logger getMainLogger() {
        return instance.getLogger();
    }
}
