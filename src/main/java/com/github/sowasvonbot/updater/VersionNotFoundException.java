package com.github.sowasvonbot.updater;

public class VersionNotFoundException extends RuntimeException {

    public VersionNotFoundException() {
        super("Version not found in plugin.yml");
    }
}
