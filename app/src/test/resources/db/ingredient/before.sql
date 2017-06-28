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
VALUES (3, 2, 4, 'carotte', 'carotte jaune');
INSERT INTO ingredient_translation (`id`, `ingredient_id`, `language_id`, `name`, `name_extra`, `note`)
VALUES (4, 2, 2, 'carrot', 'yellow carrot', 'Yellow carrot is common in Uzbekistan, but hardly can be found in Europe. Although it brings in special notes you may replace it with red carrot.');
