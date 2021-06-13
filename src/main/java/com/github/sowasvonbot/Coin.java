package com.github.sowasvonbot;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class Coin {

    static final String url =
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBhN2I5NGM0ZTU4MWI2OTkxNTlkNDg4NDZlYzA5MTM5MjUwNjIzN2M4OWE5N2M5MzI0OGEwZDhhYmM5MTZkNSJ9fX0=";
    private static final Property headProperty = new Property("textures", url);

    public static ItemStack createItemStack() {
        ItemStack coin = new ItemStack(Material.PLAYER_HEAD, 1);
        coin.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) coin.getItemMeta();

        GameProfile gameProfile =
                new GameProfile(UUID.fromString("c7b55c30-93db-43b2-a108-47eae22a2a4a"), null);
        gameProfile.getProperties().put("textures", headProperty);

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skullMeta.setDisplayName("Coin");
        coin.setItemMeta(skullMeta);
        return coin;
    }

    public static boolean isCoin(ItemStack itemStack) {
        if (itemStack.getType() != Material.PLAYER_HEAD)
            return false;

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            GameProfile gameProfile = (GameProfile) profileField.get(skullMeta);

            return gameProfile.getProperties().get("textures").stream()
                    .anyMatch(property -> property.getValue().equals(url));
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            return false;
        }

    }

    public static ShapedRecipe getRecipe() {
        NamespacedKey recipeKey = NamespacedKey.fromString("coin_easy");

        ShapedRecipe coinRecipe = new ShapedRecipe(recipeKey, Coin.createItemStack());
        coinRecipe.shape("*A*", "ABA", "*A*");
        coinRecipe.setIngredient('A', Material.GOLD_INGOT);
        coinRecipe.setIngredient('B', Material.DIAMOND);
        return coinRecipe;
    }


}
