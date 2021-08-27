package com.example.resistance;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;

public class GameEngineTest extends TestCase {
    @Test
    public void testSetRoles(){
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        GameEngine game = new GameEngine(list);
        assertEquals(5, game.players.size());
        assertEquals(2, game.spies.size());
        assertEquals(3, game.resistance.size());

        for (String spy : game.spies) {
            assertFalse(game.resistance.contains(spy)); //make sure resistance contains no spies
        }
        for (String member : game.resistance) {
            assertFalse(game.spies.contains(member)); //make sure spies contain no resistance
        }
    }

    @Test
    public void testGetNextCaptain() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        GameEngine game = new GameEngine(list);

        try {
            assertEquals(game.getCaptainList().get(0), game.getNextCaptain());
            assertEquals(game.getCaptainList().get(1), game.getNextCaptain());
            assertEquals(game.getCaptainList().get(2), game.getNextCaptain());
            assertEquals(game.getCaptainList().get(3), game.getNextCaptain());
            assertEquals(game.getCaptainList().get(4), game.getNextCaptain());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }

}