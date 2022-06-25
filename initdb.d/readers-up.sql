create table readers (
  id SERIAL PRIMARY KEY,
  login VARCHAR(20) NOT NULL,
  page  varchar(20) NOT NULL,
  number INT DEFAULT 0,
  read_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
