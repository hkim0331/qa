create table questions (
  id serial primary key,
  q text,
  nick varchar(8),
  ts timestamp default current_timestamp);
