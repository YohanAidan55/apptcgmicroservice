INSERT INTO users (id, first_name, last_name, user_name, email, password, role, enabled, provider)
SELECT 'c406abb7-65ae-4005-a8f6-f8461d76e814', 'Yohan', 'Aidan', 'aidanyo',
       'aidan.yohan@gmail.com', '$2a$10$WdcUVLg3KgUsK9FrnEBhXOQt2Vt3ZXOVLsxZXpbWiFbuYFnSq7922',
       'ROLE_USER', true, 'INTERNAL'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE id = 'c406abb7-65ae-4005-a8f6-f8461d76e814'
);

INSERT INTO users (id, first_name, last_name, user_name, email, password, role, enabled, provider)
SELECT 'b406abb7-65ae-4005-a8f6-f8461d76e815', 'Alexandre', 'Hassan', 'hassanal',
       'alexandre.hassan@gmail.com', '$2a$10$WdcUVLg3KgUsK9FrnEBhXOQt2Vt3ZXOVLsxZXpbWiFbuYFnSq7922',
       'ROLE_USER', true, 'INTERNAL'
    WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE id = 'b406abb7-65ae-4005-a8f6-f8461d76e815'
);
