(ns qa.boundary.questions
  (:require
   [duct.database.sql]
   [environ.core :refer [env]]
   [next.jdbc :refer [get-connection]]
   [next.jdbc.result-set :as rs]
   [next.jdbc.sql :as sql]
   [qa.boundary.utils :refer [ds bf]]
   [taoensso.timbre :refer [debug]]))

(defprotocol Questions
  (create [db nick question])
  (fetch [db n])
  (fetch-all [db]))

(extend-protocol Questions
  duct.database.sql.Boundary
  (create [db nick question]
    (debug "nick" nick "question" question)
    (sql/insert! (ds db) :questions {:nick nick :q question}))

  (fetch [db n]
    (sql/get-by-id (ds db) :questions n bf))

  (fetch-all [db]
    (sql/query (ds db) ["select * from questions"] bf)))
