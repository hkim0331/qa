(ns qa.boundary.goods
  (:require
   [duct.database.sql]
   #_[environ.core :refer [env]]
   [next.jdbc.sql :as sql]
   [next.jdbc.result-set :as rs]
   [qa.boundary.utils :refer [ds]]
   [taoensso.timbre :refer [debug]]))

(defprotocol Goods
  (create! [db a-id nick])
  (find-goods [db a-id])
  (find-goods-by-nick [db nick]))

(extend-protocol Goods
  duct.database.sql.Boundary
  (create!
    [db a-id nick]
    (debug "create!" a-id nick)
    (sql/insert! (ds db) :goods {:a_id a-id :nick nick}))

  (find-goods
    [db a-id]
    (let [ret (sql/find-by-keys
               (ds db)
               :goods {:a_id a-id}
               {:builder-fn rs/as-unqualified-lower-maps})]
      (debug "find-goods" ret)
      ret))
  
  (find-goods-by-nick
   [db nick]
   (let [sent (sql/find-)])))
