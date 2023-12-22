CREATE TABLE `user`
(
    `id`        INT PRIMARY KEY NOT NULL,
    `login`     VARCHAR(255) NOT NULL,
    `email`     VARCHAR(255) NOT NULL,
    `password`  VARCHAR(255) NOT NULL,
    `salt`      VARCHAR(255) NOT NULL,
    `createdAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lastLogin` DATETIME DEFAULT NULL
);