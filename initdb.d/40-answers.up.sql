create table answers (
  id serial primary key,
  q_id integer references questions(id),
  nick varchar(8),
  ts timestamp default current_timestamp);