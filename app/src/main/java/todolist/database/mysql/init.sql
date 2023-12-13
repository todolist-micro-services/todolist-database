-- MySQL Script generated by MySQL Workbench
-- Thu 07 Dec 2023 10:27:49 PM PST
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS todolistMicroServices;
CREATE SCHEMA todolistMicroServices;
USE todolistMicroServices;
-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User` ;

CREATE TABLE IF NOT EXISTS `User` (
    `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `firstname` VARCHAR(255) NOT NULL,
    `lastname` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Token` ;

CREATE TABLE IF NOT EXISTS `Token` (
    `token_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `jwt_value` VARCHAR(255) NOT NULL,
    `expiration_date` DATETIME NOT NULL,
    `user` INT UNSIGNED NOT NULL,
    `is_activate` TINYINT NOT NULL,
    PRIMARY KEY (`token_id`),
    UNIQUE INDEX `token_id_UNIQUE` (`token_id` ASC) VISIBLE,
    UNIQUE INDEX `jwt_value_UNIQUE` (`jwt_value` ASC) VISIBLE,
    UNIQUE INDEX `user_UNIQUE` (`user` ASC) VISIBLE,
    CONSTRAINT `fk_token_user`
    FOREIGN KEY (`user`)
    REFERENCES `User` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project` ;

CREATE TABLE IF NOT EXISTS `Project` (
    `project_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `creation_date` DATETIME NOT NULL,
    `creator` INT UNSIGNED NULL,
    PRIMARY KEY (`project_id`),
    UNIQUE INDEX `project_id_UNIQUE` (`project_id` ASC) VISIBLE,
    CONSTRAINT `fk_project_creator`
    FOREIGN KEY (`creator`)
    REFERENCES `User` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `List`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `List` ;

CREATE TABLE IF NOT EXISTS `List` (
    `list_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `parent` INT UNSIGNED NULL,
    `creator` INT UNSIGNED NULL,
    `project` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`list_id`),
    UNIQUE INDEX `list_id_UNIQUE` (`list_id` ASC) VISIBLE,
    CONSTRAINT `fk_list_creator`
    FOREIGN KEY (`creator`)
    REFERENCES `User` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT `fk_list_parent`
    FOREIGN KEY (`parent`)
    REFERENCES `List` (`list_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_list_project`
    FOREIGN KEY (`project`)
    REFERENCES `Project` (`project_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Event` ;

CREATE TABLE IF NOT EXISTS `Event` (
    `event_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `start_date` DATETIME NOT NULL,
    `end_date` DATETIME NOT NULL,
    `creator` INT UNSIGNED NULL,
    `project` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`event_id`),
    UNIQUE INDEX `event_id_UNIQUE` (`event_id` ASC) VISIBLE,
    CONSTRAINT `fk_event_creator`
    FOREIGN KEY (`creator`)
    REFERENCES `User` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT `fk_event_project`
    FOREIGN KEY (`project`)
    REFERENCES `Project` (`project_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Task` ;

CREATE TABLE IF NOT EXISTS `Task` (
    `task_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
    `creation_date` DATETIME NOT NULL,
    `creator` INT UNSIGNED NULL,
    `list` INT UNSIGNED NOT NULL,
    PRIMARY KEY (`task_id`),
    UNIQUE INDEX `task_id_UNIQUE` (`task_id` ASC) VISIBLE,
    CONSTRAINT `fk_task_creator`
    FOREIGN KEY (`creator`)
    REFERENCES `User` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    CONSTRAINT `fk_task_list`
    FOREIGN KEY (`list`)
    REFERENCES `List` (`list_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Trigger mydb
-- -----------------------------------------------------
DROP TRIGGER IF EXISTS delete_project;

DELIMITER $$

CREATE TRIGGER delete_project_element BEFORE DELETE ON Project
FOR EACH ROW
BEGIN
	DELETE FROM List WHERE List.project = OLD.project_id;
	DELETE FROM Event WHERE Event.project = OLD.project_id;
END $$

DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
