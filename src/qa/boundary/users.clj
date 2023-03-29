(ns qa.boundary.users
  (:require
   [next.jdbc.sql :refer [query]]
   [qa.boundary.utils :refer [ds-opt]]))

;; typing から持ってきた関数名をそのまま。
(defn find-user-by-login [db login]
  (let [ret (query
             (ds-opt db)
             ["select * from users where login=?" login])]
    (-> ret first)))
