INSERT INTO collection (id, name, user_id, total_products, favorite)
SELECT 'e1111111-1111-1111-1111-111111111111', 'Yohan Collection', 'c406abb7-65ae-4005-a8f6-f8461d76e814', 0, true
WHERE NOT EXISTS (SELECT 1 FROM collection WHERE id = 'e1111111-1111-1111-1111-111111111111');

INSERT INTO collection (id, name, user_id, total_products, favorite)
SELECT 'e2222222-2222-2222-2222-222222222222', 'Alex Collection', 'b406abb7-65ae-4005-a8f6-f8461d76e815', 0, true
WHERE NOT EXISTS (SELECT 1 FROM collection WHERE id = 'e2222222-2222-2222-2222-222222222222');

