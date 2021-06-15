package com.github.sowasvonbot.trading_inputs;

import java.util.Arrays;
import java.util.List;

public class Constants {

    protected static final String tradePrefix = "[trade]";
    protected static final String inputPrefix = "[input]";
    protected static final String coinStorePrefix = "[coins]";

    protected static final String errorTradingLineNotFound = "No trading line found";

    protected static final List<String> tradingPrefixes =
            Arrays.asList(tradePrefix, inputPrefix, coinStorePrefix);
}
