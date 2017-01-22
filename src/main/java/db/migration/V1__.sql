CREATE TABLE HangmanUser (
    id serial PRIMARY KEY,
    password bytea,
    salt bytea,
    username varchar(255) UNIQUE NOT NULL
);

create sequence hibernate_sequence;