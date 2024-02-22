INSERT INTO "User" (login, password)
VALUES
('jake', 'jako1234');

INSERT INTO "User" (login, password)
VALUES
('bigMike', 'usafreedom0123');

INSERT INTO "User" (login, password)
VALUES
('pizzaboi', 'FREEJAKO');

INSERT INTO "User" (login, password)
VALUES
('skyboi', 'menace154');

INSERT INTO "User" (login, password)
VALUES
('freshboi', 'nicecool');

INSERT INTO Chatroom (name, owner)
VALUES
('Oranges', 1),
('Chocolate', 2),
('potato chips', 3),
('UK DRILL', 4),
('TEA PALACE', 5);

INSERT INTO Message (author, room, text, date_time)
VALUES
(1, 1, 'Hello from jake in The Oranges room', CURRENT_TIMESTAMP),
(2, 2, 'Hello from BigMike in the Chocolate room', CURRENT_TIMESTAMP),
(3, 3, 'Greetings from pizzaboi in potato chips', CURRENT_TIMESTAMP),
(4, 4, 'Salutations from skyboi in UK DRILL', CURRENT_TIMESTAMP),
(5, 5, 'Welcome from freshboi in TEA PALACE', CURRENT_TIMESTAMP);

INSERT INTO UserChatRoom (UserId, ChatRoom)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO UserChatRoom (UserId, ChatRoom)
VALUES
    (1, 4),
    (2, 5),
    (3, 1),
    (4, 3),
    (5, 2);