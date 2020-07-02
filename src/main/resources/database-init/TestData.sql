use blog;

insert into users (is_moderator, reg_time, name, email, password) values
(true, "2020-01-01 00:00:00", "moderator", "moderator@example.ru", "$2y$10$NFGSDg6L7MKD9rpCO3EP3eldCQJcBqu5G9nK/lE.d6wYCGzoqTyDS"),
(true, "2020-01-01 00:00:00", "moderator", "admin@example.ru", "$2y$10$hGifr7r4B2ewlBS22HJES.1DEEVeLc97tSBbLmsCALRmRGVCLJU1u"),
(false, "2020-01-01 10:00:00", "user", "user@example.ru", "$2y$10$VsDP6gSp4Gx.H7CQt9Sm5Oo2uRSGBfCDlISGICd0w0A8LbVZ0YzHK"),
(false, "2020-01-01 10:00:00", "user1", "user1@example.ru", "$2y$10$ORyttkIKKTx42qgIiCujGeGCHa0uDm.uI7s/d0O56DvLZ7S8/QOuS"),
(false, "2020-01-02 10:00:00", "user2", "user2@example.ru", "$2y$10$a2yUJQnCBZMs9j2HkZKwTOrUqN0z6WsbmpWGeiYDyk8feQ8sbYdTq"),
(false, "2020-01-03 10:00:00", "user3", "user3@example.ru", "$2y$10$1Uo2IXWo1ZZ.3sVaKRBPk.Q3sMPhGJBq4komDjmLfAEdYWhDfyihy");

insert into posts (is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) values
--Новые посты
(true, default, NULL, 2, "2020-02-01 00:09:01", "NEW post By User1", "user1 bla bla bla", 10),
(true, default, NULL, 3, "2020-02-01", "NEW post By User2", "user2 bla bla bla", 150),
(false, default, NULL, 4, "2020-02-01", "NEW post By User3", "user3 bla bla bla", 0),
--Подтвержденные модератором посты
(true, "ACCEPTED", 1, 2, "2020-02-01 00:00:00", "ACCEPTED post By User1", "user1 bla bla bla", 30),
(true, "ACCEPTED", 1, 3, "2020-02-02 01:00:00", "ACCEPTED post By User2", "user2 bla bla bla", 40),
(false, "ACCEPTED", 1, 4, "2020-02-02 02:00:00", "ACCEPTED post By User3", "user3 bla bla bla", 0),
--Отклоненные модератором посты
(true, "DECLINED", 1, 2, "2020-02-01 00:00:03", "DECLINED post By User1", "user1 bla bla bla", 0),
(true, "DECLINED", 1, 3, "2020-02-03", "DECLINED post By User2", "user2 bla bla bla", 12),
(false, "DECLINED", 1, 4, "2020-02-03", "DECLINED post By User3", "user3 bla bla bla", 0);

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
(5,2),
(5,3);