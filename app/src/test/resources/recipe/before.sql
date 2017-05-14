CREATE TABLE recipe
(
	id int(10) not null auto_increment primary key,
	title varchar(160) not null,
	type varchar(31) null,
	cook_time time null,
	precook_time time null,
	annotation text null,
	howto text null,
	language varchar(31) default 'russian' not null
);

INSERT INTO recipe
	VALUES (1, 'Plov Tashkent style', 'main', NULL, NULL, NULL, NULL, 'russian');
INSERT INTO recipe
	VALUES (2, 'Greek salad', 'salad', NULL, NULL, NULL, NULL, 'english');