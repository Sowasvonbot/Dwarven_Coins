package com.github.sowasvonbot.updater;

import com.github.sowasvonbot.Main;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Updater {

    private static final File pluginFolder =
            new File(System.getProperty("user.dir") + File.separator + "plugins");

    public static void checkForUpdate() {

        Main.getMainLogger().info(VersionComparator.newVersionAvailable() ?
                "New version available" :
                "Plugin version is up to date");

        deletePlugin();
        try {
            downloadPlugin();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void downloadPlugin() throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(
                GitHubApiGrabber.getInstance().getLatestReleaseDownloadURL().openStream());

        File jarFile =
                new File(pluginFolder.getAbsolutePath() + File.separator + "DwarvenCoins.jar");
        FileOutputStream fileOutputStream = new FileOutputStream(jarFile);

        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

        fileOutputStream.close();

        Bukkit.getServer().reload();
    }

    public static void deletePlugin() {
        if (!pluginFolder.isDirectory()) {
            Main.getMainLogger().warning("wrong plugin folder. Not updating");
            return;
        }
        File[] files = pluginFolder.listFiles();

        for (File file : files) {
            Main.getMainLogger().info(file.getName());
            if (file.getName().contains("DwarvenCoin")) {
                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
                Main.getMainLogger().info(String.format("Trying to delete %s", file.getName()));
                file.delete();
            }
        }
    }
}
