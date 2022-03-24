(ns qa.boundary.users
 (:require
  [environ.core :refer [env]]
  [next.jdbc :refer [get-connection]]
  [next.jdbc.result-set :as rs]
  [next.jdbc.sql :refer [query]]
  [taoensso.timbre :refer [debug]]))

(def db {:dbtype   "postgresql"
         :host     (env :l22-host)
         :dbname   (env :l22-db)
         :user     (env :l22-user)
         :password (env :l22-password)})

(def ds (get-connection db))

;; typing から持ってきた関数名をそのまま。
(defn find-user-by-nick [nick]
  (let [ret (query
             ds
             ["select * from users where login=?" nick]
             {:builder-fn rs/as-unqualified-lower-maps})]
     (debug "find-user-by-nick" ret)
     (-> ret first)))
