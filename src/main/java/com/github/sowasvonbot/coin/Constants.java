package com.github.sowasvonbot.coin;

import com.github.sowasvonbot.Main;
import com.mojang.authlib.properties.Property;
import org.bukkit.NamespacedKey;

public abstract class Constants {

    protected static final String url =
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzM5MGM3MWNjOTQ3YWFiYzA1NzU4ZjNjNDk5NGEzMWZiNTgyMWZkNTE2ZjExYjBkOWUxOWU5YzQ3MWE2NDZmNyJ9fX0=";

    //protected static final String url =
    //        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ1ZjQ3ZmViNGQ3NWNiMzMzOTE0YmZkYjk5OWE0ODljOWQwZTMyMGQ1NDhmMzEwNDE5YWQ3MzhkMWUyNGI5In19fQ==";

    protected static final Property headProperty = new Property("textures", url);

    protected static final NamespacedKey coinKey = new NamespacedKey(Main.getInstance(), "coin");

    protected static final String playerUUID = "c7b55c30-93db-43b2-a108-47eae22a2a4a";
}
