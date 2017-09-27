

CREATE TABLE IF NOT EXISTS bw_paper (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  description TEXT,
  score INTEGER,
  name_publisher TEXT,
  time_published TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS bw_chapter (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  description TEXT,
  time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS bw_chapter_depend (
  id_chapter INTEGER NOT NULL REFERENCES bw_chapter(id),
  id_depend INTEGER NOT NULL REFERENCES bw_chapter(id),
  PRIMARY KEY (id_chapter, id_depend)
);


CREATE TABLE IF NOT EXISTS bw_question_meta
(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  id_paper INTEGER,
  id_chapter INTEGER,
  type INTEGER,
  difficulty REAL,
  score INTEGER
);

CREATE TABLE IF NOT EXISTS bw_tag (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS bw_tag_question(
  id_tag INTEGER NOT NULL ,
  id_q INTEGER NOT NULL ,
  PRIMARY KEY (id_q, id_tag)
);


CREATE TABLE IF NOT EXISTS bw_question
(
  id_meta INTEGER PRIMARY KEY,
  content TEXT,
  answer TEXT,
  comment TEXT
);