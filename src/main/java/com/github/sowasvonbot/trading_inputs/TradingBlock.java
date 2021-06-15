package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TradingBlock {

    private Block block;
    private Player player;
    private TradingMode tradingMode;

    public TradingBlock(Block block, Player player, String tradingLine) {
        this.block = block;
        this.player = player;

        switch (tradingLine) {
            case Constants.tradePrefix -> {
                player.sendMessage("Found trading prefix");
                tradingMode = TradingMode.TRADE;
            }
            case Constants.coinStorePrefix -> {
                player.sendMessage("Found coin store prefix");
                tradingMode = TradingMode.COINS;
            }
            case Constants.inputPrefix -> {
                player.sendMessage("Found input prefix");
                tradingMode = TradingMode.INPUT;
            }
        }
    }

    @Override public int hashCode() {
        return Objects.hash(block);
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public TradingMode getTradingMode() {
        return tradingMode;
    }

    private TradingBlock() {
        block = null;
    }

    public boolean isTradingBlock() {
        return Objects.nonNull(block);
    }

    public static TradingBlock fromBlock(Block block, Player player, String tradingLine) {
        BlockData blockData = block.getState().getBlockData();

        if (block.getType().equals(Material.CHEST)) {
            return new TradingBlock(block, player, tradingLine);
        }
        return new TradingBlock();

    }

    private enum TradingMode {
        TRADE, INPUT, COINS
    }

    @Override public boolean equals(Object obj) {
        Main.getMainLogger().info("Equals is called");
        if (!(obj instanceof TradingBlock tradingBlock))
            return false;
        return tradingBlock.getBlock().equals(block);
    }
}
