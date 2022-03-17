(ns qa.boundary.users
 (:require
  [environ.core :refer [env]]
  [next.jdbc :refer [get-connection]]
  [next.jdbc.result-set :as rs]
  [next.jdbc.sql :refer [query]]
  [taoensso.timbre :refer [debug]]
  [qa.boundary.utils :refer [ds-opt]]))

(defn find-user-by-nick [db nick]
  (let [ret (query
             (ds-opt db)
             ["select * from users where login=?" nick])]
    (-> ret first)))
