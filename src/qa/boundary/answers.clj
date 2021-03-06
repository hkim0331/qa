(ns qa.boundary.answers
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [qa.boundary.utils :refer [ds-opt]]))

(defprotocol Answers
  (create [db q-id nick answer])
  (find-one [db n])
  (find-by-keys [db n])
  (update-answer! [db map n])
  (find-recents [db n])
  (count-answers [db])
  (count-my-answers [db nick]))

(extend-protocol Answers
  duct.database.sql.Boundary
  (create [db q-id nick answer]
    ;;(debug "q_id:" q-id "nick:" nick "answer:" answer)
    (sql/insert! (ds-opt db) :answers {:q_id q-id :nick nick :a answer}))

  (find-one [db n]
    (sql/get-by-id (ds-opt db) :answers n))

  (find-by-keys [db n]
    (let [ret (sql/query (ds-opt db)
                         ["select * from answers where q_id=? order by id" n])]
      ;;(debug "find-by-keys:" ret)
      ret))

  (update-answer!
    [db map n]
    (let [ret (sql/update! (ds-opt db) :answers map {:id n})]
      ;;(debug "update-answer!" ret)
      ret))

  (find-recents [db n]
    (sql/query (ds-opt db)
               ["select * from answers order by id desc limit ?" n]))

  (count-my-answers
    [db nick]
    (let [ret (sql/query
               (ds-opt db)
               ["select count(*) from answers where nick=?" nick])]
      ;;(debug "count-my-answers" ret)
      (-> ret first :count)))

  (count-answers
    [db]
    (let [ret (sql/query
               (ds-opt db)
               ["select q_id,count(*) from answers group by q_id"])]
      ;;(debug "count-answers" ret)
      ret)))