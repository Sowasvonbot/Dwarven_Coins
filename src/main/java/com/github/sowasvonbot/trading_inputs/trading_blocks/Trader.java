package com.github.sowasvonbot.trading_inputs.trading_blocks;

import com.github.sowasvonbot.Main;
import com.github.sowasvonbot.coin.Coin;
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

    public Trader(Block block, Player player) {
        super(block, player);
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
            int count = 0;

            for (ItemStack itemStack : myInventory.getContents()) {
                if (Objects.nonNull(itemStack)) {
                    itemStack.setAmount(0);
                    count++;
                }
            }
            assert myInventory.getContents().length == 0;

            myInventory.addItem(Coin.createItemStack(count));
            isClosed = false;
        }, 20);
    }
}
