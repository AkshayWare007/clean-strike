package com.ware.cleanstrike.model;

import java.util.Vector;

import static com.ware.cleanstrike.model.Constants.*;

public class Player {
    public int playerscore;
    public int foulcount;
    public Vector<Boolean> last3score;
    Carrom carrom;

    public Player(int playerscore, int foulcount, Vector<Boolean> last3score, Carrom carrom) {
        this.playerscore = playerscore;
        this.foulcount = foulcount;
        this.last3score = last3score;
        this.carrom = carrom;
    }

    public void calculateScore(Outcome outcome) {
        int currentscore = 0;
        int numberofcoins = 1;
        switch (outcome.getStrike()) {
            case SINGLE_STRIKE:
                currentscore = 1;
                break;
            case MULTI_STRIKE:
                currentscore = 2;
                numberofcoins = 2;
                break;
            case RED_STRIKE:
                currentscore = 3;
                break;
            case STRIKER_STRIKE:
                currentscore = -1;
                numberofcoins = 0;
                foulcount++;
                break;
            case DEFUNCT_COIN:
                currentscore = -2;
                foulcount++;
                break;
            case NO_STRIKE:
                if (!last3score.get(0) && !last3score.get(1) && !last3score.get(2)) {
                    currentscore = -1;
                    foulcount++;
                } else
                    currentscore = 0;
                numberofcoins = 0;
        }
        updateScore(currentscore);
        updateCarromCoins(numberofcoins, outcome.getCoincolor());
        if (foulcount == 3) {
            this.playerscore -= 1;
            foulcount = 0;
        }
    }

    private void updateScore(int currentscore) {
        this.playerscore += currentscore;
        if (last3score.size() >= 3)
            last3score.remove(0);
        last3score.add(currentscore >= 1);
    }

    public void removeQueen() {
        carrom.redcoincount = 0;
        //TODO decrement it and if it becomes negative throw an error
    }

    public void updateCarromCoins(int numberofcoins, String coincolor) {
        if (coincolor.equals("BLACK")) {
            carrom.blackcoincount -= numberofcoins;
        } else if (coincolor.equals("RED")) {
            removeQueen();
        }
    }
}
