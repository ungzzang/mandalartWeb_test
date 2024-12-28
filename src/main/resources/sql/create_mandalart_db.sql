-- --------------------------------------------------------
-- 호스트:                          localhost
-- 서버 버전:                        11.4.3-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- mandalart 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `mandalart` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `mandalart`;

-- 테이블 mandalart.email_verification 구조 내보내기
CREATE TABLE IF NOT EXISTS `email_verification` (
                                                    `user_id` varchar(100) NOT NULL COMMENT '이메일이 사용자의 아이디',
    `token` varchar(64) NOT NULL COMMENT '인증에 사용할 랜덤 토큰',
    `expired_date` datetime NOT NULL COMMENT '인증만료시간',
    `created_at` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일',
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_user_TO_email_verification_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.find_password 구조 내보내기
CREATE TABLE IF NOT EXISTS `find_password` (
                                               `user_id` varchar(100) NOT NULL COMMENT '이메일이 사용자의 아이디',
    `tmp_password` varchar(100) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `expires_at` datetime DEFAULT NULL COMMENT '2차에 사용할 컬럼',
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_user_TO_find_password_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.mandalart 구조 내보내기
CREATE TABLE IF NOT EXISTS `mandalart` (
                                           `mandalart_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `project_id` bigint(20) NOT NULL COMMENT 'AUTO_INCREMENT로 처리할 예정',
    `parent_id`	bigint	NULL,
    `title` varchar(50) NOT NULL,
    `contents` text DEFAULT NULL,
    `completed_fg` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0: 미완료 1: 완료',
    `depth` tinyint(4) NOT NULL COMMENT '0:최상위 1:1단계 , 2 : 최하위',
    `start_date` date DEFAULT NULL COMMENT '만다라트 계획 시작일',
    `finish_date` date DEFAULT NULL,
    `order_id` tinyint(4) NOT NULL COMMENT '각 단계별 0~7 칸 , 선택 칸에 데이터 입력',
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL,
    PRIMARY KEY (`mandalart_id`),
    KEY `FK_project_TO_mandalart_1` (`project_id`),
    CONSTRAINT `FK_project_TO_mandalart_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.project 구조 내보내기
CREATE TABLE IF NOT EXISTS `project` (
                                         `project_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'AUTO_INCREMENT로 처리할 예정',
    `user_id` varchar(100) NOT NULL,
    `title` varchar(50) NOT NULL,
    `content` varchar(500) NOT NULL COMMENT '상세 내용없을때는 빈문자열('')이라도 보내게 처리할 예정',
    `pic` varchar(50) DEFAULT NULL COMMENT '랜덤으로 생성된 사진 명을 저장',
    `created_at` datetime DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL COMMENT '수정할때 넣어지도록 쿼리에서 처리할 예정',
    PRIMARY KEY (`project_id`),
    KEY `FK_user_TO_project_1` (`user_id`),
    CONSTRAINT `FK_user_TO_project_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.shared_project 구조 내보내기
CREATE TABLE IF NOT EXISTS `shared_project` (
                                                `project_id` bigint(20) NOT NULL,
    `title` varchar(50) NOT NULL,
    `content` text DEFAULT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL,
    PRIMARY KEY (`project_id`),
    CONSTRAINT `FK_project_TO_shared_project_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.shared_project_comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `shared_project_comment` (
                                                        `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '댓글 여러 개 작성될 수 있어서 pk 설정',
    `project_id` bigint(20) NOT NULL,
    `user_id` varchar(100) NOT NULL,
    `content` varchar(500) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_at` datetime DEFAULT NULL,
    PRIMARY KEY (`comment_id`),
    KEY `FK_shared_project_TO_shared_project_comment_1` (`project_id`),
    KEY `FK_user_TO_shared_project_comment_1` (`user_id`),
    CONSTRAINT `FK_shared_project_TO_shared_project_comment_1` FOREIGN KEY (`project_id`) REFERENCES `shared_project` (`project_id`),
    CONSTRAINT `FK_user_TO_shared_project_comment_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.shared_project_like 구조 내보내기
CREATE TABLE IF NOT EXISTS `shared_project_like` (
                                                     `user_id` varchar(100) NOT NULL,
    `project_id` bigint(20) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`user_id`,`project_id`),
    KEY `FK_shared_project_TO_shared_project_like_1` (`project_id`),
    CONSTRAINT `FK_shared_project_TO_shared_project_like_1` FOREIGN KEY (`project_id`) REFERENCES `shared_project` (`project_id`),
    CONSTRAINT `FK_user_TO_shared_project_like_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 mandalart.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
                                      `user_id` varchar(100) NOT NULL COMMENT '이메일이 사용자의 아이디',
    `upw` varchar(100) NOT NULL COMMENT '임시비밀번호가 발급되면 임시비밀번호로 업데이트 할 예정',
    `nick_name` varchar(100) NOT NULL,
    `pic` varchar(50) DEFAULT NULL,
    `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
    `updated_at` timestamp NULL DEFAULT NULL COMMENT 'ON UPDATE로 자동으로 처리할 예정',
    `tmp_pw_fg` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0: 임시비밀번호 신청 안함, 1: 신청',
    PRIMARY KEY (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


ALTER TABLE mandalart
    ADD CONSTRAINT chk_completed_fg CHECK (completed_fg IN (0, 1));
-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
