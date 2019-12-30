package com.ware.cleanstrike.model;

import lombok.Data;

@Data
public class Outcome {
    String strike;
    String coincolor;
    int numberofcoins;

    public Outcome(String strike, String coincolor, int numberofcoins){
        this.strike = strike;
        this.coincolor = coincolor;
        this.numberofcoins = numberofcoins;
    }
}
