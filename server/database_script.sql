#Users Database
DROP USER IF EXISTS 'dbuser'@'localhost';
CREATE USER IF NOT EXISTS 'dbuser'@'localhost' IDENTIFIED BY 'MyPasswordIsMoreSecureThanYoursBET';

DROP DATABASE IF EXISTS savethecity;
CREATE DATABASE IF NOT EXISTS savethecity;
USE savethecity;



CREATE TABLE accounts(
    device_hash CHAR(60) NOT NULL PRIMARY KEY,
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

);



CREATE TABLE permissions(
    id TINYINT NOT NULL PRIMARY KEY AUTO_INCREMENT,             #Int identfier for permission level
    name VARCHAR(255) NOT NULL                                  #String for name of permission EX: Can Edit Home Page Carousel
);

CREATE TABLE group_permissions(
    groups_id TINYINT NOT NULL,
    permission_id TINYINT NOT NULL,
    FOREIGN KEY (groups_id) REFERENCES groups(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id),
    PRIMARY KEY (groups_id,permission_id)
);

CREATE TABLE pictures(
    id CHAR(40) NOT NULL,
    owner_id CHAR(36) NOT NULL,
    extension VARCHAR(4) NOT NULL,
    date_uploaded DATE NOT NULL,
    file_size INT UNSIGNED NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES accounts(id) ON DELETE CASCADE,
    PRIMARY KEY (id, owner_id)
);

CREATE TABLE picture_tags(
    tag VARCHAR(255) NOT NULL,
    picture_id CHAR(40) NOT NULL,
    picture_owner_id CHAR(36) NOT NULL DEFAULT "StockPhotos_StockPhotos_StockPhotos_",
    FOREIGN KEY (picture_id, picture_owner_id) REFERENCES pictures(id, owner_id),
    PRIMARY KEY (tag, picture_id)
);

CREATE TABLE template_categories(
    id SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL DEFAULT "",
    priority TINYINT UNSIGNED NOT NULL DEFAULT 255
);

CREATE TABLE templates(
    id SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,   #is folder name
    name VARCHAR(255) NOT NULL,
    category_id SMALLINT UNSIGNED NOT NULL,
    strict BOOLEAN NOT NULL,
    font VARCHAR(255) DEFAULT NULL,
    bg_color BINARY(3) DEFAULT NULL,
    FOREIGN KEY (category_id) REFERENCES template_categories(id)
);

CREATE TABLE template_pages(
    id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    template_id SMALLINT UNSIGNED NOT NULL,                     #is folder name
    number SMALLINT UNSIGNED DEFAULT 0,                         #page number, 0 if non-strict (layout)
    name VARCHAR(255) NOT NULL,                                 #is parsed into file name (no spaces, etc.)
    FOREIGN KEY (template_id) REFERENCES templates(id) ON DELETE CASCADE
);

CREATE TABLE stories(
    id CHAR(36) NOT NULL PRIMARY KEY,
    template_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    owner_id CHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    strict BOOLEAN NOT NULL,
    font VARCHAR(255) DEFAULT NULL,
    bg_color BINARY(3) DEFAULT NULL,
    is_published BOOLEAN NOT NULL DEFAULT FALSE,
    date_created DATE NOT NULL,
    last_modified DATE NOT NULL,
    FOREIGN KEY (template_id) REFERENCES templates(id) ON DELETE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES accounts(id) ON DELETE CASCADE
);

CREATE TABLE contributors(
    story_id CHAR(36) NOT NULL,
    shared_with_id CHAR(36) NOT NULL,
    is_contributor BOOLEAN NOT NULL,
    page_access VARCHAR(255) Default NULL,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    FOREIGN KEY (shared_with_id) REFERENCES accounts(id) ON DELETE CASCADE,
    PRIMARY KEY (story_id, shared_with_id)
);


CREATE TABLE pages(  #stores non-strict pages in story, used to change layouts. Strict pages just use the page in template_pages directly
    layout_id INT UNSIGNED DEFAULT NULL,  #refers to a specific layout by its id, null if blank
    story_id CHAR(36) NOT NULL,
    page_number SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (layout_id) REFERENCES template_pages(id) ON DELETE CASCADE,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    PRIMARY KEY (story_id, page_number)
);

CREATE TABLE default_text_fields(
    template_id SMALLINT UNSIGNED NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(31) NOT NULL DEFAULT "text",  #currently only text, other options may be added later
    max_size SMALLINT UNSIGNED NOT NULL DEFAULT 1022,  #max length in characters
    layout_id INT UNSIGNED DEFAULT NULL,  #refers to a specific layout by its id, used only if unique to a specific page
    FOREIGN KEY (layout_id) REFERENCES template_pages(id) ON DELETE CASCADE,
    FOREIGN KEY (template_id) REFERENCES templates(id) ON DELETE CASCADE,
    UNIQUE (template_id, name, layout_id)
);

CREATE TABLE text_fields(
    story_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(1022) NOT NULL DEFAULT "",
    type VARCHAR(31) NOT NULL DEFAULT "text",  #currently only text, other options may be added later
    max_size SMALLINT UNSIGNED NOT NULL DEFAULT 1022,  #max length in characters
    page SMALLINT UNSIGNED DEFAULT NULL,  #refers to a specific page by number, used only if unique to a specific page
    FOREIGN KEY (story_id, page) REFERENCES pages(story_id, page_number) ON DELETE CASCADE,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    UNIQUE (story_id, name, page)
);


