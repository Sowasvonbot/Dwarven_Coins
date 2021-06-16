package com.github.sowasvonbot.trading_inputs.trading_blocks;

import com.github.sowasvonbot.trading_inputs.TradingBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class InputBlock extends TradingBlock {
    public InputBlock(Block block, Player player) {
        super(block, player);
    }

    @Override public boolean isTradingBlock() {
        return true;
    }

    @Override public void handleTrade() {

    }
}
