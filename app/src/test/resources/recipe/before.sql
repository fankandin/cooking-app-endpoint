CREATE TABLE recipe
(
	id int(10) not null primary key,
	title varchar(160) not null,
	type varchar(31) null,
	cook_time time null,
	precook_time time null,
	annotation text null,
	howto text null,
	language varchar(31) default 'russian' not null
);

INSERT INTO recipe
	VALUES (1, 'Плов ташкентский', 'main', NULL, NULL, NULL, NULL, NULL);
INSERT INTO recipe
	VALUES (2, 'Салат греческий', 'salad', NULL, NULL, NULL, NULL, NULL);