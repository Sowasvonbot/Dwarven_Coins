package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.Main;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryListener implements Listener {

    private final List<InventoryAction> listenToActions =
            Arrays.asList(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE,
                    InventoryAction.PLACE_SOME, InventoryAction.MOVE_TO_OTHER_INVENTORY);

    @EventHandler public void onItemPut(InventoryClickEvent event) {
        Main.getMainLogger().info(event.getAction().name());
        if (Objects.isNull(event.getClickedInventory()) || !event.getInventory().getType()
                .equals(InventoryType.CHEST) || !listenToActions.contains(event.getAction()))
            return;


        Main.getMainLogger().info("Item in chest");
        if (!(event.getInventory().getHolder() instanceof Container chest))
            return;
        Main.getMainLogger().info("It is a chest");
        Block chestBlock = chest.getBlock();

        if (!(event.getWhoClicked() instanceof Player player))
            return;

        TradingBlock tradingBlock = NetworkStorage.getInstance().getBlock(player, chestBlock);
        if (!tradingBlock.isTradingBlock())
            return;

        tradingBlock.handleTrade();

    }
}
