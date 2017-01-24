CREATE TABLE HangmanUser (
    id SERIAL PRIMARY KEY,
    password BYTEA,
    salt BYTEA,
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Game (
  id SERIAL PRIMARY KEY,
  hangmanuser_id INT REFERENCES HangmanUser(id),
  word TEXT,
  guesses TEXT
);

create sequence hibernate_sequence;