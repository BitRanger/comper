

CREATE TABLE IF NOT EXISTS wk_paper (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  description TEXT,
  score INTEGER,
  name_publisher TEXT,
  time_published TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS wk_chapter (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  description TEXT,
  time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS wk_question_meta 
(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  id_paper INTEGER,
  id_chapter INTEGER,
  type INTEGER,
  difficulty REAL,
  score INTEGER
);



CREATE TABLE IF NOT EXISTS wk_question 
(
  id_meta INTEGER PRIMARY KEY,
  content TEXT,
  answer TEXT,
  comment TEXT
);