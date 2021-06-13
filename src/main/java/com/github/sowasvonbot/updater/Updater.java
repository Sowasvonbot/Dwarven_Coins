package com.github.sowasvonbot.updater;

import com.github.sowasvonbot.Main;
import org.bukkit.Bukkit;

import java.io.File;

public class Updater {

    public static void checkForUpdate() {

        Main.getMainLogger().info(VersionComparator.newVersionAvailable() ?
                "New version available" :
                "Plugin version is up to date");

    }

    public static void deletePlugin() {
        File pluginFolder = new File(System.getProperty("user.dir") + File.separator + "plugins");
        if (!pluginFolder.isDirectory()) {
            Main.getMainLogger().warning("wrong plugin folder. Not updating");
            return;
        }
        File[] files = pluginFolder.listFiles();

        for (File file : files) {
            Main.getMainLogger().info(file.getName());
            if (file.getName().contains(".jar")) {
                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
                Main.getMainLogger().info(String.format("Trying to delete %s", file.getName()));
                file.delete();
            }
        }
    }
}
