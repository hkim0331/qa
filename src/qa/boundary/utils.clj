(ns qa.boundary.utils
  (:require
   [next.jdbc.result-set :as rs]))

(defn ds [db]
  (-> db :spec :datasource))

(def bf {:builder-fn rs/as-unqualified-lower-maps})
