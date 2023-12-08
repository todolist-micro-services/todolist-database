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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
