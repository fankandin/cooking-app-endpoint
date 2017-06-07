-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: db-java
-- Generation Time: Jun 07, 2017 at 10:16 PM
-- Server version: 5.7.17
-- PHP Version: 7.0.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `palinfo_cooking`
--

-- --------------------------------------------------------

--
-- Table structure for table `coursetype`
--

CREATE TABLE `coursetype` (
  `id` smallint(6) NOT NULL,
  `name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `coursetype`
--

INSERT INTO `coursetype` (`id`, `name`) VALUES
(1, 'main'),
(2, 'side'),
(3, 'salad'),
(4, 'soup'),
(5, 'appetiser'),
(6, 'sauce'),
(7, 'dessert'),
(8, 'beverage'),
(9, 'bakery'),
(10, 'conservation');

-- --------------------------------------------------------

--
-- Table structure for table `cousine`
--

CREATE TABLE `cousine` (
  `id` int(10) UNSIGNED NOT NULL,
  `name_ru` varchar(40) NOT NULL,
  `name_en` varchar(40) DEFAULT NULL,
  `name_de` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cousine`
--

INSERT INTO `cousine` (`id`, `name_ru`, `name_en`, `name_de`) VALUES
(1, 'Русская', 'Russian', 'Russische'),
(2, 'Итальянская', 'Italian', 'Italienische'),
(3, 'Узбекская', 'Uzbek', 'Usbekisch');

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT 'Use it with a domain-default language to mark images when needed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `image_info`
--

CREATE TABLE `image_info` (
  `image_id` bigint(20) NOT NULL,
  `language_id` smallint(6) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `id` int(11) NOT NULL,
  `name` varchar(80) NOT NULL COMMENT 'Name to be used as an identifier/note dealing with i18n. Use it e.g. with a domain-default language.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`id`, `name`) VALUES
(11, 'Parsley'),
(5, 'Баранина'),
(9, 'Говядина'),
(4, 'Зира'),
(2, 'Лук репчатый'),
(13, 'Мед'),
(3, 'Морковь'),
(1, 'Рис узбекский'),
(10, 'Рыбный соус'),
(6, 'Сливки 10%'),
(7, 'Сливки 20%'),
(8, 'Сливки 30%'),
(12, 'Чеснок');

-- --------------------------------------------------------

--
-- Table structure for table `ingredienttype`
--

CREATE TABLE `ingredienttype` (
  `id` smallint(6) NOT NULL,
  `name` varchar(40) NOT NULL COMMENT 'Domain-default language should be used. It supposedely suffices for the scope, so caching can be dona on the application level'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ingredienttype`
--

INSERT INTO `ingredienttype` (`id`, `name`) VALUES
(6, 'cereal'),
(10, 'cheese'),
(9, 'dairy'),
(3, 'fish'),
(11, 'flavour'),
(5, 'fruit'),
(8, 'green'),
(14, 'grocery'),
(1, 'meat'),
(12, 'oil'),
(2, 'poultry'),
(13, 'premade'),
(7, 'spice'),
(4, 'vegetable');

-- --------------------------------------------------------

--
-- Table structure for table `ingredient_info`
--

CREATE TABLE `ingredient_info` (
  `id` int(11) NOT NULL,
  `ingredient_id` int(11) NOT NULL,
  `language_id` smallint(6) NOT NULL,
  `name` varchar(80) NOT NULL,
  `name_extra` varchar(80) DEFAULT NULL COMMENT 'The name can be ambiguous despite it is commonly used. This can be used as an extra',
  `note` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ingredient_info`
--

INSERT INTO `ingredient_info` (`id`, `ingredient_id`, `language_id`, `name`, `name_extra`, `note`) VALUES
(1, 1, 1, 'Рис узбекский', 'для узбекского плова', 'Узбекские сорта: Лазурный.\r\nТурецкий аналог: Pilavlik Pirinc.'),
(2, 2, 1, 'Лук репчатый', NULL, NULL),
(3, 3, 1, 'Морковь', NULL, NULL),
(4, 4, 1, 'Зира', 'немолотая', NULL),
(5, 5, 1, 'Баранина', 'окорок', NULL),
(6, 5, 2, 'lamb', 'leg', NULL),
(7, 5, 3, 'Lamm', 'Keule', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `ingredient_ingredienttype`
--

CREATE TABLE `ingredient_ingredienttype` (
  `id` bigint(20) NOT NULL,
  `ingredient_id` int(11) NOT NULL,
  `type_id` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ingredient_ingredienttype`
--

INSERT INTO `ingredient_ingredienttype` (`id`, `ingredient_id`, `type_id`) VALUES
(1, 1, 6),
(2, 2, 4),
(3, 3, 4),
(4, 4, 7),
(5, 5, 1),
(6, 6, 9),
(7, 7, 9),
(8, 8, 9);

-- --------------------------------------------------------

--
-- Table structure for table `language`
--

CREATE TABLE `language` (
  `id` smallint(6) NOT NULL,
  `code` varchar(8) NOT NULL COMMENT 'Language code (e.g. IETF language tag)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `language`
--

INSERT INTO `language` (`id`, `code`) VALUES
(3, 'de'),
(2, 'en'),
(5, 'es'),
(4, 'fr'),
(6, 'it'),
(1, 'ru');

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE `recipe` (
  `id` bigint(20) NOT NULL,
  `name` varchar(160) NOT NULL COMMENT 'Name to be used as an identifier/note dealing with i18n. Use it e.g. with a domain-default language.',
  `cook_time` time DEFAULT NULL,
  `precook_time` time DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`id`, `name`, `cook_time`, `precook_time`) VALUES
(1, 'Плов ташкентский \"Байрам Ош\"', '02:05:00', '01:00:00'),
(2, 'Pepper sauce for steak', '00:30:00', '00:10:00'),
(3, 'Test recipe', NULL, NULL),
(4, 'Test recipe 2', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `recipe_cousine`
--

CREATE TABLE `recipe_cousine` (
  `id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `cousine_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `recipe_image`
--

CREATE TABLE `recipe_image` (
  `id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `image_id` bigint(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `recipe_info`
--

CREATE TABLE `recipe_info` (
  `id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `language_id` smallint(6) NOT NULL,
  `title` varchar(160) NOT NULL,
  `annotation` text,
  `method` text COMMENT 'How to cook the recipe'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Language dependent information for the recipes';

--
-- Dumping data for table `recipe_info`
--

INSERT INTO `recipe_info` (`id`, `recipe_id`, `language_id`, `title`, `annotation`, `method`) VALUES
(1, 1, 1, 'Плов ташкентский \"Байром Ош\"', 'Настоящий узбекский плов с форума эмигрантов Узбекистана', 'Перед началом можно чуток выпить за результат. Только не увлекайтесь , ибо процесс, конечно, вещь захватывающая, но кушать-то что-то надо будет, а с возрастанием количества выпитого, как известно, женщины хорошеют, а закуски вкуснеют.\r\n\r\nРис промыть 5-6 раз, пока не исчезнет светлая муть в воде и замочить в тёплой воде. Лучше за несколько часов до начала готовки. Мясо нарезать кусками с кулак. Окорочные кости нарубить с кусками мяса на 2-3 части.\r\n\r\n\r\nМорковь используют жёлтую-она не такая сладкая как оранжевая. Но к сожалению, за пределами Средней Азии, ни разу не видел жёлтой. Поэтому берём оранжевую и нарезаем соломкой (не кубики и не кружочки) примерно 3х3 мм. Не вздумайте нарезать на мелкой тёрке, а то морковь превратится во время готовки в пюре.\r\nДля наглядности показываю как нарезать соломку. (зажигалка для представления размеров)\r\n\r\n\r\n\r\nЕсли морковь очень длинная, то режьте пластинки диагональю\r\n\r\nЛук шинкуют. Полукольцами илу кольцами.\r\n\r\nЕсли вы захотите выпить за хороший результат и при этом закусить традиционно по узбекски, то порежьте дополнительно Грамм 50-100 курдючного сала кубиками.\r\n\r\nНагреваем казан до максимума. Налить около 250 мл. Разогреть масло без всяких там добавок. В Узбекистане на празники иногда использут Кунжутное. Не советую оливковое. У него температура раскаливания маленькая. При разогреве начинает быстро дыметь. Поберегите его для салатов лучше. Используйте подсолнечное или, если есть в продаже, хлопковое. Огонь сделайте сильным. То что масло прокалилось и дошло до готовности можно определить по появлению беловатого дымка. Если вы используете хлопковое масло, то нужно поджарить одну луковицу до черна, чтобы она впитала в себя все дифилианты и прочие вредные вещестава. Будьте при этом осторожны, масло может стрельнуть, получите серьезный ожог, а то и вообще без глаза останетесь. После обжаривание луковицу выбросить. \r\n\r\nОбжариваем сначала кусок курдюка и кубики курдючные если вы решили ими закусить.\r\n\r\nКак только кубики покроются румянцем и превратятся в шкварки, вытащить и отложить в тарелочку с нашинкованным луком. Посолив их, можете выпить холодной водочки, закусывая завернутыми в кусок лепёшки шкварками курдюка с луком. После того как большой кусок курдюка покроется румянцем, откладываем его в сторонку. Мы делаем это, чтобы он в процессе готовки не растопился и положим обратно в зирвак только перед закладкой риса.\r\nТеперь ждём заново появления белого дымка, после чего обжариваем мясо с костями, обжарить на сильном огне до румяной корочки. Вытаскиваем и откадываем в тарелку и опять даём разогретъся маслу, после чего добавить остальное мясо в раскалённое масло и жарить всё до румяного цвета. Очень важно, чтобы мясо моментально покрывалось корочкой, которая предохраняет кусок мяса от потери влаги и сока. В этом и заключается особенное отличие приготовления ташкентского плова от остальных. Мясо после жарки будеть тушиться и сохранит весь свой аромат и сок. \r\nКак только мясо подрумянится,\r\n\r\nвыложить обратно мясо с костями, слегка поджарить и добавить лук. Жарить вместе с мясом, до тех пор пока лук не обрумянится слегка. Не пережаривайте лук до тёмнго цвета. Ташкентский плов должен быть светлого цвета.\r\n\r\nЛук один из основных продуктов!!! Тех кто не любит его, могу успокоить- он к концу готовки полностью растворится. Высыпать морковь и дать пару минут, чтобы она впитала пары и размягчилась. Потом перемешиваем всё. Жарится всё должно вместе, в собственном соку.\r\n\r\n[attachment=136936:attachment]\r\nПри жарке никуда не уходить, а переодически помешивать. Готовность моркови определяется отсутсвием влаги на дне казана, которая вышла из лука, моркови и мяса. В данном виде плова морковь сильно не прожаривается, а иногда восвсе раскладывается поверх мяса и сразу же заливается водой. После готовности моркови нужно постараться отделить её от мяса, а именно- мясо на дно, а морковь сверху. \r\n\r\nЕсли вы используете сорт Лазурный, налейте в казан 1,25 литра кипячёной горячей воды. Если у вас другие сорта риса, то нужно тоже 1,25, но с расчётом, что может придётся добавить воды. \r\n[attachment=136936:attachment]\r\n\r\nТеперь добавить чайную ложку сдавленной в ладони зиры, зарчивы(куркумы) пол-чайной ложки, барбариса чайную ложку, пол чайной ложки сладкого молотого перца. В Узбекистане на праздники добавляют перец-горошек. В домашних условиях же- острый красный или зелёный стручковый перец. Можно и того и другого вместе. В общем, на ваше усмотрение. В данном варианте я высыпал чайную ложку перца-горошка в чайное закрвающееся ситечко и опустил его в варящийся зирвак. Таким образом вкус горошка распостранитсяа в плове, а горошки можно будет не подавать или подать в отдельном блюде для тех, кто нормально переносит такой перец при надкусвании. Не надо использовать молотый перец, потому-что аромат будет отличаться. Добавляем пару стручков перца и не чищенные головки чеснока.\r\n\r\n\r\nВарить 30-40 минут на слабоми огне, можно с наполовину закрытой крышкой. Минут через 20-25 после начала варки посолить, да так, чтобы после нормализации вкуса соли, добавить ещё чуток, с учётом того, что его впитает рис. То есть вкус должен слегка пересоленным. Если вы решили делать плов с горохом, то высыпать на поверхность замоченный горох, который уже стал достаточно мягким. Дать прокипеть ещё минут 10, выкладываем в тарелку отваренные головки чеснока и перчинки, не повредив их, иначе зирвак будет островатым. Включить огонь на полную мощность, засунуть поглубже в зирвак уже остывший на тарелке кусок жаренного курдюка. Теперь аккуратно высыпаем небольшими порциями рис и разравниваем поверхность, старайсь не перемешивать со слоем моркови. \r\nЗасыпка, расчёт пропорции воды и весь последующий процесс считаются самыми сложными. Ибо от этого зависит весь результат. Всё должно быть расчитано так, чтобы рисинки не лопнуи и в тоже время не были сухими. И мясо чтобы не подгорело. Нет конкретной теории расчёта. Это уже зависит от мастерства повара. Тот рис, который я советую (Лазурный), поглощает воду в пропорции 1 кг к 1.25 литрам воды, с учётом того, что дополнительно выйдет влага из мяса и моркови. В других видах плова добавляют другое количество воды, в зависимости от количества жидкости и сока в продуктах . Рис высыпать нужно очень осторожно и не в коем случае не смешивать с морковью.В плове - каждому продукту свой слой. Многие советуют, чтобы вода покрывала рис на толщину одного или двух пальцев. Но это не совсем верно, ибо эти советы тех, кто привык к своему казану и рису. Лучше при засыпке риса сделайте так, чтобы вода слегка покрывала рис. \r\n\r\n\r\nЕсли у вас изначально много воды, то отчерпайте ложкой в чашку и оставьте на стороне. Лучше меньше воды и потом нехватку можно будет отрегулировать дополнительной водой. А вот если воды будет изначально больше нормы и она впитается в рис, то уже будет поздно исправлять. Если у вас газовая конфорка и огонь охватывает равномерно дно и слегка края, то и рис равномерно впитывает воду. Но вот на электрической плите сложнее- бока казана не совсем хорошо прогреваются и рис там плохо проваривается, в то время как в самом центре, наоборот- сильно вываривается. Вот тут нужно сделать небольшое исключение из правил- пару раз аккуратно перемешать рис. Чтобы не говорили люди, но я вас уверяю- ничего страшного не будет. Делайте только аккуратно, чтобы не задеть нижний слой моркови. И не делайте это как при варке каши. Шумовкой сверху, вдоль стенок казана засовываете до уровня моркови, потом поддеваете, чтобы от стенок до центра казана отчерпнулось и переворачивате наоборот- то что было в центре, теперь у стенок. И так по кругу. Одной, максимум двух таких процедур во время варки риса достаточно. После каждого раза аккуратно разравнивайте поверхность риса. Для того, чтобы рис сохранял свою форму и был рассыпчатым, я использую одну фишку: после того, как вода более менее впитается, я закрываю крышку на пару минут, потом открываю и очень аккуратно разрыхливаю верхние слои. Пробую одну рисинку и если она при надкусе издаёт хруст, то вдеваю шумовку вдоль стенок, отодвигаю рис слегка и если воды совсем нет, то доливаю пару ложек воды поверху через шумовку или отложенного в сторону бульона. Закрываю крышку и через пару минут повторяю то же самое. Когда рис больше не издаёт хруста, мягок, на вид не лопнул, слегка «суховат» и вся вода впиталась- это идеальная консистенция. \r\n\r\n\r\nИ всегда запоминайте сколько всего воды вы потратили, т.е. отмеряйте. Таким образом приучайте себя к каждому сорту риса. «Присев» на один сорт риса, вы уже будете знать сколько воды использовать и потом уже доведёте себя до автоматизма. Пробуйте, а если результат не совсем хороший, то вы будете знать, где вы допускали ошибку. \r\n\r\nКак только вода впитается, аккуратно просовывая шумовку между стенками казана и пловом собрать содержимое в горку. Так чтобы с виду напоминало пол-мяча. Не нарушайте слоёв. Выкладываем поверх риса в середине ранне замоченый изюм(без жидкости), отложенный чеснок и перчинки. Проделать палочкой или ручкой ложки 4-5 отверстий в горке риса до самого дна. \r\n\r\n\r\nЕсли рис, на ваше усмотрение, ещё чуть суховат, то долить воды в отверстия, но очень мало, можно сказать, покапать. \r\n\r\nПереключить на самый медленный огонь, посыпать сверху ещё раз зирой и закрыть плотно крышкой. И в течении 30 минут НЕ ОТКРЫВАТЬ!!!\r\n\r\n\r\nПо истечении 30-ти минут откладываем в тарелку перчинки, чеснок и постарайтесь не рассыпав рис, вытащить со дна мясо и курдюк. Нарежьте нужное для одного лягана(большой тарелки) мясо и курдюк пластинками. \r\n\r\n\r\nЕсли вы решили добавить чёрный перец, то выложите его до перемешивания в казан с пловом. А всё, что осталось в казане аккуратно размешать. Мешать не как кашу, а черпнув вдоль стенок шумовкой до самого дна поднять порции и слегка тряся рукой дать рассыпаться содержимому обратно в казан. Таким образом мы не нарушим структуру рисинок, которые при обычном мешании могут повредиться.\r\n\r\nВыложить в ляган, посыпать мясом и курдюком и в центр поставит чеснок с перчинкой. Ну и всё!\r\n\r\nЧеснок нужно кушать без кожуры, надавливая на дольку. Подать к плову салат «Ачичук» из мелко нарезанных помидоров, репчатого лука и огурцов. \r\nМожно посыпать сверху мелко нарезанным укропом с зелёным луком. \r\nНу вот и всё господа!\r\nПрикрепленный файл  n18.jpg ( 152.6 килобайт ) Кол-во скачиваний: 410\r\n\r\nДа, не забудьте после плова хорошего зелёного или черного чая и попивая говорить “Маззззааа” (кайф).');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_ingredient`
--

CREATE TABLE `recipe_ingredient` (
  `id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `ingredient_id` int(11) NOT NULL,
  `amount` decimal(6,2) NOT NULL,
  `amount_netto` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Is the amount measured e.g. on peeled or deboned product',
  `measurement` enum('unit','gram','ml','tsp','tbsp','handful','pinch','lug') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe_ingredient`
--

INSERT INTO `recipe_ingredient` (`id`, `recipe_id`, `ingredient_id`, `amount`, `amount_netto`, `measurement`) VALUES
(1, 1, 1, 500.00, 0, 'gram'),
(2, 1, 2, 250.00, 1, 'gram'),
(3, 1, 3, 500.00, 1, 'gram'),
(6, 2, 8, 400.00, 0, 'ml'),
(10, 1, 5, 500.00, 1, 'gram'),
(11, 4, 1, 123.00, 0, 'ml'),
(13, 1, 8, 123.00, 0, 'ml'),
(14, 4, 2, 3.00, 0, 'tbsp'),
(15, 4, 6, 40.00, 0, 'gram'),
(16, 4, 4, 2.00, 0, 'tsp'),
(19, 4, 9, 5656.00, 0, 'tbsp'),
(20, 1, 6, 456.00, 0, 'tbsp'),
(21, 1, 4, 2.00, 0, 'tbsp'),
(24, 1, 13, 1.00, 0, 'tbsp'),
(25, 2, 9, 2.00, 0, 'tsp');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_ingredient_info`
--

CREATE TABLE `recipe_ingredient_info` (
  `id` bigint(20) NOT NULL,
  `recipe_ingredient_id` bigint(20) NOT NULL,
  `language_id` smallint(6) NOT NULL,
  `preparation` varchar(255) DEFAULT NULL COMMENT 'How to prepare the ingredient. Examples: "finely sliced", "chopped", "soaked for at least 24 hours". This is needed more for better formatting of a recipe, than a normalization.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe_ingredient_info`
--

INSERT INTO `recipe_ingredient_info` (`id`, `recipe_ingredient_id`, `language_id`, `preparation`) VALUES
(1, 2, 1, 'нарезанный полукольцами'),
(2, 3, 1, 'соломкой 3х3 мм (жульен)'),
(3, 10, 1, 'нарезать кусками с женский кулак; мозговые кости разрубить пополам');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_reference`
--

CREATE TABLE `recipe_reference` (
  `id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `reference_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe_reference`
--

INSERT INTO `recipe_reference` (`id`, `recipe_id`, `reference_id`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `recipe_tag`
--

CREATE TABLE `recipe_tag` (
  `id` bigint(20) NOT NULL,
  `recipe_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe_tag`
--

INSERT INTO `recipe_tag` (`id`, `recipe_id`, `tag_id`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `reference`
--

CREATE TABLE `reference` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(80) NOT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Recipe sources';

--
-- Dumping data for table `reference`
--

INSERT INTO `reference` (`id`, `title`, `url`) VALUES
(1, 'Плов - Форум Эмигрантов Узбекистана', 'http://www.fromuz.com/forum/index.php?showtopic=11478');

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL,
  `title` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`id`, `title`) VALUES
(1, 'плов');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `coursetype`
--
ALTER TABLE `coursetype`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cousine`
--
ALTER TABLE `cousine`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name_ru`),
  ADD KEY `name_en` (`name_en`,`name_de`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `image_info`
--
ALTER TABLE `image_info`
  ADD PRIMARY KEY (`image_id`,`language_id`),
  ADD KEY `language_id` (`language_id`);

--
-- Indexes for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `ingredienttype`
--
ALTER TABLE `ingredienttype`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `ingredient_info`
--
ALTER TABLE `ingredient_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ingredient_id` (`ingredient_id`,`language_id`) USING BTREE,
  ADD KEY `language_id` (`language_id`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `ingredient_ingredienttype`
--
ALTER TABLE `ingredient_ingredienttype`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ingredient_id` (`ingredient_id`,`type_id`) USING BTREE,
  ADD KEY `type_id` (`type_id`);

--
-- Indexes for table `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `title` (`name`);

--
-- Indexes for table `recipe_cousine`
--
ALTER TABLE `recipe_cousine`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cousine_id` (`cousine_id`),
  ADD KEY `recipe_id` (`recipe_id`,`cousine_id`) USING BTREE;

--
-- Indexes for table `recipe_image`
--
ALTER TABLE `recipe_image`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `recipe_id` (`recipe_id`,`image_id`) USING BTREE,
  ADD KEY `image_id` (`image_id`);

--
-- Indexes for table `recipe_info`
--
ALTER TABLE `recipe_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `recipe_id` (`recipe_id`,`language_id`) USING BTREE,
  ADD KEY `language_id` (`language_id`),
  ADD KEY `title` (`title`);

--
-- Indexes for table `recipe_ingredient`
--
ALTER TABLE `recipe_ingredient`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `recipe_id` (`recipe_id`,`ingredient_id`) USING BTREE,
  ADD KEY `ingredient_id` (`ingredient_id`);

--
-- Indexes for table `recipe_ingredient_info`
--
ALTER TABLE `recipe_ingredient_info`
  ADD PRIMARY KEY (`id`),
  ADD KEY `language_id` (`language_id`),
  ADD KEY `recipe_ingredient_id` (`recipe_ingredient_id`,`language_id`) USING BTREE;

--
-- Indexes for table `recipe_reference`
--
ALTER TABLE `recipe_reference`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `recipe_id` (`recipe_id`,`reference_id`) USING BTREE,
  ADD KEY `reference_id` (`reference_id`);

--
-- Indexes for table `recipe_tag`
--
ALTER TABLE `recipe_tag`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `recipe_id` (`recipe_id`,`tag_id`) USING BTREE,
  ADD KEY `tag_id` (`tag_id`);

--
-- Indexes for table `reference`
--
ALTER TABLE `reference`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `title` (`title`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `title` (`title`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cousine`
--
ALTER TABLE `cousine`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ingredient`
--
ALTER TABLE `ingredient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `ingredient_info`
--
ALTER TABLE `ingredient_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `ingredient_ingredienttype`
--
ALTER TABLE `ingredient_ingredienttype`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `recipe`
--
ALTER TABLE `recipe`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `recipe_cousine`
--
ALTER TABLE `recipe_cousine`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `recipe_image`
--
ALTER TABLE `recipe_image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `recipe_info`
--
ALTER TABLE `recipe_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `recipe_ingredient`
--
ALTER TABLE `recipe_ingredient`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT for table `recipe_ingredient_info`
--
ALTER TABLE `recipe_ingredient_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `recipe_reference`
--
ALTER TABLE `recipe_reference`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `recipe_tag`
--
ALTER TABLE `recipe_tag`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `reference`
--
ALTER TABLE `reference`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tag`
--
ALTER TABLE `tag`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `image_info`
--
ALTER TABLE `image_info`
  ADD CONSTRAINT `image_info_ibfk_1` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `image_info_ibfk_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ingredient_ingredienttype`
--
ALTER TABLE `ingredient_ingredienttype`
  ADD CONSTRAINT `ingredient_ingredienttype_ibfk_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ingredient_ingredienttype_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `ingredienttype` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `recipe_cousine`
--
ALTER TABLE `recipe_cousine`
  ADD CONSTRAINT `recipe_cousine_ibfk_2` FOREIGN KEY (`cousine_id`) REFERENCES `cousine` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recipe_cousine_ibfk_3` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recipe_image`
--
ALTER TABLE `recipe_image`
  ADD CONSTRAINT `recipe_image_ibfk_3` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recipe_image_ibfk_4` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recipe_info`
--
ALTER TABLE `recipe_info`
  ADD CONSTRAINT `recipe_info_ibfk_1` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recipe_info_ibfk_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recipe_ingredient`
--
ALTER TABLE `recipe_ingredient`
  ADD CONSTRAINT `recipe_ingredient_ibfk_3` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recipe_ingredient_info`
--
ALTER TABLE `recipe_ingredient_info`
  ADD CONSTRAINT `recipe_ingredient_info_ibfk_1` FOREIGN KEY (`recipe_ingredient_id`) REFERENCES `recipe_ingredient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recipe_ingredient_info_ibfk_2` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`);

--
-- Constraints for table `recipe_reference`
--
ALTER TABLE `recipe_reference`
  ADD CONSTRAINT `recipe_reference_ibfk_2` FOREIGN KEY (`reference_id`) REFERENCES `reference` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recipe_reference_ibfk_3` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recipe_tag`
--
ALTER TABLE `recipe_tag`
  ADD CONSTRAINT `recipe_tag_ibfk_3` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recipe_tag_ibfk_4` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
