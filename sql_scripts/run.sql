CREATE TABLE Users (
    userID SERIAL PRIMARY KEY,
    fio VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    login VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE TABLE Requests (
     requestID SERIAL PRIMARY KEY,
     startDate DATE NOT NULL,
     orgTechType VARCHAR(100) NOT NULL,
     orgTechModel VARCHAR(100) NOT NULL,
     problemDescryption TEXT NOT NULL,
     requestStatus VARCHAR(50) NOT NULL,
     completionDate DATE,
     repairParts TEXT,
     masterID INTEGER REFERENCES Users(userID),
     clientID INTEGER REFERENCES Users(userID)
);

CREATE TABLE Comments (
     commentID SERIAL PRIMARY KEY,
     message TEXT NOT NULL,
     masterID INTEGER REFERENCES Users(userID),
     requestID INTEGER REFERENCES Requests(requestID)
);


-- Команды импорта в нужно вводить в коносль psql
\COPY Users(userID, fio, phone, login, password, type) FROM '/home/goga_rid/ГИА/2024gia/B2_import_Полюс/Пользователи/inputDataUsers.csv' DELIMITER ';' CSV HEADER;

\COPY Requests(requestID, startDate, orgTechType, orgTechModel, problemDescryption, requestStatus, completionDate, repairParts, masterID, clientID) FROM '/home/goga_rid/ГИА/2024gia/B2_import_Полюс/Заявки/inputDataRequests.csv' DELIMITER ';' CSV HEADER NULL AS 'null';

\COPY Comments(commentID, message, masterID, requestID) FROM '/home/goga_rid/ГИА/2024gia/B2_import_Полюс/Комментарии/inputDataComments.csv' DELIMITER ';' CSV HEADER;