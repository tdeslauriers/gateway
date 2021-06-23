DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user_role;

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    username VARCHAR(255),
    password VARCHAR(255),
    account_created DATE,
    enabled BOOLEAN,
    is_account_expired BOOLEAN,
    is_account_locked BOOLEAN,
    is_password_expired BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE role (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE user_role (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES role (id)
);