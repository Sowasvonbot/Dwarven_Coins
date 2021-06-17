package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.Main;
import com.github.sowasvonbot.trading_inputs.trading_blocks.CoinStorage;
import com.github.sowasvonbot.trading_inputs.trading_blocks.Dummy;
import com.github.sowasvonbot.trading_inputs.trading_blocks.InputBlock;
import com.github.sowasvonbot.trading_inputs.trading_blocks.Trader;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public abstract class TradingBlock {

    protected Block block;
    protected final Player player;
    protected final Inventory myInventory;

    public TradingBlock(Block block, Player player) {
        this.block = block;
        this.player = player;


        if (Objects.nonNull(block) && block.getState() instanceof InventoryHolder inventoryHolder) {
            player.sendMessage("Found inventory");
            myInventory = inventoryHolder.getInventory();
        } else
            myInventory = null;
    }

    @Override public int hashCode() {
        return player.hashCode();
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public abstract boolean isTradingBlock();

    public static TradingBlock fromBlock(Block block, Player player, String tradingLine) {
        BlockData blockData = block.getState().getBlockData();

        if (block.getType().equals(Material.CHEST)) {
            return switch (getTradingModeFromString(player, tradingLine)) {
                case INPUT -> new InputBlock(block, player);
                case COINS -> new CoinStorage(block, player);
                case TRADE -> new Trader(block, player);
            };
        }
        return new Dummy();

    }

    public abstract void handleTrade();

    private enum TradingMode {
        TRADE, INPUT, COINS
    }

    @Override public boolean equals(Object obj) {
        Main.getMainLogger()
                .info(String.format("Equals is called in %s", this.getClass().getSimpleName()));
        if (!(obj instanceof TradingBlock tradingBlock))
            return false;
        return tradingBlock.getBlock().equals(block);
    }

    private static TradingMode getTradingModeFromString(Player player, String tradingLine) {
        switch (tradingLine) {
            case Constants.tradePrefix -> {
                player.sendMessage("Found trading prefix");
                return TradingMode.TRADE;
            }
            case Constants.coinStorePrefix -> {
                player.sendMessage("Found coin store prefix");
                return TradingMode.COINS;
            }
            case Constants.inputPrefix -> {
                player.sendMessage("Found input prefix");
                return TradingMode.INPUT;
            }
        }
        throw new IllegalArgumentException(tradingLine + " is not a valid trading line");
    }
}
