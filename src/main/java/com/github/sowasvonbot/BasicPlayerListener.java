package com.github.sowasvonbot;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class BasicPlayerListener implements Listener {

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.chat("Hello");
    }

    @EventHandler public void preventLapisBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.PLAYER_HEAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void preventEquipmentOfHead(InventoryClickEvent event) {
        if (Objects.isNull(event.getCursor()) || event.getCursor()
                .getType() != Material.PLAYER_HEAD)
            return;

        String s = event.getCursor().getType().createBlockData().getAsString();

        Bukkit.getLogger().info("Is head:" + Coin.isCoin(event.getCursor()));
        if (event.getSlotType().equals(InventoryType.SlotType.ARMOR))

            event.setCancelled(true);
    }
}
