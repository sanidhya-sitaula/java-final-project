CREATE TABLE USERS 
    (ID bigint auto_increment, 
    NAME varchar(255),
    USERNAME varchar(255),
    PASSWORD varchar(255)
    ); 

CREATE TABLE POSTS
    (
        ID bigint auto_increment,
        USERNAME varchar(255),
        DATE_POSTED varchar(255),
        NUM_LIKES int,
        PHOTO BLOB(100K)
    );

CREATE TABLE POSTS_LIKES 
    (
        POSTID int, 
        LIKEDBYUSERID int
    );