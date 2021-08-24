package com.example.resistance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GameEngine {
    public final ArrayList<String> players, spies, resistance;

    public GameEngine(ArrayList<String> list) {
        this.players = list;
        this.spies = new ArrayList<String>();
        this.resistance = new ArrayList<String>();
        setRoles();
    }

    public ArrayList<String> getSpies() {
        return this.spies;
    }

    public ArrayList<String> getResistance() {
        return this.resistance;
    }

    private void setRoles() {
        Random random = new Random(new Date().getTime()); // setting current time as a seed
        int i = 0, numberOfSpies = getNumberOfSpies(players.size());
        String name;
        while (i < numberOfSpies) {
            name = this.players.get(random.nextInt(players.size()));
            if (!this.spies.contains(name)) { //if it's not repeated
                this.spies.add(name);
                i++;
            }
        }
        for (String player : players) {
            if (!this.spies.contains(player)) { //if players is not spy
                this.resistance.add(player);
            }
        }
    }

    /*
    * 5-6 players = 2 spies
    * 7-8 players = 3 spies
    * 9 players = 4 spies
    * */
    private int getNumberOfSpies(int size) {
        int spies;
        if (size < 7) {
            spies = 2;
        }
        else if (size < 9) {
            spies = 3;
        }
        else spies = 4;

        return spies;
    }
}
