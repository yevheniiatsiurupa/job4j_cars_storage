create table transmission(
	id serial primary key,
	name varchar
);

create table car_body(
	id serial primary key,
	name varchar
);

create table engine(
	id serial primary key,
	name varchar
);

create table car_make(
	id serial primary key,
	name varchar
);

create table car_model(
	id serial primary key,
	name varchar,
	car_make_id integer references car_make(id)
);

create table car(
	id serial primary key,
	car_make_id integer references car_make(id),
	car_model_id integer references car_model(id),
	transmission_id integer references transmission(id),
	car_body_id integer references car_body(id),
	engine_id integer references engine(id),
	year integer,
	price integer
);

create table account(
	id serial primary key,
	name varchar,
	login varchar unique,
	password varchar,
	email varchar unique,
	role varchar
);

create table application(
	id serial primary key,
	account_id integer references account(id),
	car_id integer references car(id),
	description varchar,
	photo varchar,
	sold boolean,
	created date
);



insert into transmission (name) values ('механика');
insert into transmission (name) values ('автомат');
insert into transmission (name) values ('робот');
insert into transmission (name) values ('вариатор');

insert into car_body (name) values ('седан');
insert into car_body (name) values ('хетчбэк');
insert into car_body (name) values ('универсал');
insert into car_body (name) values ('внедорожник');
insert into car_body (name) values ('кабриолет');
insert into car_body (name) values ('купе');
insert into car_body (name) values ('пикап');
insert into car_body (name) values ('минивэн');
insert into car_body (name) values ('фургон');
insert into car_body (name) values ('микроавтобус');

insert into engine (name) values ('бензин');
insert into engine (name) values ('гибрид');
insert into engine (name) values ('газ');
insert into engine (name) values ('дизель');
insert into engine (name) values ('электро');

insert into car_make (name) values ('Audi');
insert into car_make (name) values ('BMW');
insert into car_make (name) values ('Chevrolet');
insert into car_make (name) values ('Ford');
insert into car_make (name) values ('Honda');
insert into car_make (name) values ('Jeep');
insert into car_make (name) values ('Nissan');
insert into car_make (name) values ('Opel');

insert into car_model(name, car_make_id) values ('Model 1', 1);
insert into car_model(name, car_make_id) values ('Model 2', 2);
insert into car_model(name, car_make_id) values ('Model 3', 3);
insert into car_model(name, car_make_id) values ('Model 4', 4);
insert into car_model(name, car_make_id) values ('Model 5', 5);
insert into car_model(name, car_make_id) values ('Model 6', 6);
insert into car_model(name, car_make_id) values ('Model 7', 7);
insert into car_model(name, car_make_id) values ('Model 8', 8);


insert into car (car_make_id, car_model_id, transmission_id, car_body_id, engine_id, year, price)
values (1, 1, 1, 2, 1, 2018, 100000);
insert into car (car_make_id, car_model_id, transmission_id, car_body_id, engine_id, year, price)
values (2, 2, 2, 2, 1, 2017, 200000);


insert into account(name, login, password, email, role)
values ('admin', 'admin', 'adminpass', 'admin em1', 'admin');
insert into account(name, login, password, email, role)
values ('user', 'user', 'userpass', 'user em1', 'user');

insert into application(account_id, car_id, description, sold, created)
values (2, 1, 'Текст объявления 1', false, now());

insert into application(account_id, car_id, description, sold, created)
values (2, 2, 'Текст объявления 2', false, now());