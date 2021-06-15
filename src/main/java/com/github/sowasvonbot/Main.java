package com.github.sowasvonbot;

import com.github.sowasvonbot.coin.Coin;
import com.github.sowasvonbot.coin.CoinListener;
import com.github.sowasvonbot.trading_inputs.InventoryListener;
import com.github.sowasvonbot.trading_inputs.ShieldListener;
import com.github.sowasvonbot.updater.CommandExecutor;
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
        //getServer().addRecipe(Coin.getCraftBackRecipe());

        new CommandExecutor().registerCommands();

        getServer().getPluginManager().registerEvents(new CoinListener(), this);
        getServer().getPluginManager().registerEvents(new ShieldListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        //Main.getMainLogger().info(VersionComparator.newVersionAvailable() ?
        //       "New version available" :
        //        "Plugin version is up to date");
    }

    @Override public void onDisable() {
        getLogger().info("onDisable is called!");
        getServer().removeRecipe(Coin.getRecipe().getKey());
    }

    public static Logger getMainLogger() {
        return instance.getLogger();
    }
}
