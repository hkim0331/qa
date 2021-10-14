(ns qa.boundary.questions
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [qa.boundary.utils :refer [ds bf]]
   [taoensso.timbre :refer [debug]]))

(defprotocol Questions
  (create [db nick question])
  (fetch [db n])
  (fetch-all [db])
  (count-my-questions [db nick]))

(extend-protocol Questions
  duct.database.sql.Boundary
  (create [db nick question]
    (debug "nick" nick "question" question)
    (sql/insert! (ds db) :questions {:nick nick :q question}))

  (fetch [db n]
    (let [ret (sql/get-by-id (ds db) :questions n bf)]
     (debug "ret" ret)
     ret))

  (fetch-all [db]
    ;; CHANGED: 
    (sql/query (ds db) ["select * from questions order by id asc"] bf))

  (count-my-questions
    [db nick]
    (let [ret (sql/query
               (ds db)
               ["select count(*) from questions where nick=?" nick]
               bf)]
      (-> ret first :count))))
