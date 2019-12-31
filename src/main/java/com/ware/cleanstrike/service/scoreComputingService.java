package com.ware.cleanstrike.service;

import com.ware.cleanstrike.model.Outcome;
import com.ware.cleanstrike.model.Player;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import static com.ware.cleanstrike.model.Constants.*;


@Service
public class scoreComputingService {

    public void computeScore(Player player, HttpServletRequest request){
        Outcome outcome = getOutcome(request);
        player.calculateScore(outcome);
    }

    private Outcome getOutcome(HttpServletRequest request){
        String red = request.getParameter("redcoins");
        String black = request.getParameter("blackcoins");
        String striker = request.getParameter("striker");
        String defunct = request.getParameter("defunct");

        //TODO throw an exception if invalid input have entered and dont change the user
        //validate();

        if(red.equals("1")){
            return new Outcome(RED_STRIKE, "RED",1 );
        }
        if(!black.equals("null")){
            int blackcoin = Integer.parseInt(black);
            if(blackcoin == 1)
                return new Outcome(SINGLE_STRIKE, "BLACK",1 );
            else
                return new Outcome(MULTI_STRIKE, "BLACK", blackcoin);
        }
        if(!striker.equals("null")){
            return new Outcome(STRIKER_STRIKE, "NULL", 0);
        }
        if(!defunct.equals("null")){
            return new Outcome(DEFUNCT_COIN, defunct, 1);
        }
        return new Outcome(NO_STRIKE, "NULL", 0);
    }
}
