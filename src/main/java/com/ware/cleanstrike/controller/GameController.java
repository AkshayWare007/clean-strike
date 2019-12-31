package com.ware.cleanstrike.controller;

import com.ware.cleanstrike.model.Carrom;
import com.ware.cleanstrike.model.Player;
import com.ware.cleanstrike.service.scoreComputingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Controller
public class GameController {
    Map<String, Vector<Boolean>> clientscore = new HashMap<>();

    @Autowired
    scoreComputingService scoreComputingService;

    @GetMapping("/startgame")
    public String startGame(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "nextplayer", defaultValue = "Akshay") String nextplayer, Model model){
        String player1name = request.getParameter("player1name");
        String player2name = request.getParameter("player2name");

        clientscore.put("player1name", new Vector<>(3));
        clientscore.put("player2name", new Vector<>(3));

        model.addAttribute("nextplayer", player1name);

        response.addCookie(new Cookie("player1name", player1name));
        response.addCookie(new Cookie("player1score", "0"));
        response.addCookie(new Cookie("player1foulcount", "0"));

        response.addCookie(new Cookie("player2name", player2name));
        response.addCookie(new Cookie("player2score", "0"));
        response.addCookie(new Cookie("player2foulcount", "0"));

        response.addCookie(new Cookie("blackcoincount","9"));
        response.addCookie(new Cookie("redcoincount","1"));

        response.addCookie(new Cookie("nextplayer", request.getParameter("player1name")));
        return "getInput";
    }

    @GetMapping("/play")
    public String computeScore(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "nextplayer", defaultValue = "Akshay") String nextplayer, Model model) {
        Map<String, String> cookiemap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        Arrays.stream(cookies).forEach(c-> cookiemap.put(c.getName(), c.getValue()));

        int blackcoincount = Integer.parseInt(cookiemap.get("blackcoincount"));
        int redcoincount = Integer.parseInt(cookiemap.get("redcoincount"));
        Carrom carrom = new Carrom(blackcoincount, redcoincount);

        if(nextplayer.equals(cookiemap.get("player2name"))){
            int score = Integer.parseInt(cookiemap.get("player1score"));
            int foulcount = Integer.parseInt(cookiemap.get("player1foulcount"));
            Player player = new Player(score, foulcount, clientscore.get("player1name"), carrom);
            scoreComputingService.computeScore(player, request);
            clientscore.put("player1name", player.last3score);
            model.addAttribute("nextplayer", cookiemap.get("player2name"));
            response.addCookie(new Cookie("player1score", String.valueOf(player.playerscore)));
            response.addCookie(new Cookie("player1foulcount", String.valueOf(player.foulcount)));
        }
        else if(nextplayer.equals(cookiemap.get("player1name"))) {
            int score = Integer.parseInt(cookiemap.get("player2score"));
            int foulcount = Integer.parseInt(cookiemap.get("player2foulcount"));
            Player player = new Player(score, foulcount, clientscore.get("player2name"), carrom);
            scoreComputingService.computeScore(player, request);
            clientscore.put("player2name", player.last3score);
            model.addAttribute("nextplayer", cookiemap.get("player1name"));
            response.addCookie(new Cookie("player2score", String.valueOf(player.playerscore)));
            response.addCookie(new Cookie("player2foulcount", String.valueOf(player.foulcount)));
        }
        response.addCookie(new Cookie("blackcoincount", String.valueOf(carrom.blackcoincount)));
        response.addCookie(new Cookie("redcoincount", String.valueOf(carrom.redcoincount)));
        //TODO Check player has won
        return "getInput";
    }
}
