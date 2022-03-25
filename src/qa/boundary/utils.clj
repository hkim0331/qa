(ns qa.boundary.utils
  (:require
   [next.jdbc :as jdbc]
   [next.jdbc.result-set :as rs]))

(defn ds [db]
  (-> db :spec :datasource))

(defn ds-opt [db]
  (jdbc/with-options (ds db)
    {:builder-fn rs/as-unqualified-lower-maps}))

;; 消せない。参照している関数がある。
(def bf {:builder-fn rs/as-unqualified-lower-maps})
