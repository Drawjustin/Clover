
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema enjoytrip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enjoytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `enjoytrip` ;

-- -----------------------------------------------------
-- Table `enjoytrip`.`sido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`sido` (
  `sido_code` INT NOT NULL,
  `sido_name` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`gugun`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`gugun` (
  `gugun_code` INT NOT NULL,
  `gugun_name` VARCHAR(30) NULL DEFAULT NULL,
  `sido_code` INT NOT NULL,
  PRIMARY KEY (`gugun_code`, `sido_code`),
  INDEX `gugun_to_sido_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  CONSTRAINT `gugun_to_sido_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  INDEX `attraction_to_content_type_id_fk_idx` (`content_type_id` ASC) VISIBLE,
  INDEX `attraction_to_sido_code_fk_idx` (`sido_code` ASC) VISIBLE,
  INDEX `attraction_to_gugun_code_fk_idx` (`gugun_code` ASC) VISIBLE,
  CONSTRAINT `attraction_to_content_type_id_fk`
    FOREIGN KEY (`content_type_id`)
    REFERENCES `enjoytrip`.`content_type` (`content_type_id`),
  CONSTRAINT `attraction_to_gugun_code_fk`
    FOREIGN KEY (`gugun_code`)
    REFERENCES `enjoytrip`.`gugun` (`gugun_code`),
  CONSTRAINT `attraction_to_sido_code_fk`
    FOREIGN KEY (`sido_code`)
    REFERENCES `enjoytrip`.`sido` (`sido_code`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_description`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_attraciton_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `enjoytrip`.`attraction_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`attraction_detail` (
  `content_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `created_time` VARCHAR(14) NULL DEFAULT NULL,
  `modified_time` VARCHAR(14) NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `attraction_detail_to_basic_content_id_fk`
    FOREIGN KEY (`content_id`)
    REFERENCES `enjoytrip`.`attraction_info` (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;

-- -----------------------------------------------------
-- Table `enjoytrip`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`member` (
  `user_id` VARCHAR(30) NOT NULL,
  `user_name` VARCHAR(30) NOT NULL,
  `user_pass` VARCHAR(10) NOT NULL,
  `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_email` VARCHAR(30) NOT NULL UNIQUE,
  `user_question_result` VARCHAR(100) NOT NULL,
  `token` VARCHAR(1000),
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`trip_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`trip_board` (
  `trip_article_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(30) NOT NULL,
  `user_name` VARCHAR(30) NOT NULL,
  `register_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `total_clover` INT NULL DEFAULT '0',
  `subject` VARCHAR(30) NOT NULL,
  `content` VARCHAR(10000) NOT NULL,
  `hit` INT NULL DEFAULT '0',
  `cost` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`trip_article_id`),
  INDEX `user_id` (`user_id` ASC) VISIBLE,
  CONSTRAINT `trip_board_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`member` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`clover`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`clover` (
  `clover_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(30) NOT NULL,
  `trip_article_id` INT NOT NULL,
  `clover_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`clover_id`),
  INDEX `member_to_clover_user_id_fk` (`user_id` ASC) VISIBLE,
  INDEX `trip_board_to_clover_trip_article_id_fk` (`trip_article_id` ASC) VISIBLE,
  CONSTRAINT `member_to_clover_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`member` (`user_id`),
  CONSTRAINT `trip_board_to_clover_trip_article_id_fk`
    FOREIGN KEY (`trip_article_id`)
    REFERENCES `enjoytrip`.`trip_board` (`trip_article_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`qna_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`qna_board` (
  `qna_article_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT '0',
  `user_name` VARCHAR(30) NULL DEFAULT NULL,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`qna_article_id`),
  INDEX `qna_board_to_member_user_id_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `qna_board_to_member_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`member` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`qna_article_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`qna_article_comment` (
  `qna_article_comment_id` INT NOT NULL AUTO_INCREMENT,
  `qna_article_id` INT NULL DEFAULT NULL,
  `qna_article_comment_content` VARCHAR(100) NULL DEFAULT NULL,
  `user_id` VARCHAR(30) NULL DEFAULT NULL,
  `register_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`qna_article_comment_id`),
  INDEX `qna_board_to_qna_article_comment_qna_article_id_fk` (`qna_article_id` ASC) VISIBLE,
  INDEX `member_to_qna_article_comment_user_id_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `member_to_qna_article_comment_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`member` (`user_id`)
    ON DELETE CASCADE,
  CONSTRAINT `qna_board_to_qna_article_comment_qna_article_id_fk`
    FOREIGN KEY (`qna_article_id`)
    REFERENCES `enjoytrip`.`qna_board` (`qna_article_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`tag` (
  `trip_article_id` INT NOT NULL,
  `tag_name` VARCHAR(30) NOT NULL,
  `tag_type` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`trip_article_id`, `tag_name`),
  CONSTRAINT `trip_board_to_tag_trip_article_id_fk`
    FOREIGN KEY (`trip_article_id`)
    REFERENCES `enjoytrip`.`trip_board` (`trip_article_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`trip_board_file`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`trip_board_file` (
  `trip_board_file_id` INT NOT NULL AUTO_INCREMENT,
  `trip_article_id` INT NULL DEFAULT NULL,
  `save_folder` VARCHAR(100) NULL DEFAULT NULL,
  `original_file` VARCHAR(100) NULL DEFAULT NULL,
  `save_file` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`trip_board_file_id`),
  INDEX `trip_board_to_trip_board_file_fk` (`trip_article_id` ASC) VISIBLE,
  CONSTRAINT `trip_board_to_trip_board_file_fk`
    FOREIGN KEY (`trip_article_id`)
    REFERENCES `enjoytrip`.`trip_board` (`trip_article_id`)
	ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `enjoytrip`.`trip_article_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `enjoytrip`.`trip_article_comment` (
  `trip_article_comment_id` INT NOT NULL AUTO_INCREMENT,
  `trip_article_id` INT NOT NULL,
  `trip_article_comment_content` VARCHAR(100) NULL,
  `user_id` VARCHAR(30) NULL,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`trip_article_comment_id`),
  INDEX `trip_article_id_idx` (`trip_article_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `trip_article_id`
    FOREIGN KEY (`trip_article_id`)
    REFERENCES `enjoytrip`.`trip_board` (`trip_article_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `enjoytrip`.`member` (`user_id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


