SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `wk_comper` ;
CREATE SCHEMA IF NOT EXISTS `wk_comper` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `wk_comper` ;

-- -----------------------------------------------------
-- Table `wk_comper`.`wk_paper`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wk_comper`.`wk_paper` ;

CREATE TABLE IF NOT EXISTS `wk_comper`.`wk_paper` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NULL,
  `description` VARCHAR(2048) NULL,
  `score` INT NULL,
  `name_publisher` VARCHAR(256) NULL,
  `time_published` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wk_comper`.`wk_chapter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wk_comper`.`wk_chapter` ;

CREATE TABLE IF NOT EXISTS `wk_comper`.`wk_chapter` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NULL,
  `description` VARCHAR(2048) NULL,
  `time_created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wk_comper`.`r_paper_chapter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wk_comper`.`r_paper_chapter` ;

CREATE TABLE IF NOT EXISTS `wk_comper`.`r_paper_chapter` (
  `r_id_paper` INT UNSIGNED NOT NULL,
  `r_id_chapter` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`r_id_paper`, `r_id_chapter`),
  INDEX `id_chapter_idx` (`r_id_chapter` ASC),
  CONSTRAINT `r_id_paper`
    FOREIGN KEY (`r_id_paper`)
    REFERENCES `wk_comper`.`wk_paper` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `r_id_chapter`
    FOREIGN KEY (`r_id_chapter`)
    REFERENCES `wk_comper`.`wk_chapter` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wk_comper`.`wk_question_meta`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wk_comper`.`wk_question_meta` ;

CREATE TABLE IF NOT EXISTS `wk_comper`.`wk_question_meta` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id_paper` INT UNSIGNED NOT NULL,
  `id_chapter` INT UNSIGNED NOT NULL,
  `type` INT NOT NULL,
  `difficulty` DOUBLE NOT NULL,
  `score` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_chapter_idx` (`id_chapter` ASC),
  INDEX `id_paper_idx` (`id_paper` ASC),
  CONSTRAINT `id_chapter`
    FOREIGN KEY (`id_chapter`)
    REFERENCES `wk_comper`.`wk_chapter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_paper`
    FOREIGN KEY (`id_paper`)
    REFERENCES `wk_comper`.`wk_paper` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `wk_comper`.`wk_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `wk_comper`.`wk_question` ;

CREATE TABLE IF NOT EXISTS `wk_comper`.`wk_question` (
  `id_meta` INT UNSIGNED NOT NULL,
  `content` VARCHAR(4096) NULL,
  `answer` VARCHAR(4096) NULL,
  `comment` VARCHAR(2048) NULL,
  PRIMARY KEY (`id_meta`),
  CONSTRAINT `id_meta`
    FOREIGN KEY (`id_meta`)
    REFERENCES `wk_comper`.`wk_question_meta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
