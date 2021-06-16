package com.github.sowasvonbot.coin;

import com.mojang.authlib.GameProfile;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

public class Coin {


    public static ItemStack createItemStack(int amount) {
        ItemStack coin = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta) coin.getItemMeta();

        skullMeta.getPersistentDataContainer()
                .set(Constants.coinKey, PersistentDataType.STRING, "Coin");

        GameProfile gameProfile = new GameProfile(UUID.fromString(Constants.playerUUID), null);
        gameProfile.getProperties().put("textures", Constants.headProperty);

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

    public static ItemStack createItemStack() {
        return createItemStack(1);
    }

    public static boolean isCoin(ItemStack itemStack) {
        if (Objects.isNull(itemStack) || Objects.isNull(itemStack.getItemMeta()))
            return false;
        return itemStack.getItemMeta().getPersistentDataContainer()
                .has(Constants.coinKey, PersistentDataType.STRING);
    }

    public static ShapedRecipe getRecipe() {
        NamespacedKey recipeKey = NamespacedKey.fromString("coin_easy");

        ShapedRecipe coinRecipe = new ShapedRecipe(recipeKey, Coin.createItemStack());
        coinRecipe.shape("*A*", "ABA", "*A*");
        coinRecipe.setIngredient('A', Material.GOLD_INGOT);
        coinRecipe.setIngredient('B', Material.DIAMOND);

        return coinRecipe;
    }

    public static Recipe getCraftBackRecipe() {
        NamespacedKey recipeKey = NamespacedKey.fromString("coin_back");

        RecipeChoice recipeChoice = new CoinRecipeChoice();

        ShapelessRecipe backToGoldRecipe =
                new ShapelessRecipe(recipeKey, new ItemStack(Material.GOLD_INGOT, 4));
        backToGoldRecipe.addIngredient(recipeChoice);

        return backToGoldRecipe;
    }



}
