package com.github.sowasvonbot.updater;

import com.github.sowasvonbot.Main;

import java.io.File;

public class Updater {

    public static void checkForUpdate() {

        Main.getMainLogger().info(VersionComparator.newVersionAvailable() ?
                "New version available" :
                "Plugin version is up to date");


        Main.getMainLogger().info(System.getProperty("user.dir") + File.separator + "plugins");
    }
}
