CREATE TABLE day_done
(
    `id`                 INT                  NOT NULL AUTO_INCREMENT,
    `habit_id`           INT                  NOT NULL,
    `date`               DATE                 NOT NULL,
    `type`               ENUM ('DONE','SKIP') NOT NULL,
    `created_date`       TIMESTAMP            NOT NULL,
    `last_modified_date` TIMESTAMP            NOT NULL,
    `version`            BIGINT               NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `day_done_ibfk_1` FOREIGN KEY (`habit_id`) REFERENCES `habit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB;