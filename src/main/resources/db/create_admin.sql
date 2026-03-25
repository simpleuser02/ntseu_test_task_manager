INSERT INTO users (id, username, email, password, role)
VALUES (
           RANDOM_UUID(),
           'admin',
           'admin@example.com',
           '$2a$12$Jov7wG2gL7clSuvXlTWUkuDqA8wmxp2us7MoB8kAGHMfH1crZoMC2',
           'ADMIN'
       );