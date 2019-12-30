package com.ware.cleanstrike.model;

import com.ware.cleanstrike.model.Carrom;
import com.ware.cleanstrike.model.Outcome;

import java.util.Vector;

import static com.ware.cleanstrike.model.Constants.*;

public class Player{
    public String playername;
    int playerscore;
    int foulcount;
    Vector<Boolean> last3score;

    public Player(int playerscore, int foulcount, Vector<Boolean> last3score){
        this.playerscore = playerscore;
        this.foulcount = foulcount;
        this.last3score = last3score;
    }
    public void calculateScore(Outcome outcome) {
        int score = 0;
        int numberofcoins = 1;
        switch (outcome.getStrike()) {
            case SINGLE_STRIKE:
                score = 1;
                break;
            case MULTI_STRIKE:
                score = 2;
                numberofcoins = 2;
                break;
            case RED_STRIKE:
                score = 3;
                break;
            case STRIKER_STRIKE:
                score = -1;
                numberofcoins = 0;
                foulcount++;
                break;
            case DEFUNCT_COIN:
                score = -2;
                foulcount++;
                break;
            case NO_STRIKE:
                if (!last3score.get(0) && !last3score.get(1) && !last3score.get(2)) {
                    score = -1;
                    foulcount++;
                } else
                    score = 0;
                numberofcoins = 0;
        }
        updateScore(score);
        //updateCarromCoins(numberofcoins, outcome.getCoincolor());
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
}
