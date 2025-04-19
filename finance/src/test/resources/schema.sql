-- Drop and create chat table
DROP TABLE IF EXISTS chat;

CREATE TABLE chat (
    id INT NOT NULL,
    CONSTRAINT pk_chat_id PRIMARY KEY (id)
);

-- Drop and create icon table
DROP TABLE IF EXISTS icon;

CREATE TABLE icon (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    CONSTRAINT pk_icon_id PRIMARY KEY (id)
);

-- Drop and create user_management table
DROP TABLE IF EXISTS user_management;

CREATE TABLE user_management (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NULL,
    email VARCHAR(150) NULL,
    password VARCHAR(32) NULL,
    telegram_user_id INT NULL,
    language INT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (id)
);

-- Drop and create category table
DROP TABLE IF EXISTS category;

CREATE TABLE category (
   	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    user_id INT NOT NULL,
    icon_id INT NULL,
    CONSTRAINT pk_category_id PRIMARY KEY (id),
    CONSTRAINT fk_icon_id FOREIGN KEY (icon_id) REFERENCES icon(id),
    CONSTRAINT fk_category_user_id FOREIGN KEY (user_id) REFERENCES user_management(id)
);


-- Drop and create transaction table
DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    amount NUMERIC(19, 2) NULL,
    note VARCHAR(250) NULL,
    category_id INT NOT NULL,
    user_id INT NOT NULL,
    chat_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_transaction_id PRIMARY KEY (id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id),
    CONSTRAINT fk_chat_id FOREIGN KEY (chat_id) REFERENCES chat(id),
    CONSTRAINT fk_transaction_user_id FOREIGN KEY (user_id) REFERENCES user_management(id)
);