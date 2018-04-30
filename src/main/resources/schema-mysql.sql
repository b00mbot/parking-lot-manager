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
  `createdBy` VARCHAR(45) NOT NULL,
  `created` DATETIME NOT NULL,
  `lastModifiedBy` VARCHAR(45) NOT NULL,
  `lastModified` DATETIME NOT NULL,
  `lastModifiedReason` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB^;


-- -----------------------------------------------------
-- Table `parkinglot`.`PARKING_TICKET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`PARKING_TICKET` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  `gateId` INT NOT NULL,
  `createdBy` VARCHAR(45) NOT NULL,
  `created` DATETIME NOT NULL,
  `lastModifiedBy` VARCHAR(45) NOT NULL,
  `lastModified` DATETIME NOT NULL,
  `lastModifiedReason` VARCHAR(45) NULL,
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
  `modifiedBy` VARCHAR(45) NOT NULL,
  `modified` DATETIME NOT NULL,
  `modifiedReason` VARCHAR(45) NULL,
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
  `modifiedBy` VARCHAR(45) NOT NULL,
  `modified` DATETIME NOT NULL,
  `modifiedReason` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_TICKET_ID_idx` (`ticketId` ASC),
  CONSTRAINT `FK_PARKINGTICKETHISTORY_PARKINGTICKET_ID`
    FOREIGN KEY (`ticketId`)
    REFERENCES `parkinglot`.`PARKING_TICKET` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB^;


-- -----------------------------------------------------
-- Table `parkinglot`.`PARKING_CAPACITY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `parkinglot`.`PARKING_CAPACITY`^;
CREATE TABLE `parkinglot`.`PARKING_CAPACITY` (
  `id` ENUM('1') NOT NULL,
  `currentCapacity` INT NOT NULL,
  `maxCapacity` INT NOT NULL,
  `lastModified` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB^;

INSERT INTO `parkinglot`.`PARKING_CAPACITY` VALUES ('1', 0, 10, NOW())^;

-- -----------------------------------------------------
-- Trigger `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_INSERT`
-- -----------------------------------------------------
USE `parkinglot`^;
DROP TRIGGER IF EXISTS `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_INSERT`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_INSERT` AFTER INSERT ON `GATE` FOR EACH ROW
BEGIN
	INSERT INTO `parkinglot`.`GATE_HISTORY` (gateId, status, modifiedBy, modified, modifiedReason) VALUES (NEW.id, NEW.status, NEW.lastModifiedBy, NEW.lastModified, NEW.lastModifiedReason);
END^;


-- -----------------------------------------------------
-- Trigger `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_UPDATE`
-- -----------------------------------------------------
USE `parkinglot`^;
DROP TRIGGER IF EXISTS `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_UPDATE`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`ADD_TO_GATE_HISTORY_AFTER_UPDATE` AFTER UPDATE ON `GATE` FOR EACH ROW
BEGIN
	INSERT INTO `parkinglot`.`GATE_HISTORY` (gateId, status, modifiedBy, modified, modifiedReason) VALUES (NEW.id, NEW.status, NEW.lastModifiedBy, NEW.lastModified, NEW.lastModifiedReason);
END^;


-- -----------------------------------------------------
-- Trigger `parkinglot`.`PARKING_TICKET_AFTER_INSERT`
-- -----------------------------------------------------
USE `parkinglot`^;
DROP TRIGGER IF EXISTS `parkinglot`.`PARKING_TICKET_AFTER_INSERT`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`PARKING_TICKET_AFTER_INSERT` AFTER INSERT ON `PARKING_TICKET` FOR EACH ROW
BEGIN
  UPDATE `parkinglot`.`PARKING_CAPACITY` SET currentCapacity = currentCapacity + 1, lastModified = NEW.lastModified;
  INSERT INTO `parkinglot`.`PARKING_TICKET_HISTORY` (ticketId, status, modifiedBy, modified, modifiedReason) VALUES (NEW.id, NEW.status, NEW.lastModifiedBy, NEW.lastModified, NEW.lastModifiedReason);
END^;


-- -----------------------------------------------------
-- Trigger `parkinglot`.`PARKING_TICKET_AFTER_UPDATE`
-- -----------------------------------------------------
USE `parkinglot`^;
DROP TRIGGER IF EXISTS `parkinglot`.`PARKING_TICKET_AFTER_UPDATE`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`PARKING_TICKET_AFTER_UPDATE` AFTER UPDATE ON `PARKING_TICKET` FOR EACH ROW
BEGIN
  UPDATE `parkinglot`.`PARKING_CAPACITY` SET currentCapacity = currentCapacity - 1, lastModified = NEW.lastModified;
  INSERT INTO `parkinglot`.`PARKING_TICKET_HISTORY` (ticketId, status, modifiedBy, modified, modifiedReason) VALUES (NEW.id, NEW.status, NEW.lastModifiedBy, NEW.lastModified, NEW.lastModifiedReason);
END^;


-- -----------------------------------------------------
-- Trigger `parkinglot`.`PARKING_CAPACITY_BEFORE_UPDATE`
-- -----------------------------------------------------
USE `parkinglot`^;
DROP TRIGGER IF EXISTS `parkinglot`.`PARKING_CAPACITY_BEFORE_UPDATE`^;
CREATE DEFINER = CURRENT_USER TRIGGER `parkinglot`.`PARKING_CAPACITY_BEFORE_UPDATE` BEFORE UPDATE ON `PARKING_CAPACITY` FOR EACH ROW
BEGIN
  IF (NEW.currentCapacity > NEW.maxCapacity) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Max capacity reached';
  END IF;
  IF (NEW.currentCapacity < 0) THEN
    SET NEW.currentCapacity = 0;
  END IF;
END^;


SET SQL_MODE=@OLD_SQL_MODE ^;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS ^;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS ^;