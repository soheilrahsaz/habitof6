ALTER TABLE `user`
    ADD `created_date`       TIMESTAMP NOT NULL AFTER `email`,
    ADD `last_modified_date` TIMESTAMP NOT NULL AFTER `created_date`,
    ADD `version`            BIGINT    NOT NULL AFTER `last_modified_date`;

ALTER TABLE `habit`
    ADD `created_date`       TIMESTAMP NOT NULL AFTER `days`,
    ADD `last_modified_date` TIMESTAMP NOT NULL AFTER `created_date`,
    ADD `version`            BIGINT    NOT NULL AFTER `last_modified_date`;