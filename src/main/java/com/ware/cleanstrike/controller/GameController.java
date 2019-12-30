package com.ware.cleanstrike.controller;

import com.ware.cleanstrike.model.Player;
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

    @GetMapping("/startgame")
    public String startGame(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "nextplayer", defaultValue = "Akshay") String nextplayer, Model model){
        String player1name = request.getParameter("player1name");
        String player2name = request.getParameter("player2name");

        System.out.println(player1name);
        System.out.println(player2name);

        model.addAttribute("nextplayer", nextplayer);

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
    public String computeScore(HttpServletRequest request, @CookieValue(value = "nextplayer", defaultValue = "Akshay") String nextplayer, Model model) {
        Map<String, String> cookiemap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        Arrays.stream(cookies).forEach(c-> cookiemap.put(c.getName(), c.getValue()));

        if(cookiemap.get("nextplayer").equals(cookiemap.get("player1name"))){
            int score = Integer.parseInt(cookiemap.get("player1score"));
            int foulcount = Integer.parseInt(cookiemap.get("player1foulcount"));
            Player player = new Player(score, foulcount, clientscore.get("player1name"));

        }
        System.out.println(request.getParameter("redcoins"));
        System.out.println(request.getParameter("blackcoins"));
        model.addAttribute("nextplayer", nextplayer);
        return "getInput";
    }
}
