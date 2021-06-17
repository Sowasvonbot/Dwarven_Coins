package com.github.sowasvonbot.trading_inputs;

import com.github.sowasvonbot.Tuple;
import org.bukkit.Material;

import java.util.List;
import java.util.Objects;

public record Trade(Material material, int materialAmount, int coinAmount) {

    @Override public String toString() {
        return material + ": " + materialAmount + "\n" + "Coins: " + coinAmount;
    }

    public static Trade fromStringList(List<String> strings) {
        //if (strings.size() != 3)
        //    throw new TradeParseException("Invalid strings list: " + strings);

        String line2 = strings.get(1);
        String line3 = strings.get(2);

        line2 = line2.trim();
        line3 = line3.trim();

        String[] parseLine = line2.split(" ", 2);

        if (parseLine.length != 2)
            throw new TradeParseException("Line " + line2 + " has one argument missing");
        Tuple posAndInt = findIntegerInLine(parseLine);
        int materialAmount = posAndInt.x();


        Material material = Material.matchMaterial(parseLine[(posAndInt.y() + 1) % 2]);

        if (Objects.isNull(material))
            throw new TradeParseException(
                    "Material with name: " + parseLine[(posAndInt.y() + 1) % 2] + " not found!");

        posAndInt = findIntegerInLine(line3.split(" ", 2));

        return new Trade(material, materialAmount, posAndInt.x());

    }

    private static Tuple findIntegerInLine(String[] line) {
        int pos = 0;
        int lineInteger = 0;
        try {
            lineInteger = Integer.parseInt(line[pos]);
        } catch (NumberFormatException e) {
            try {
                pos++;
                lineInteger = Integer.parseInt(line[pos]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                throw new TradeParseException(
                        "Not able to find Integer in line " + String.join(" ", line));
            }
        }
        return new Tuple(lineInteger, pos);
    }


    public static class TradeParseException extends RuntimeException {
        public TradeParseException(String message) {
            super(message);
        }
    }
}
