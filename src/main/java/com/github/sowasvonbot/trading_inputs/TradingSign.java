package com.github.sowasvonbot.trading_inputs;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class TradingSign {

    private Block sign;
    private Player player;
    private String tradeLine;

    public TradingSign(Block sign, Player player, String tradeLine) {
        this.sign = sign;
        this.player = player;
        this.tradeLine = tradeLine;
    }

    public TradingSign() {
        sign = null;
    }

    public Block getSign() {
        return sign;
    }

    public Player getPlayer() {
        return player;
    }

    public String getTradeLine() {
        return tradeLine;
    }

    public boolean isValidTradingSign() {
        return Objects.nonNull(sign);
    }



    public static TradingSign fromSignBlock(Block sign, Player player, List<String> lines) {
        String tradeLine = getTradeLine(lines);
        if (tradeLine.equals(Constants.errorTradingLineNotFound))
            return new TradingSign();
        return new TradingSign(sign, player, tradeLine);
    }

    private static String getTradeLine(List<String> lines) {
        for (String line : lines) {
            if (Constants.tradingPrefixes.contains(line.toLowerCase()))
                return line;
        }
        return Constants.errorTradingLineNotFound;
    }

    @Override public int hashCode() {
        return player.hashCode();
    }

    @Override public boolean equals(Object obj) {
        if (!(obj instanceof TradingSign sign))
            return false;
        return sign.getSign().equals(this.sign);
    }
}
