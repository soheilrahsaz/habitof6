ALTER TABLE `user` ADD UNIQUE(`username`);
#don't need this index anymore
ALTER TABLE `user` DROP INDEX `username`;

ALTER TABLE `user` ADD UNIQUE(`email`);