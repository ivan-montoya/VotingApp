DROP TABLE IF EXISTS Candidate;
DROP TABLE IF EXISTS Thread;
DROP TABLE IF EXISTS RegisteredUser;

CREATE TABLE RegisteredUser (
	name        VARCHAR(25),
	username    VARCHAR(30),
	password    VARCHAR(30),
	email       VARCHAR(30),
	birthday    CHAR(10),
	user_since  TIMESTAMP,
	numThread   INTEGER,

	PRIMARY KEY (username)
);

CREATE TABLE Thread (
	title           VARCHAR(80),
	description     VARCHAR(300),
	numCandidates   INTEGER,
	thread_ID       INTEGER,
	date_created    TIMESTAMP,
	username        VARCHAR(30),
	private_status  VARCHAR(5),
	open_status     VARCHAR(5),

	PRIMARY KEY (username,thread_ID),
	FOREIGN KEY (username) REFERENCES RegisteredUser(username)
);

CREATE TABLE Candidate (
	description  VARCHAR(50),
	thread_ID    INTEGER,
	username     VARCHAR(30),
	votes        INTEGER,

	PRIMARY KEY (thread_ID,username, description)
);


CREATE TABLE Voted (
	voter      VARCHAR(30),
	creator    VARCHAR(30),
	thread_ID  INTEGER,

	PRIMARY KEY (voter, creator, thread_ID)
);

CREATE TABLE PrivateGroup (
	group_name      VARCHAR(20),
	creator         VARCHAR(30),

	PRIMARY KEY (group_name, creator)
);

CREATE TABLE GroupMember (
	group_name     VARCHAR(20),
	group_creator  VARCHAR(30),
	username       VARCHAR(30),
	
	PRIMARY KEY (group_name, group_creator, username),
	FOREIGN KEY (username) REFERENCES RegisteredUser(username),
	FOREIGN KEY (group_name,group_creator) REFERENCES PrivateGroup(group_name, creator)
);

CREATE TABLE GroupThread (
	group_name      VARCHAR(20),
	group_creator   VARCHAR(30),
	thread_ID       INTEGER,
	thread_creator  VARCHAR(30),

	PRIMARY KEY (group_name, group_creator, thread_ID, thread_creator),
	FOREIGN KEY (group_name,group_creator) REFERENCES PrivateGroup(group_name, creator),
	FOREIGN KEY (thread_creator) REFERENCES RegisteredUser(username)
);