package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.trading_inputs.trading_blocks.Trader;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Arrays;
import java.util.List;

public class ShieldListener implements Listener {

    @EventHandler public void changeSignEvent(SignChangeEvent event) {
        handleTradeSign(event.getBlock(), event.getPlayer(), Arrays.asList(event.getLines()));
    }

    @EventHandler public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().name().toLowerCase().contains("sign"))
            return;

        NetworkStorage.getInstance().destroySign(event.getPlayer(), event.getBlock());
    }

    private void handleTradeSign(Block sign, Player player, List<String> lines) {
        if (!(sign.getState().getBlockData() instanceof WallSign signState))
            return;

        TradingSign tradingSign = TradingSign.fromSignBlock(sign, player, lines);

        if (!tradingSign.isValidTradingSign())
            return;

        TradingBlock tradingBlock = TradingBlock
                .fromBlock(sign.getRelative(signState.getFacing().getOppositeFace()), player,
                        tradingSign.getTradeLine());

        if (!tradingBlock.isTradingBlock()) {
            sign.breakNaturally();
            return;
        }

        if ((tradingBlock instanceof Trader trader)) {
            try {
                Trade trade = Trade.fromStringList(lines);
                trader.setTrade(trade);
                player.sendMessage(trade.toString());

            } catch (Trade.TradeParseException e) {
                player.sendMessage(e.getMessage());
                sign.breakNaturally();
                return;
            }
        }
        NetworkStorage.registerTradingBlock(tradingBlock, tradingSign);


    }
}
