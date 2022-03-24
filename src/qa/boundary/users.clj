(ns qa.boundary.users
 (:require
  [environ.core :refer [env]]
  [next.jdbc :refer [get-connection]]
  [next.jdbc.result-set :as rs]
  [next.jdbc.sql :refer [query]]
  [taoensso.timbre :refer [debug]]
  [qa.boundary.utils :refer [ds-opt]]))

<<<<<<< HEAD
(def db {:dbtype   "postgresql"
         :host     (env :l22-host)
         :dbname   (env :l22-db)
         :user     (env :l22-user)
         :password (env :l22-password)})

(def ds (get-connection db))

;; typing から持ってきた関数名をそのまま。
(defn find-user-by-nick [nick]
=======
(defn find-user-by-nick [db nick]
>>>>>>> hotfix/1.1.1
  (let [ret (query
             (ds-opt db)
             ["select * from users where login=?" nick])]
    (-> ret first)))
