package com.github.sowasvonbot.coin;


import com.github.sowasvonbot.Main;
import com.github.sowasvonbot.coin.Coin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

public class CoinListener implements Listener {

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.chat("Hello");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void anyBlockEvent(BlockFormEvent blockEvent) {
        Main.getMainLogger().info(blockEvent.getEventName());
    }

    @EventHandler public void preventCoinPlace(BlockPlaceEvent event) {
        event.setCancelled(Coin.isCoin(event.getItemInHand()));
    }

    @EventHandler public void preventCoinPlace(BlockMultiPlaceEvent event) {
        event.setCancelled(Coin.isCoin(event.getItemInHand()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void preventEquipmentOfHead(InventoryClickEvent event) {
        if (event.getCursor().getType() != Material.PLAYER_HEAD)
            return;

        boolean isCoin = Coin.isCoin(event.getCursor());
        boolean isArmor = event.getSlotType() == InventoryType.SlotType.ARMOR;
        boolean isCrafting = !isArmor && event.getSlotType() == InventoryType.SlotType.CRAFTING;

        event.setCancelled((isCoin && isArmor) || (!isCoin && isCrafting));
    }
}
