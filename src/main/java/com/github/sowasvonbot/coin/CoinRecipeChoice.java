package com.github.sowasvonbot.coin;

import com.github.sowasvonbot.Main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.Objects;

public class CoinRecipeChoice extends RecipeChoice.ExactChoice {

    @Override public CoinRecipeChoice clone() {
        return new CoinRecipeChoice();
    }

    public CoinRecipeChoice() {
        super(new ItemStack(Material.PLAYER_HEAD));
    }

    @Override public boolean test(ItemStack t) {
        System.out.println("Hello");
        return Coin.isCoin(t);
    }

    @Override public boolean equals(Object obj) {
        return this==obj;
    }

    @Override public int hashCode() {
        int hash = 37 * 3 + Objects.hashCode(this.getChoices().get(0)) + Objects
                .hash(Coin.createItemStack());
        return hash;
    }


}
