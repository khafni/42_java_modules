CREATE TABLE "User" (
    id SERIAL PRIMARY KEY,
    login varchar(50) not null,
    password varchar(100) not null
);

CREATE TABLE Chatroom (
   id  SERIAL PRIMARY KEY,
   name varchar(50) not null,
   owner SERIAL REFERENCES "User"(id)
);

CREATE TABLE UserChatRoom (
    id SERIAL PRIMARY KEY,
    UserId SERIAL REFERENCES "User"(id),
    ChatRoom SERIAL REFERENCES Chatroom(id)
);

CREATE TABLE Message (
  id SERIAL PRIMARY KEY,
  author SERIAL REFERENCES "User"(id),
  room SERIAL REFERENCES Chatroom(id),
  text TEXT not null,
  date_time timestamp not null
);

