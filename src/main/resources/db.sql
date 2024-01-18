CREATE DATABASE IF NOT EXISTS chatRoom;

USE chatRoom;

CREATE TABLE IF NOT EXISTS user (
    userName VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status INT
    );
