(ns qa.boundary.goods
 (:require
  [duct.database.sql]
  #_[environ.core :refer [env]]
  [next.jdbc.sql :as sql]
  [qa.boundary.utils :refer [ds]]
  [taoensso.timbre :refer [debug]]))

(defprotocol Goods
  (create! [db a-id nick]))

(extend-protocol Goods
 duct.database.sql.Boundary
 (create! [db a-id nick]
   (debug "create!" a-id nick)
   (sql/insert! (ds db) :goods {:a_id a-id :nick nick})))


