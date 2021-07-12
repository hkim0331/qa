(ns qa.boundary.answers
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [qa.boundary.utils :refer [ds bf]]
   [taoensso.timbre :refer [debug]]))

(defprotocol Answers
  (create [db q-id nick answer])
  (find-one [db n])
  (find-by-keys [db n])
  (update-answer! [db map n])
  (find-recents [db n])
  (count-my-answers [db nick]))

(extend-protocol Answers
  duct.database.sql.Boundary
  (create [db q-id nick answer]
    (debug "q_id" q-id "nick" nick "answer" answer)
    (sql/insert! (ds db) :answers {:q_id q-id :nick nick :a answer}))

  (find-one [db n]
    (sql/get-by-id (ds db) :answers n bf))

  (find-by-keys [db n]
    ;; (sql/find-by-keys
    ;;  (ds db)
    ;;  :answers
    ;;  {:q_id n :order-by [[:id :asc]]}
    ;;  bf))
    (sql/query (ds db)
               ["select * from answers where q_id=? order by id" n]
               bf))

  (update-answer!
    [db map n]
    (sql/update! (ds db) :answers map {:id n}))

  (find-recents [db n]
    (sql/query (ds db)
               ["select * from answers order by id desc limit ?" n]
               bf))
  (count-my-answers
    [db nick]
    (let [ret (sql/query
               (ds db)
               ["select count(*) from answers where nick=?" nick]
               bf)]
      (debug "count-my-answers" ret)
      (-> ret first :count))))