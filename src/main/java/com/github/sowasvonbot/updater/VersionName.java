package com.github.sowasvonbot.updater;


import java.util.ArrayList;
import java.util.List;

public record VersionName(int first_digit, int second_digit, int third_digit)
        implements Comparable<VersionName> {

    public int getFirst_digit() {
        return first_digit;
    }

    public int getSecond_digit() {
        return second_digit;
    }

    public int getThird_digit() {
        return third_digit;
    }

    public static VersionName fromString(String toParse) {
        toParse = toParse.replace(" ", "");
        toParse = toParse.replace("v", "");

        List<Integer> tmpList = new ArrayList<>();
        try {

            for (String temp : toParse.split("\\.")) {
                tmpList.add(Integer.parseInt(temp));
            }
            assert tmpList.size() == 3;
        } catch (NumberFormatException | AssertionError e) {
            throw new IllegalArgumentException(
                    String.format("The String %s is not a valid version number", toParse));
        }
        return new VersionName(tmpList.get(0), tmpList.get(1), tmpList.get(2));
    }

    @Override public int compareTo(VersionName o) {
        return (first_digit - o.first_digit) * 10000 + (second_digit - o.second_digit) * 100 + (third_digit - o.third_digit);
    }

    @Override public String toString() {
        return String.format("v%d.%d.%d", first_digit, second_digit, third_digit);
    }
}
