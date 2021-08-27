package com.example.resistance;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class GameEngine implements Serializable {
    public final ArrayList<String> players, spies, resistance, captainList;
    int captainIndex = 0;
    int currentRound = 0;
    int[] roundsDistribution = new int[5];

    public GameEngine(@NonNull ArrayList<String> list) {
        this.players = list;
        this.spies = new ArrayList<String>();
        this.resistance = new ArrayList<String>();

        setRoles();

        // we copy players list and shuffle it to make captains list
        captainList = new ArrayList<>(list);
        Random random = new Random(new Date().getTime());
        Collections.shuffle(captainList, random);
    }

    public String getNextCaptain() {
        if (captainIndex == captainList.size()) {
            captainIndex = 0;
        }
        return captainList.get(captainIndex++);
    }
    
    public ArrayList<String> getPlayersCopy() {
        return new ArrayList<>(this.players);
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<String> getSpies() {
        return this.spies;
    }

    public ArrayList<String> getResistance() {
        return this.resistance;
    }

    public ArrayList<String> getCaptainList() {
        return captainList;
    }

    /*
    * This method simply assigns roles to each player
    * */
    private void setRoles() {
        Random random = new Random(new Date().getTime()); // setting current time as a seed
        int i = 0, numberOfSpies = setRoundsGetSpies(players.size());
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

    public int getCurrentRound() {
        return currentRound;
    }

    public void nextRound() {
        currentRound++;
    }

    public int getCurrentRoundLimit() {
        return roundsDistribution[currentRound];
    }

    /*
    * 5-6 players = 2 spies
    * 7-9 players = 3 spies
    * 10 players = 4 spies
    * */
    private int setRoundsGetSpies(int size) {
        int spies;
        if (players.size() == 5) {
            spies = 2;
            roundsDistribution[0] = 2;
            roundsDistribution[1] = 3;
            roundsDistribution[2] = 2;
            roundsDistribution[3] = 3;
            roundsDistribution[4] = 3;
        }
        else if (players.size() == 6) {
            spies = 2;
            roundsDistribution[0] = 2;
            roundsDistribution[1] = 3;
            roundsDistribution[2] = 4;
            roundsDistribution[3] = 3;
            roundsDistribution[4] = 4;
        }
        else if (players.size() == 7) { // from 7 people and more, 4th round is a star round(requires two fails)
            spies = 3;
            roundsDistribution[0] = 2;
            roundsDistribution[1] = 3;
            roundsDistribution[2] = 3;
            roundsDistribution[3] = 4;
            roundsDistribution[4] = 4;
        }
        else if (players.size() == 8 || players.size() == 9) {
            spies = 3;
            roundsDistribution[0] = 3;
            roundsDistribution[1] = 4;
            roundsDistribution[2] = 4;
            roundsDistribution[3] = 5;
            roundsDistribution[4] = 5;
        }
        else {
            spies = 4;
            roundsDistribution[0] = 3;
            roundsDistribution[1] = 4;
            roundsDistribution[2] = 4;
            roundsDistribution[3] = 5;
            roundsDistribution[4] = 5;
        }
        return spies;
    }
}
