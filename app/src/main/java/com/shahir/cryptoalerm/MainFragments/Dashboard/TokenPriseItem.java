package com.shahir.cryptoalerm.MainFragments.Dashboard;

public class TokenPriseItem {

    private final String symbol, price_usd;

    public TokenPriseItem(String symbol, String price_usd) {
        this.symbol = symbol;
        this.price_usd = price_usd;
    }


    public String getSymbol() {
        return symbol;
    }

    public String getPrice_usd() {
        return price_usd;
    }
}
