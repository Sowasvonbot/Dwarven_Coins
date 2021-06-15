package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.Main;
import com.github.sowasvonbot.coin.Coin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class InventoryListener implements Listener {

    private final List<InventoryAction> listenToActions =
            Arrays.asList(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE,
                    InventoryAction.PLACE_SOME);

    @EventHandler public void onItemPut(InventoryClickEvent event) {
        Main.getMainLogger().info(event.getAction().name());
        if (!event.getClickedInventory().getType().equals(InventoryType.CHEST) || !listenToActions
                .contains(event.getAction()))
            return;

        Main.getMainLogger().info("Item in chest");
        if (!(event.getClickedInventory().getHolder() instanceof Container chest))
            return;
        Main.getMainLogger().info("It is a chest");
        Block chestBlock = chest.getBlock();

        if (!NetworkStorage.getInstance().containsBlock(chestBlock))
            return;

        Main.getMainLogger().info("Chest was a trading chest");
        if (event.getWhoClicked() instanceof Player player) {
            player.sendMessage("TRADING");
            player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, SoundCategory.MASTER, 1.0f,
                    1.0f);
        }



        Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {
            ItemStack itemStack = event.getCurrentItem();
            itemStack.setAmount(0);
            event.getClickedInventory().addItem(Coin.createItemStack());

        }, 20);

    }
}
