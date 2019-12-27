package com.ware.cleanstrike.service;

import com.ware.cleanstrike.model.Carrom;

public class PlayCarrom {
    public void startGame(){
        Carrom carrom = new Carrom("Akshay","Ware");
        while(!hasCoinsExhausted()){
            System.out.println("Akshay, what coins have u pocketed ?");
            
        }
    }
}
