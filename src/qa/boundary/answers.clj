(ns qa.boundary.answers
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [qa.boundary.utils :refer [ds bf]]
   [taoensso.timbre :refer [debug]]))

(defprotocol Answers
  (create [db nick question])
  (find-one [db n])
  (find-by-keys [db n]))

(extend-protocol Answers
  duct.database.sql.Boundary
  (create [db nick question]
    (debug "nick" nick "question" question)
    (sql/insert! (ds db) :answers {:nick nick :q question}))

  (find-one [db n]
    (sql/get-by-id (ds db) :answers n bf))

  (find-by-keys [db n]
    (sql/find-by-keys (ds db) :answers {:q_id n} bf)))
