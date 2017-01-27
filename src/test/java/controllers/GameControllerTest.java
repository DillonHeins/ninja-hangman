package controllers;

import models.Game;
import models.HangmanUser;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by dillon on 2017/01/27.
 * GameControllerTest
 */
public class GameControllerTest {
    @Test
    public void testGameWasCreated() {
        HangmanUser hangmanUser = new HangmanUser("username", new byte[0], new byte[0]);
        Game game = new Game("word", "aeiou", hangmanUser);

        assertTrue(game.getWord().equals("word"));
    }
}
