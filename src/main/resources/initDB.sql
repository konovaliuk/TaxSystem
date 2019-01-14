-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema tax_system
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tax_system
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tax_system` DEFAULT CHARACTER SET utf8 ;
USE `tax_system`;

-- -----------------------------------------------------
-- Table `tax_system`.`user_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`user_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20) NOT NULL,
  `description` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tax_system`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `surname` VARCHAR(20) NOT NULL,
  `patronymic` VARCHAR(20) NULL DEFAULT NULL,
  `email` VARCHAR(120) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `type_id` INT(11) NULL DEFAULT NULL,
  `deletion_mark` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `type_id_user_type_fk_idx` (`type_id` ASC),
  CONSTRAINT `u_type_id_user_type_fk`
    FOREIGN KEY (`type_id`)
    REFERENCES `tax_system`.`user_type` (`id`)
	ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 56
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tax_system`.`form_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`form_status` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(15) NOT NULL,
  `description` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tax_system`.`ownership_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`ownership_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tax_system`.`taxpayer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`taxpayer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL DEFAULT NULL,
  `registration_number` VARCHAR(12) NULL DEFAULT NULL,
  `email` VARCHAR(120) NULL DEFAULT NULL,
  `postcode` VARCHAR(10) NULL DEFAULT NULL,
  `ownership_type_id` INT(11) NULL DEFAULT NULL,
  `employee_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `employee_id_fk_idx` (`employee_id` ASC),
  INDEX `ownership_type_id_fk_idx` (`ownership_type_id` ASC),
  CONSTRAINT `t_employee_id_fk`
    FOREIGN KEY (`employee_id`)
    REFERENCES `tax_system`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `t_ownership_type_id_fk`
    FOREIGN KEY (`ownership_type_id`)
    REFERENCES `tax_system`.`ownership_type` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tax_system`.`feedback_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`feedback_form` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `taxpayer_id` INT(11) NULL DEFAULT NULL,
  `initiator_id` INT(11) NULL DEFAULT NULL,
  `reviewer_id` INT(11) NULL DEFAULT NULL,
  `description` VARCHAR(1000) CHARACTER SET utf8 NULL DEFAULT NULL,
  `reviewer_comment` VARCHAR(1000) NULL DEFAULT NULL,
  `creation_date` DATE NOT NULL,
  `last_modified_date` DATE NOT NULL,
  `status_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `taxpayer_id_fk_idx` (`taxpayer_id` ASC),
  INDEX `initiator_id_fk_idx` (`initiator_id` ASC),
  INDEX `reviewer_id_fk_idx` (`reviewer_id` ASC),
  INDEX `status_id_fk_idx` (`status_id` ASC),
  CONSTRAINT `csf_initiator_id_fk`
    FOREIGN KEY (`initiator_id`)
    REFERENCES `tax_system`.`user` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `csf_reviewer_id_fk`
    FOREIGN KEY (`reviewer_id`)
    REFERENCES `tax_system`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `csf_status_id_fk`
    FOREIGN KEY (`status_id`)
    REFERENCES `tax_system`.`form_status` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `csf_taxpayer_id_fk`
    FOREIGN KEY (`taxpayer_id`)
    REFERENCES `tax_system`.`taxpayer` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 47
DEFAULT CHARACTER SET = utf8;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- -----------------------------------------------------
-- Table `tax_system`.`tax_report_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tax_system`.`tax_report_form` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `quarter` INT(4) NULL DEFAULT NULL,
  `year` INT(11) NULL DEFAULT NULL,
  `taxpayer_id` INT(11) NOT NULL,
  `initiator_id` INT(11) NOT NULL,
  `reviewer_id` INT(11) NULL DEFAULT NULL,
  `reviewer_comment` VARCHAR(300) NULL DEFAULT NULL,
  `main_activity_income` DECIMAL(11,2) NULL DEFAULT NULL,
  `investment_income` DECIMAL(11,2) NULL DEFAULT NULL,
  `property_income` DECIMAL(11,2) NULL DEFAULT NULL,
  `main_activity_expenses` DECIMAL(11,2) NULL DEFAULT NULL,
  `investment_expenses` DECIMAL(11,2) NULL DEFAULT NULL,
  `property_expenses` DECIMAL(11,2) NULL DEFAULT NULL,
  `creation_date` DATE NOT NULL,
  `last_modified_date` DATE NULL DEFAULT NULL,
  `status_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `taxpayer_id_fk_idx` (`taxpayer_id` ASC),
  INDEX `initiator_id_fk_idx` (`initiator_id` ASC),
  INDEX `reviewer_id_fk_idx` (`reviewer_id` ASC),
  INDEX `status_id_fk_idx` (`status_id` ASC),
  CONSTRAINT `tf_initiator_id_fk`
    FOREIGN KEY (`initiator_id`)
    REFERENCES `tax_system`.`user` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `tf_reviewer_id_fk`
    FOREIGN KEY (`reviewer_id`)
    REFERENCES `tax_system`.`user` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `tf_status_id_fk`
    FOREIGN KEY (`status_id`)
    REFERENCES `tax_system`.`form_status` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `tf_taxpayer_id_fk`
    FOREIGN KEY (`taxpayer_id`)
    REFERENCES `tax_system`.`taxpayer` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 47
DEFAULT CHARACTER SET = utf8;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
