package com.github.sowasvonbot.trading_inputs;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Objects;

public class NetworkStorage {

    private final BiMap<TradingSign, TradingBlock> storage;

    public NetworkStorage() {
        this.storage = HashBiMap.create();
    }

    private void storeTradingBlock(TradingBlock tradingBlock, TradingSign tradingSign) {
        if (storage.values().stream()
                .anyMatch(tradingBlock1 -> tradingBlock1.equals(tradingBlock))) {
            tradingSign.getSign().breakNaturally();
        } else {
            storage.put(tradingSign, tradingBlock);
        }

    }

    private static NetworkStorage instance;

    public static NetworkStorage getInstance() {
        return Objects.isNull(instance) ? instance = new NetworkStorage() : instance;
    }

    public static void registerTradingBlock(TradingBlock tradingBlock, TradingSign tradingSign) {
        getInstance().storeTradingBlock(tradingBlock, tradingSign);
    }
}
