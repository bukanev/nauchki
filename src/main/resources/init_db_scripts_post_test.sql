INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'AUTHOR');
INSERT INTO roles (id, name) VALUES (3, 'USER');

INSERT INTO users (id, username, login, password, email) VALUES (1, 'admin', 'admin', '123', 'admin@test.ru');
INSERT INTO users (id, username, login, password, email) VALUES (2, 'author', 'author', '123', 'author@test.ru');
INSERT INTO users (id, username, login, password, email) VALUES (3, 'user', 'user', '123', 'user@test.ru');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 3);

INSERT INTO post (tag, title, subtitle, text, author_id) VALUES ('post1', 'Post1 title', 'subtitle', 'post1 text', 1);
INSERT INTO post (tag, title, subtitle, text, author_id) VALUES ('post2', 'Post2 title', 'subtitle', 'post2 text', 2);
INSERT INTO post (tag, title, subtitle, text, author_id) VALUES ('post1', 'Post3 title', 'subtitle', 'post3 text', 3);
