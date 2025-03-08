Код создает базу данных My_cats.Db, в которую добавляется таблица types. 
Таблица types содержит два столбца: id и type. 
Параметры столбцов следующие:
id- тип данных: INTEGER,первичный ключ:да,уникальность:да;
type-тип данных и ограничение: VARCHAR (100),не null:да.
Добавлена таблица Cats cо столбцами: 
id- тип данных: INTEGER,первичный ключ:да,уникальность:да,не null:да;
name-тип данных: VARCHAR,не null:да;
type_id: INTEGER, FOREIGH KEY, не null:да;
age: INTEGER, не null:да;
weight: DOUBLE, не null:да.
