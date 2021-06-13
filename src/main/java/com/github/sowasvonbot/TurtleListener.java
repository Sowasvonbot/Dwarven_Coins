package com.github.sowasvonbot;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class TurtleListener implements Listener {

    Map<LivingEntity, Float> rotationMap;
    private static Random random = new Random();

    @EventHandler public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof LivingEntity))
            return;
        ItemStack holdedItem = event.getPlayer().getInventory().getItemInMainHand();

        if (!holdedItem.getType().toString().toLowerCase().contains("pickaxe"))
            return;

        if (Objects.isNull(rotationMap))
            rotationMap = new HashMap<>();


        LivingEntity miningTurtle = (LivingEntity) event.getRightClicked();

        rotationMap.put(miningTurtle, 0f);
        miningTurtle.setCustomName("Mining turtle " + holdedItem.getType());
        miningTurtle.setCustomNameVisible(true);

        holdedItem.getItemMeta().setUnbreakable(true);

        miningTurtle.setCanPickupItems(true);
        miningTurtle.setAI(false);

        Random random = new Random();

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            RayTraceResult rayTraceResult = miningTurtle.rayTraceBlocks(2);
            miningTurtle.setAI(true);
            if (Objects.isNull(rayTraceResult)) {
                rotateAndMove(miningTurtle);
                return;
            }
            Block block = rayTraceResult.getHitBlock();
            if (Objects.isNull(block)) {
                rotateAndMove(miningTurtle);
                return;
            }
            Location location = block.getLocation().subtract(miningTurtle.getLocation());
            miningTurtle.setVelocity(location.toVector());

            if (block.isPreferredTool(holdedItem)) {
                block.breakNaturally(holdedItem);
                block = block.getRelative(0, 1, 0);
                if (!Objects.isNull(block) && block.isPreferredTool(holdedItem))
                    block.breakNaturally(holdedItem);
            }
        }, 0, 1);

    }

    public void rotateAndMove(LivingEntity miningTurtle) {
        float y = 0f;
        rotationMap.replace(miningTurtle, (rotationMap.get(miningTurtle) + 1f) % 360);
        miningTurtle.setRotation(rotationMap.get(miningTurtle), 0);
        if (miningTurtle.getRemainingAir() > 1)
            y = -0.1f;

        if (rotationMap.get(miningTurtle) == 0.f)
            miningTurtle.setVelocity(
                    new Vector(random.nextFloat() - 0.5f, y, random.nextFloat() - 0.5f));
    }
}
