#!/usr/bin/env bb

;; pods 必要なライブラリがあれば読む。
(require '[babashka.pods :as pods])
(pods/load-pod 'org.babashka/postgresql "0.0.4")
(require '[pod.babashka.postgresql :as pg])

;; pods にないライブラリは、自力で読む。
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {environ/environ {:mvn/version "1.2.0"}}})
(require '[environ.core :refer [env]])

(def db {:dbtype   "postgresql"
         :dbname   "qa"
         :user     (env :qa-user)
         :password (env :qa-password)})

(pg/execute! db ["create table questions (
   id serial primary key,
   q text,
   nick varchar(8),
   ts timestamp default current_timestamp)"])

(pg/execute! db ["create table answers (
   id serial primary key,
   q_id integer references questions(id),
   nick varchar(8),
   a text,
   ts timestamp default current_timestamp)"])

(pg/execute! db ["create table goods (
   id serial primary key,
   q_id integer references questions(id) default null,
   a_id integer references answers(id) default null,
   nick varchar(8),
   ts timestamp default current_timestamp)"])

