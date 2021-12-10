(ns qa.boundary.users
 (:require
  [environ.core :refer [env]]
  [next.jdbc :refer [get-connection]]
  [next.jdbc.result-set :as rs]
  [next.jdbc.sql :refer [query]]
  [taoensso.timbre :refer [debug]]))

(def db {:dbtype   "postgresql"
         :host     (env :r99c-host)
         :dbname   (env :r99c-db)
         :port     (env :r99c-port)
         :user     (env :r99c-user)
         :password (env :r99c-password)})

(def ds (get-connection db))

;; typing から持ってきた関数名をそのまま。
(defn find-user-by-nick [nick]
  (let [ret (query
             ds
             ["select * from users where login=?" nick]
             {:builder-fn rs/as-unqualified-lower-maps})]
     (debug "find-user-by-nick" ret)
     (-> ret first)))
