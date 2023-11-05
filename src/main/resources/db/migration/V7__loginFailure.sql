CREATE TABLE login_failure
(
    `id`                 INT         NOT NULL AUTO_INCREMENT,
    `source_ip`           VARCHAR(32) NOT NULL,
    `wrong_attempts`     INT         NOT NULL,
    `block_datetime`     TIMESTAMP   NULL,
    `created_date`       TIMESTAMP   NOT NULL,
    `last_modified_date` TIMESTAMP   NOT NULL,
    `version`            BIGINT      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE (`source_ip`)
) ENGINE = InnoDB;