package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.trading_inputs.trading_blocks.Dummy;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;

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

    public boolean containsBlock(Block block) {
        return storage.values().stream()
                .anyMatch(tradingBlock -> tradingBlock.getBlock().equals(block));
    }

    public TradingBlock getBlock(Player player, Block block) {
        Optional<TradingBlock> optionalTradingBlock = storage.values().stream()
                .filter(tradingBlock -> tradingBlock.getBlock().equals(block)).findFirst();
        return optionalTradingBlock.orElseGet(Dummy::new);
    }

    private static NetworkStorage instance;

    public static NetworkStorage getInstance() {
        return Objects.isNull(instance) ? instance = new NetworkStorage() : instance;
    }

    public static void registerTradingBlock(TradingBlock tradingBlock, TradingSign tradingSign) {
        getInstance().storeTradingBlock(tradingBlock, tradingSign);
    }
}
