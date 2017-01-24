package models;

import javax.persistence.*;

/**
 * Created by dillon on 2017/01/24.
 * Game
 */
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String word;
    private String guesses;

    @ManyToOne
    private HangmanUser hangmanUser;

    public Game() {}

    public Game(String word, String guesses, HangmanUser hangmanUser) {
        this.word = word;
        this.guesses = guesses;
        this.hangmanUser = hangmanUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getGuesses() {
        return guesses;
    }

    public void setGuesses(String guesses) {
        this.guesses = guesses;
    }

    public HangmanUser getHangmanUser() {
        return hangmanUser;
    }

    public void setHangmanUser(HangmanUser hangmanUser) {
        this.hangmanUser = hangmanUser;
    }
}
