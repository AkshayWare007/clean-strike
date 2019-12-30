package com.ware.cleanstrike.service;

import com.ware.cleanstrike.model.Outcome;
import com.ware.cleanstrike.model.Player;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class scoreComputingService {
    public void computeScore(Player player, HttpServletRequest request){
        Outcome outcome = getOutcome(request);
        player.calculateScore();
    }

    private Outcome getOutcome(HttpServletRequest request){
        String red = request.getParameter("redcoins");
        String black = request.getParameter("blackcoins");
        String striker = request.getParameter("striker");
        String defunct = request.getParameter("defunct");

        //TODO throw an exception if invalid input have entered and dont change the user
        //validate();

        if(red.equals("1")){
            return new Outcome("Red-strike", "RED",1 );
        }
        if(!black.equals("null")){
            int blackcoin = Integer.parseInt()
        }

    }
}
