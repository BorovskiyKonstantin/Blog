use blog;

insert into users (is_moderator, reg_time, name, email, password, code) values
(true, "2020-01-01 00:00:00", "moderator1", "moderator1@blog.com", "$2a$10$FQNW14QdSW4lI1rtw0BnlOylbd.lth8Rf/ucMcfWxBCwvpGZi1Kku", "moderator1code"),
(false, "2020-01-01 10:00:00", "user1", "user1@blog.com", "$2a$10$Te..QX8Us53BOZTfXwU1QOAuEk/4pCGDIl18ounAjXtbJYqvsRGFq", "user1code"),
(false, "2020-01-02 10:00:00", "user2", "user2@blog.com", "$2a$10$2WcYksolmVBUeVnMJk1Gtuud0z3Xf.aytBqc.wuYuqUyVtFTkKfqe", "user2code"),
(false, "2020-01-03 10:00:00", "user3", "user3@blog.com", "$2a$10$fWmLmsjs7cqStbogzgi/0u31POXRsiDru1uPLDjmWDDKJNm3q09xK", "user3code");

insert into posts (is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) values
(true, default, NULL, 2, "2020-02-01 00:09:01", "Post1 By User1", "user1 bla bla bla", 0),
(true, "ACCEPTED", 1, 2, "2020-02-01 00:00:02", "Post2 By User1", "user1 bla bla bla", 0),
(false, "DECLINED", 1, 2, "2020-02-01 00:00:03", "Post3 By User1", "user1 bla bla bla", 0),

(false, default, NULL, 3, "2020-02-01", "Post1 By User2", "user2 bla bla bla", 0),
(true, "ACCEPTED", 1, 3, "2020-02-02 01:00:00", "Post2 By User2", "user2 bla bla bla", 0),
(false, "DECLINED", 1, 3, "2020-02-03", "Post3 By User2", "user2 bla bla bla", 0),

(false, default, NULL, 3, "2020-02-01", "Post1 By User2", "user3 bla bla bla", 0),
(true, "ACCEPTED", 1, 4, "2020-02-02 02:00:00", "Post2 By User2", "user3 bla bla bla", 0),
(false, "DECLINED", 1, 4, "2020-02-03", "Post3 By User2", "user3 bla bla bla", 0);

insert into post_votes (post_id, user_id, time, value) values
(1, 2, "2020-02-01 00:00:01", 1),
(1, 3, "2020-02-01 00:00:01", 0),
(1, 4, "2020-02-01 00:00:01", 1),

(2, 2, "2020-02-01 00:00:01", 1),
(2, 3, "2020-02-01 00:00:02", 1),
(2, 4, "2020-02-01 00:00:03", 1),
(2, 2, "2020-02-01 00:00:01", 0),
(2, 3, "2020-02-01 00:00:02", 0),
(2, 4, "2020-02-01 00:00:03", 0),

(3, 2, "2020-02-01 00:00:01", 1),
(3, 3, "2020-02-01 00:00:01", 0),
(3, 4, "2020-02-01 00:00:01", 1),

(4, 2, "2020-02-01 00:00:01", 1),
(4, 3, "2020-02-01 00:00:01", 0),
(4, 4, "2020-02-01 00:00:01", 1),

(5, 2, "2020-02-01 00:00:01", 0),
(5, 3, "2020-02-01 00:00:01", 0),
(5, 4, "2020-02-01 00:00:01", 0),

(8, 2, "2020-02-01 00:00:01", 0),
(8, 3, "2020-02-01 00:00:01", 1),
(8, 4, "2020-02-01 00:00:01", 1);

insert into post_comments(parent_id, time, user_id, post_id, text) values
(null, "2020-02-01 00:00:01", 2, 5, "blablabla"),
(1, "2020-02-01 00:00:02", 3, 5, "comment text"),
(1, "2020-02-01 00:00:03", 4, 1,"!!!!"),

(null, "2020-02-01 00:00:01", 2, 2, "111"),
(1, "2020-02-01 00:00:02", 3, 2, "222");



insert into tags (name) values 
("tag1"),
("tag2"),
("tag3");

insert into tag2post (post_id, tag_id) VALUES
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(2,3);

insert into captcha_codes (code, secret_code, time) values
("testCode", "testSecretCode", "2020-02-01 00:00:02"),
("test", "test", "2020-02-01 00:00:02")