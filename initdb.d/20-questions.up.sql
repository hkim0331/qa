create table questions (
  id serial primary key,
  q text,
  nick varchar(20),
  ts timestamp default current_timestamp);
