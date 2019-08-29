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

create table car(
	id serial primary key,
	name varchar,
	transmission_id integer references transmission(id),
	car_body_id integer references car_body(id),
	engine_id integer references engine(id)
);

insert into transmission (name) values ('transmission 1');
insert into transmission (name) values ('transmission 2');
insert into transmission (name) values ('transmission 3');
insert into transmission (name) values ('transmission 4');
insert into transmission (name) values ('transmission 5');

insert into car_body (name) values ('car_body 1');
insert into car_body (name) values ('car_body 2');
insert into car_body (name) values ('car_body 3');
insert into car_body (name) values ('car_body 4');
insert into car_body (name) values ('car_body 5');

insert into engine (name) values ('engine 1');
insert into engine (name) values ('engine 2');
insert into engine (name) values ('engine 3');
insert into engine (name) values ('engine 4');
insert into engine (name) values ('engine 5');

insert into car (name, transmission_id, car_body_id, engine_id)
values ('first car', 1, 2, 5);
insert into car (name, transmission_id, car_body_id, engine_id)
values ('second car', 2, 4, 3);
insert into car (name, transmission_id, car_body_id, engine_id)
values ('third car', 4, 1, 2);