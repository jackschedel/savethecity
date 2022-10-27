#Users Database
DROP USER IF EXISTS 'dbuser'@'localhost';
CREATE USER IF NOT EXISTS 'dbuser'@'localhost' IDENTIFIED BY 'MyPasswordIsMoreSecureThanYoursBET';

DROP DATABASE IF EXISTS savethecity;
CREATE DATABASE IF NOT EXISTS savethecity;
USE savethecity;



CREATE TABLE accounts(

    id CHAR(60) NOT NULL PRIMARY KEY,
	lights_wins INT UNSIGNED NOT NULL DEFAULT 1,
	lights_losses INT UNSIGNED NOT NULL DEFAULT 1,
	lights_meter FLOAT UNSIGNED NOT NULL DEFAULT 0,
	
	lights_nextdiff FLOAT UNSIGNED NOT NULL DEFAULT 0.2,
	
	
	water_wins INT UNSIGNED NOT NULL DEFAULT 1,
	water_losses INT UNSIGNED NOT NULL DEFAULT 1,
	water_meter FLOAT UNSIGNED NOT NULL DEFAULT 0,
	
	water_nextdiff FLOAT UNSIGNED NOT NULL DEFAULT 0.2,
	
	
	trash_wins INT UNSIGNED NOT NULL DEFAULT 1,
	trash_losses INT UNSIGNED NOT NULL DEFAULT 1,
	trash_meter FLOAT UNSIGNED NOT NULL DEFAULT 0,
	
	trash_nextdiff FLOAT UNSIGNED NOT NULL DEFAULT 0.2
	
);


CREATE TABLE game_records(
	account_id CHAR(60) NOT NULL,
	game_played ENUM('lights', 'water', 'trash') NOT NULL,
	diff_played FLOAT UNSIGNED NOT NULL,
	winrate FLOAT UNSIGNED NOT NULL,
	meter FLOAT UNSIGNED NOT NULL,
	did_win BOOLEAN NOT NULL

);

