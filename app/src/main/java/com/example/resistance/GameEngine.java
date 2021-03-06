package com.example.resistance;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class GameEngine implements Serializable {
    final ArrayList<String> players, spies, resistance, captainList;
    ArrayList<String> roundGoers; //list containing players who about to vote
    boolean[] roundVotes; //array that stores votes in one round
    byte[] history = new byte[5]; //array that stores info about which group (spies/resistance) scored the round
    int captainIndex = 0, currentRound = 0, captainSwitches = 0; //captain switches is the count of how many times captain switched (5 times and spies win the round)
    int[] roundsDistribution = new int[5]; //array containing info about how many players go to each round

    public GameEngine(@NonNull ArrayList<String> list) {
        this.players = list;
        this.spies = new ArrayList<>();
        this.resistance = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            history[i] = 0; //stands for null (or no one), indicating that no one won the round yet
            //positive byte means resistance won the round
            //negative byte means spies won the round
        }

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

    public int getCurrentRound() {
        return currentRound;
    }

    public void nextRound() {
        currentRound++;
    }

    public int getCurrentRoundLimit() {
        return roundsDistribution[currentRound];
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

    public void setRoundGoers(@NonNull ArrayList<String> roundGoers) {
        this.roundGoers = roundGoers;
        roundVotes = new boolean[roundGoers.size()];
    }

    /*
    * This method simply assigns roles to each player
    * */
    private void setRoles() {
        Random random = new Random(new Date().getTime()); // setting current time as a seed
        int i = 0, numberOfSpies = setRoundsGetSpies();
        String name;
        while (i < numberOfSpies) {
            name = this.players.get(random.nextInt(players.size()));
            if (!this.spies.contains(name)) { //if name's not repeated
                this.spies.add(name);
                i++;
            }
        }
        for (String player : players) {
            if (!this.spies.contains(player)) { //if player is not spy
                this.resistance.add(player);
            }
        }
    }

    public boolean anyWinner() {
        int r = 0, s = 0; //resistance, spies
        for (byte c : history) {
            if (c > 0) r++;
            else if (c < 0) s++;
        }
        return r >= 3 || s >= 3;
    }

    public String whoWon() {
        int r = 0, s = 0; //resistance, spies
        for (byte c : history) {
            if (c > 0) r++;
            else if (c < 0) s++;
        }
        if (r >= 3) return "Resistance";
        else return "Spies";
    }

    /*
    * 5-6 players = 2 spies
    * 7-9 players = 3 spies
    * 10 players = 4 spies
    * */
    private int setRoundsGetSpies() {
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

    /*
    * Returns true if the the round is star round
    * meaning that it requires two fails for spies to win
    * */
    public boolean isStarRound(int round) {
        return round == 3 && players.size() >= 7;
    }

    /*
    * This method returns true if round fails due to excessive switches
    * returns false otherwise
    * */
    public boolean captainSwitched() {
        if (captainSwitches == 5) {
            //round is after spies;
            history[getCurrentRound()] = -1;
            nextRound();
            return true;
        }
        else {
            captainSwitches++;
            return false;
        }

    }

    public void resetSwitches() {
        captainSwitches = 0;
    }

    /*
    * Calculates who won the round
    * based on votes
    * Updates the history
    * */
    public int calculateResult() {
        int fails = 0;
        for (boolean roundVote : roundVotes) {
            if (!roundVote) fails++; // if roundVotes[i] is false, we increment fails
        }

        if (fails == 0) resistanceWonTheRound();
        else {
            if (players.size() >= 7) {
                if (currentRound == 3) { //actually star round is round 4, but we are using 0-indexing here
                    if (fails >= 2) spiesWonTheRound();
                    else resistanceWonTheRound();
                }
                else spiesWonTheRound();
            }
            else spiesWonTheRound();
        }

        return fails;
    }

    private void spiesWonTheRound() {
        history[currentRound] = -1; //indicates that spies won the round
    }

    private void resistanceWonTheRound() {
        history[currentRound] = 1; //indicates that resistance won the round
    }
}
