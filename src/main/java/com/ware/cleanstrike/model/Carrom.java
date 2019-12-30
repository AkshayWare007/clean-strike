package com.ware.cleanstrike.model;

import lombok.Data;

@Data
public class Carrom {
    public int blackcoincount = 9;
    public int redcoincount = 1;
    Player firstplayer;
    Player secondplayer;

    public Carrom(String player1name, String player2name) {
        firstplayer.playername = player1name;
        secondplayer.playername = player2name;
    }

    public void removeQueen() {
        redcoincount = 0;
    }

    public void updateCarromCoins(int numberofcoins, String coincolor) {
        if (coincolor.equals("BLACK")) {
            this.blackcoincount -= numberofcoins;
        } else if (coincolor.equals("RED"))
            removeQueen();
    }
}
