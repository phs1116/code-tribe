CREATE TABLE memberGatherings(
    memberGatheringId INT(11) AUTO_INCREMENT,
    gatheringNo INT(11) COMMENT "모임 인덱스",
    userId VARCHAR(50) COMMENT "모임 참가자",
    scoreYn TINYINT(1) COMMENT "모임 평가 여부",
    score INT(11) COMMENT "모임 평가 점수",
    joinAt DATETIME(6) COMMENT "참가 시간",
    PRIMARY KEY(memberGatheringId)
 )ENGINE = innoDB DEFAULT CHARSET = utf8 COMMENT "모임 참가 테이블";

CREATE TABLE categories(
	categoryId INT(11) unsigned AUTO_INCREMENT COMMENT "카테고리 인덱스",
	categoryName VARCHAR(50) NOT NULL COMMENT "카테고리 이름",
	PRIMARY KEY(categoryId)
)ENGINE = innoDB DEFAULT CHARSET = utf8 COMMENT "회원 테이블";

CREATE TABLE members(
	userId VARCHAR(50) COMMENT "회원 ID",
	email VARCHAR(50) NOT NULL COMMENT "회원 이메일",
	password VARCHAR(64) NOT NULL COMMENT "회원 비밀번호",
	nickname VARCHAR(50) NOT NULL COMMENT "회원 닉네임",
	phoneNumber VARCHAR(20) NOT NULL COMMENT "회원 전화번호",
	createdAt DATETIME(6) NOT NULL COMMENT "가입 일자",
	updatedAt DATETIME(6) NOT NULL COMMENT "수정 일자"	,
	createdBy VARCHAR(50) COMMENT "생성자",
	updatedBy VARCHAR(50) COMMENT "수정자",
	PRIMARY KEY(userId)
)ENGINE = innoDB DEFAULT CHARSET = utf8 COMMENT "회원 테이블";

CREATE TABLE gatherings (
	gatheringNo INT(11) unsigned AUTO_INCREMENT COMMENT "모임 인덱스",
	userId VARCHAR(50) NOT NULL COMMENT "모임 작성자",
	gatheringTitle VARCHAR(128) NOT NULL COMMENT "모임 타이틀",
	content TEXT COMMENT "모임 본문",
	gatheringAt DATETIME(6) COMMENT "모임 시작 시간",
	gatheringPlace VARCHAR(64) COMMENT "모임 위치",
	latitude DOUBLE COMMENT "모임 위치 위도",
	longitude DOUBLE COMMENT "모임 위치 경도",
	exitYn TINYINT(1) DEFAULT 0 NOT NULL COMMENT "모임 종료 여부",
	categoryId INT(11) NOT NULL COMMENT "모임 카테고리",
	createdAt DATETIME(6) COMMENT "모임 게시글 생성 시간",
	updatedAt DATETIME(6) COMMENT "모임 게시글 수정 시간",
	createdBy VARCHAR(50) COMMENT "생성자",
	updatedBy VARCHAR(50) COMMENT "수정자",
	PRIMARY KEY(gatheringNo)
)ENGINE = innoDB DEFAULT CHARSET = utf8 COMMENT "모임 테이블";

