package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by dillon on 2017/01/22.
 * POJO for a Hangman User
 */
@Entity
public class HangmanUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;
    private byte[] password;
    private byte[] salt;

    public HangmanUser() {}

    public HangmanUser(String username, byte[] password, byte[] salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
