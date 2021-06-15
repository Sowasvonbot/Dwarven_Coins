package com.github.sowasvonbot.trading_inputs;

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
            case Constants.tradePrefix:
                player.sendMessage("Found trading prefix");
                tradingMode = TradingMode.TRADE;
                break;
            case Constants.coinStorePrefix:
                player.sendMessage("Found coin store prefix");
                tradingMode = TradingMode.COINS;
                break;
            case Constants.inputPrefix:
                player.sendMessage("Found input prefix");
                tradingMode = TradingMode.INPUT;
                break;
        }
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


}
