package services;

import com.google.inject.Inject;
import dao.GameDao;
import dao.HangmanUserDao;
import models.Game;
import models.HangmanUser;
import ninja.session.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by dillon on 2017/01/23.
 */
public class GameService {
    @Inject
    private GameDao gameDao;
    @Inject
    private HangmanUserDao hangmanUserDao;

    public static String getWord(String urlToRead) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return result.toString().toLowerCase();
    }

    public Long newGame(Session session) {
        HangmanUser hangmanUser = hangmanUserDao.getUserByUsername(session.get("username"));

        String word = getWord("http://www.setgetgo.com/randomword/get.php");

        Game game = new Game(word, "", hangmanUser);
        return gameDao.createGame(game);
    }
}
