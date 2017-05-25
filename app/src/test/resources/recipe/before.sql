INSERT INTO recipe (`id`, `name`, `cook_time`, `precook_time`)
	VALUES (1, 'Plov Tashkent style', NULL, NULL);
INSERT INTO recipe (`id`, `name`, `cook_time`, `precook_time`)
	VALUES (2, 'Greek salad', NULL, NULL);

INSERT INTO ingredient (`id`, `name`)
	VALUES (1, 'onion');
INSERT INTO ingredient (`id`, `name`)
	VALUES (2, 'carrot');
INSERT INTO ingredient (`id`, `name`)
	VALUES (3, 'lamb');

INSERT INTO recipe_ingredient (`recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`)
	VALUES (1, 1, 250, true, 'gram');
INSERT INTO recipe_ingredient (`recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`)
	VALUES (1, 2, 500, true, 'gram');
INSERT INTO recipe_ingredient (`recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`)
	VALUES (1, 3, 500, false, 'gram');
INSERT INTO recipe_ingredient (`recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`)
	VALUES (2, 1, 1, false, 'unit');
