(ns qa.boundary.questions
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [qa.boundary.utils :refer [ds-opt]]
   [taoensso.timbre :refer [debug]]))

(defprotocol Questions
  (create [db nick question])
  (fetch [db n])
  (fetch-all [db])
  (fetch-after [db date])
  (count-my-questions [db nick]))

(extend-protocol Questions
  duct.database.sql.Boundary
  (create [db nick question]
    (debug "nick" nick "question" question)
    (sql/insert! (ds-opt db) :questions {:nick nick :q question}))

  (fetch [db n]
    (let [ret (sql/get-by-id (ds-opt db) :questions n)]
     ;;(debug "ret" ret)
     ret))

  (fetch-all [db]
    (sql/query
     (ds-opt db)
     ["select * from questions order by id desc"]))

  (fetch-after [db date]
   (sql/query
    (ds-opt db)
    ["select * from questions where ts > ?::DATE order by id desc" date]))

  (count-my-questions
    [db nick]
    (let [ret (sql/query
               (ds-opt db)
               ["select count(*) from questions where nick=?" nick])]
      (-> ret first :count))))
