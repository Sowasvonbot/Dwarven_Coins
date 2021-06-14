package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShieldListener implements Listener {

    @EventHandler public void changeSignEvent(SignChangeEvent event) {
        handleTradeSign(event.getBlock(), Arrays.asList(event.getLines()));
    }

    @EventHandler public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().name().toLowerCase().contains("sign"))
            return;

        getSurroundingBlocks(event.getBlock())
                .forEach(block -> Main.getMainLogger().info(block.getType().name()));
    }

    private void handleTradeSign(Block sign, List<String> lines) {
        if (sign.getState() instanceof Sign)
            Main.getMainLogger().info("A sign");
        if (!getSurroundingBlocks(sign).stream()
                .anyMatch(blockState -> blockState instanceof Chest))
            return;
        if (!lines.contains("trade"))
            return;
        Main.getMainLogger().info(String
                .format("Trade sign at x:%d, y:%d, z:%d", sign.getX(), sign.getY(), sign.getZ()));
    }

    private List<BlockState> getSurroundingBlocks(@Nonnull Block block) {
        List<BlockState> boundingBlocks = new ArrayList<>();

        boundingBlocks.add(block.getRelative(0, 0, 1).getState());
        boundingBlocks.add(block.getRelative(0, 0, -1).getState());
        boundingBlocks.add(block.getRelative(1, 0, 0).getState());
        boundingBlocks.add(block.getRelative(-1, 0, 0).getState());
        return boundingBlocks;
    }
}
