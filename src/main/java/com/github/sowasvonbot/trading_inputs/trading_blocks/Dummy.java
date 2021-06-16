package com.github.sowasvonbot.trading_inputs.trading_blocks;

import com.github.sowasvonbot.Main;
import com.github.sowasvonbot.trading_inputs.TradingBlock;

public class Dummy extends TradingBlock {
    public Dummy() {
        super(null, null);
    }

    @Override public boolean isTradingBlock() {
        return false;
    }

    @Override public void handleTrade() {
        Main.getMainLogger().warning("Trading on Dummy called");
    }
}
