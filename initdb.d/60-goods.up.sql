create table goods (
  id serial primary key,
  q_id integer references questions(id) default null,
  a_id integer references answers(id) default null,
  nick varchar(8),
  ts timestamp default current_timestamp);