(ns qa.boundary.goods
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [next.jdbc.result-set :as rs]
   [qa.boundary.utils :refer [ds]]
   [taoensso.timbre :refer [debug]]))

(def ^:private bfn {:builder-fn rs/as-unqualified-lower-maps})

(defprotocol Goods
  (create! [db a-id nick])
  (found? [db a-id nick])
  (find-goods [db a-id])
  (count-sent [db nick])
  (count-received [db nick]))

(extend-protocol Goods
  duct.database.sql.Boundary
  (create!
    [db a-id nick]
    (debug "create!" a-id nick)
    (sql/insert! (ds db) :goods {:a_id a-id :nick nick}))

  (found?
    [db a-id nick]
    (let [ret (sql/find-by-keys
               (ds db)
               :goods
               {:a_id a-id :nick nick})]
      (boolean (seq ret))))

  (find-goods
    [db a-id]
    (let [ret (sql/find-by-keys
               (ds db)
               :goods {:a_id a-id}
               bfn)]
      (debug "find-goods" ret)
      ret))

  (count-sent
    [db nick]
    (let [ret (sql/query
               (ds db)
               ["select count(*) from goods where nick=?" nick]
               bfn)]
      (debug "sent" ret)
      (:count (first ret))))

  (count-received
    [db nick]
    (let [ret (sql/query
               (ds db)
               ["select count(*) from goods
                inner join answers on answers.id=goods.a_id
                where answers.nick=?" nick])]
      (debug "reveived" ret)
      (:count (first ret)))))
