package com.github.sowasvonbot.trading_inputs.trading_blocks;

import com.github.sowasvonbot.Main;
import com.github.sowasvonbot.coin.Coin;
import com.github.sowasvonbot.trading_inputs.Trade;
import com.github.sowasvonbot.trading_inputs.TradingBlock;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class Trader extends TradingBlock {

    private boolean isClosed = false;
    private Trade trade;

    public Trader(Block block, Player player) {
        super(block, player);
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    @Override public boolean isTradingBlock() {
        return true;
    }

    @Override public void handleTrade() {
        if (isClosed)
            return;
        isClosed = true;
        player.sendMessage("TRADING");
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, SoundCategory.MASTER, 1.0f,
                1.0f);

        Bukkit.getServer().getScheduler().runTaskLater(Main.getInstance(), () -> {

            for (ItemStack itemStack : myInventory.getContents()) {


                if (Objects.nonNull(itemStack) && Coin.isCoin(itemStack)) {
                    while (itemStack.getAmount() >= trade.coinAmount()) {
                        itemStack.setAmount(itemStack.getAmount() - trade.coinAmount());
                        myInventory
                                .addItem(new ItemStack(trade.material(), trade.materialAmount()));

                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_STEP,
                            SoundCategory.MASTER, 1.0f, 1.0f);
                }

            }
            isClosed = false;
        }, 20 * 3);
    }
}
