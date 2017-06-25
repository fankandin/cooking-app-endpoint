INSERT INTO language (`id`, `code`)
VALUES (1, 'ru');
INSERT INTO language (`id`, `code`)
VALUES (2, 'en');

INSERT INTO recipe (`id`, `title`, `cook_time`, `precook_time`, `annotation`, `method`, `language_id`)
	VALUES (1, 'Plov Tashkent style', '02:00:00', '01:30:00', 'Delicious traditional Uzbek course', 'Cut the carrot into 3x3 mm sticks (like duble-size julienne) and slice the onion into half-rings...', 2);
INSERT INTO recipe (`id`, `title`, `cook_time`, `precook_time`, `annotation`, `method`, `language_id`)
	VALUES (2, 'Greek salad', '00:30:00', NULL, 'Tasty refreshing famous salad', 'Add finely graded Parmigiano cheese to the sourcream, finely grade the garlic...', 2);

INSERT INTO ingredient (`id`, `name`)
	VALUES (1, 'onion');
INSERT INTO ingredient (`id`, `name`)
	VALUES (2, 'carrot');
INSERT INTO ingredient (`id`, `name`)
	VALUES (3, 'lamb');
INSERT INTO ingredient (`id`, `name`)
	VALUES (4, 'sour cream');
INSERT INTO ingredient (`id`, `name`)
	VALUES (5, 'jeera');
INSERT INTO ingredient (`id`, `name`)
	VALUES (6, 'yellow carrot');

INSERT INTO recipe_ingredient (`id`, `recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`, `preparation`)
	VALUES (1, 1, 1, 250, true, 'gram', 'halved rings');
INSERT INTO recipe_ingredient (`id`, `recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`, `preparation`)
	VALUES (2, 1, 2, 500, true, 'gram', 'cut into 3x3 mm sticks');
INSERT INTO recipe_ingredient (`id`, `recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`)
	VALUES (3, 1, 3, 500, false, 'gram');
INSERT INTO recipe_ingredient (`id`, `recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`)
	VALUES (4, 2, 1, 1, false, 'unit');
