package com.github.sowasvonbot.updater;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface MessageTarget {

    void sendMessage(String message);

    public static MessageTarget fromPlayer(Player player) {
        return new MessageTarget() {
            @Override public void sendMessage(String message) {
                player.sendMessage(message);
            }
        };
    }

    public static MessageTarget fromPlugin(Plugin plugin){
        return new MessageTarget() {
            @Override public void sendMessage(String message) {
                plugin.getLogger().info(message);
            }
        };
    }
}
