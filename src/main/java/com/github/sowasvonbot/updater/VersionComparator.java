package com.github.sowasvonbot.updater;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VersionComparator {

    protected static String getLocalVersion() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                VersionComparator.class.getClassLoader().getResourceAsStream("plugin.yml")));


        while (reader.ready()) {
            String line = reader.readLine();

            if (line.contains("version:")) {
                line = line.replace(" ", "");
                line = line.replace("version:", "");
                reader.close();
                return line;
            }
        }
        throw new VersionNotFoundException();
    }

    public static boolean newVersionAvailable() {
        try {
            return !getLocalVersion()
                    .equals(GitHubApiGrabber.getInstance().getVersionNameOnGithub());
        } catch (IOException e) {
            Bukkit.getLogger()
                    .warning("Error retrieving version from GitHub, auto update not available");
            return false;
        }
    }
}
