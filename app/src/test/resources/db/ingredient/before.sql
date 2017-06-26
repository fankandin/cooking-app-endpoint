INSERT INTO ingredient (`id`, `name`)
VALUES (1, 'onion');
INSERT INTO ingredient (`id`, `name`)
VALUES (2, 'carrot');
INSERT INTO ingredient (`id`, `name`)
VALUES (3, 'rosemary');

INSERT INTO ingredient_translation (`id`, `ingredient_id`, `language_id`, `name`, `name_extra`)
VALUES (1, 1, 2, 'onion', 'organic range');
INSERT INTO ingredient_translation (`id`, `ingredient_id`, `language_id`, `name`, `name_extra`)
VALUES (2, 1, 3, 'die Zwiebel', 'bio');

INSERT INTO ingredient_translation (`id`, `ingredient_id`, `language_id`, `name`, `name_extra`)
VALUES (3, 2, 1, 'морковь', '');
INSERT INTO ingredient_translation (`id`, `ingredient_id`, `language_id`, `name`, `name_extra`)
VALUES (4, 2, 2, 'carrot', '');
