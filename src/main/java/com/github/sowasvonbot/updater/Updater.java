package com.github.sowasvonbot.updater;

import com.github.sowasvonbot.Main;

import java.io.File;

public class Updater {

    public static void checkForUpdate() {

        Main.getMainLogger().info(VersionComparator.newVersionAvailable() ?
                "New version available" :
                "Plugin version is up to date");


        File pluginFolder = new File(System.getProperty("user.dir") + File.separator + "plugins");
        if (!pluginFolder.isDirectory()) {
            Main.getMainLogger().warning("wrong plugin folder. Not updating");
            return;
        }
        File[] files = pluginFolder.listFiles();

        for (File file : files) {
            Main.getMainLogger().info(file.getName());
        }

    }
}
