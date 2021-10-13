#!/usr/bin/env bb
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {environ/environ {:mvn/version "1.2.0"}}})
(require '[environ.core :refer [env]])

(require '[babashka.pods :as pods])
(pods/load-pod 'org.babashka/postgresql "0.0.4")
(require '[pod.babashka.postgresql :as pg])

(def db {:dbtype   "postgresql"
         :dbname   "qa"
         :user     (env :qa-user)
         :password (env :qa-password)})

;; foreigh key constraints.
(doseq [tbl ["goods" "answers" "questions"]]
  (pg/execute! db [(str "drop table if exists " tbl)]))
