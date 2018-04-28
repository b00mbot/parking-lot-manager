-- -----------------------------------------------------
-- NOTE: Using ^ separator here after every SQL statement with the exception of those in
-- triggers as we want to use Spring Data to initialize the database.
-- -----------------------------------------------------


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 ^;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 ^;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES' ^;

-- -----------------------------------------------------
-- Schema parkinglot
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema parkinglot
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `parkinglot` DEFAULT CHARACTER SET utf8^;
USE `parkinglot`^;

-- -----------------------------------------------------
-- Table `parkinglot`.`GATE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`GATE` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  `createUser` VARCHAR(45) NOT NULL,
  `createDate` DATETIME NOT NULL,
  `updateUser` VARCHAR(45) NOT NULL,
  `updateDate` DATETIME NOT NULL,
  `updateReason` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB^;


-- -----------------------------------------------------
-- Table `parkinglot`.`PARKING_TICKET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`PARKING_TICKET` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  `gateId` INT NOT NULL,
  `createUser` VARCHAR(45) NOT NULL,
  `createDate` DATETIME NOT NULL,
  `updateUser` VARCHAR(45) NOT NULL,
  `updateDate` DATETIME NOT NULL,
  `updateReason` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `gateId_idx` (`gateId` ASC),
  CONSTRAINT `FK_PARKINGTICKET_GATE_ID`
    FOREIGN KEY (`gateId`)
    REFERENCES `parkinglot`.`GATE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB^;


-- -----------------------------------------------------
-- Table `parkinglot`.`GATE_HISTORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`GATE_HISTORY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gateId` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `updateUser` VARCHAR(45) NOT NULL,
  `updateDate` DATETIME NOT NULL,
  `updateReason` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_GATE_ID_idx` (`gateId` ASC),
  CONSTRAINT `FK_GATEHISTORY_GATE_ID`
    FOREIGN KEY (`gateId`)
    REFERENCES `parkinglot`.`GATE` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB^;


-- -----------------------------------------------------
-- Table `parkinglot`.`PARKING_TICKET_HISTORY`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`PARKING_TICKET_HISTORY` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ticketId` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `updateUser` VARCHAR(45) NOT NULL,
  `updateDate` VARCHAR(45) NOT NULL,
  `updateReason` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_TICKET_ID_idx` (`ticketId` ASC),
  CONSTRAINT `FK_PARKINGTICKETHISTORY_PARKINGTICKET_ID`
    FOREIGN KEY (`ticketId`)
    REFERENCES `parkinglot`.`PARKING_TICKET` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB^;

USE `parkinglot`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_INSERT` AFTER INSERT ON `GATE` FOR EACH ROW
BEGIN
	INSERT INTO `parkinglot`.`GATE_HISTORY` (gateId, status, updateUser, updateDate, updateReason) VALUES (NEW.id, NEW.status, NEW.updateUser, NEW.updateDate, NEW.updateReason);
END^;

USE `parkinglot`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_UPDATE` AFTER UPDATE ON `GATE` FOR EACH ROW
BEGIN
	INSERT INTO `parkinglot`.`GATE_HISTORY` (gateId, status, updateUser, updateDate, updateReason) VALUES (NEW.id, NEW.status, NEW.updateUser, NEW.updateDate, NEW.updateReason);
END^;

USE `parkinglot`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`ADD_TO_PARKING_TICKET_HISTORY_AFTER_INSERT` AFTER INSERT ON `PARKING_TICKET` FOR EACH ROW
BEGIN
	INSERT INTO `parkinglot`.`PARKING_TICKET_HISTORY` (ticketId, status, updateUser, updateDate, updateReason) VALUES (NEW.id, NEW.status, NEW.updateUser, NEW.updateDate, NEW.updateReason);
END^;

USE `parkinglot`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`ADD_TO_PARKING_TICKET_HISTORY_AFTER_UPDATE` AFTER UPDATE ON `PARKING_TICKET` FOR EACH ROW
BEGIN
	INSERT INTO `parkinglot`.`PARKING_TICKET_HISTORY` (ticketId, status, updateUser, updateDate, updateReason) VALUES (NEW.id, NEW.status, NEW.updateUser, NEW.updateDate, NEW.updateReason);
END^;

SET SQL_MODE=@OLD_SQL_MODE ^;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS ^;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS ^;