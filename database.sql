CREATE DATABASE magicbook;

CREATE TABLE User (
	UserId CHAR(36) PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    dob DATE NOT NULL,
    sex VARCHAR(15) NOT NULL,
    country VARCHAR(512) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(128) NOT NULL,
    avatar VARCHAR(512) DEFAULT 'DefaultImg\\avatar-icon.png'
);

CREATE TABLE Friendship (
	FriendshipId CHAR(36) PRIMARY KEY,
    status VARCHAR(32),
    receiverId CHAR(36),
    UserId CHAR(36),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE Gr (
	GroupId CHAR(36) PRIMARY KEY,
    name VARCHAR(512) NOT NULL,
    avatar VARCHAR(512) DEFAULT 'DefaultImg\\group-avatar-icon.png',
    UserId CHAR(36),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE GroupAccess(
	AccessId CHAR(36) PRIMARY KEY,
    isAdmin INT NOT NULL DEFAULT 0,
    UserId CHAR(36),
    GroupId CHAR(36),
    FOREIGN KEY (UserId) REFERENCES User(UserId),
    FOREIGN KEY (GroupId) REFERENCES Gr(GroupId)
); 

CREATE TABLE Post (
	PostId CHAR(36) PRIMARY KEY, 
    content VARCHAR(2048) NOT NULL,
    visibility VARCHAR(128) NOT NULL,
    postDate DATE NOT NULL,
    postTime TIME NOT NULL,
    file VARCHAR(512),
    UserId CHAR(36),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE Comment (
	CommentId CHAR (36) PRIMARY KEY,
    content VARCHAR(2048) NOT NULL,
    commentDATE DATE NOT NULL,
    commentTIME TIME NOT NULL,
    PostId CHAR(36),
    UserId CHAR(36),
    FOREIGN KEY (PostId) REFERENCES Post(PostId),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE LikePost (
	LikeId CHAR (36) PRIMARY KEY,
    likeDATE DATE NOT NULL,
    likeTIME TIME NOT NULL,
    PostId CHAR (36),
    UserId CHAR (36),
	FOREIGN KEY (PostId) REFERENCES Post(PostId),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE message (
	MessageId CHAR(36) PRIMARY KEY,
    content VARCHAR(2048) NOT NULL,
    sentDate DATE NOT NULL, 
    sentTime TIME NOT NULL, 
    receiverId CHAR(36),
    UserId CHAR(36),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);

CREATE TABLE Notification (
	NotificationId CHAR(36) PRIMARY KEY, 
    isRead BOOL NOT NULL DEFAULT 0,
    createdTime DATETIME,
    sentId CHAR(36),
    UserId CHAR(36),
    FOREIGN KEY (UserId) REFERENCES User(UserId)
);


