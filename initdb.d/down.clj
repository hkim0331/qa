#!/usr/bin/env bb
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {environ/environ {:mvn/version "1.2.0"}}})
(require '[environ.core :refer [env]])

(require '[babashka.pods :as pods])
(pods/load-pod 'org.babashka/postgresql "0.0.8")
(require '[pod.babashka.postgresql :as pg])

(def db {:dbtype   "postgresql"
         :host     (env :qa-host)
         :dbname   (env :qa-db)
         :port     (env :qa-port)
         :user     (env :qa-user)
         :password (env :qa-password)})

;; foreigh key constraints prevend me to drop tables.
(doseq [tbl ["goods" "answers" "questions"]]
  (println "drop" tbl)
  (pg/execute! db [(str "drop table if exists " tbl)]))
