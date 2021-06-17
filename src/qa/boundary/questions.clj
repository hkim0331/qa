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
   (let [ret (sql/insert! (ds db)
                          :questions
                          {:nick nick :q question})]
     (debug "create" ret)
     ret))

  (fetch [db n])

  (fetch-all [db]))