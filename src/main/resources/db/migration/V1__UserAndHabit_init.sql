DROP TABLE IF EXISTS `habit`;
DROP TABLE IF EXISTS `user`;


CREATE TABLE `user`
(
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `username`  VARCHAR(32) NOT NULL,
    `password`  VARCHAR(60) NOT NULL,
    `first_name` VARCHAR(64)  DEFAULT NULL,
    `last_name`  VARCHAR(64)  DEFAULT NULL,
    `email`     VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='user data';

CREATE TABLE `habit`
(
    `id`        INT                 NOT NULL AUTO_INCREMENT,
    `user_id`    INT                 NOT NULL,
    `name`      VARCHAR(128)        NOT NULL,
    `color_hex`  CHAR(7)             NOT NULL,
    `direction` ENUM ('ASC','DESC') NOT NULL,
    `days`      VARCHAR(13) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `userId` (`user_id`),
    CONSTRAINT `habit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='Habit data';