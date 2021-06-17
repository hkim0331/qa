;;; auth.clj (this file) is an excerpt from
;;; ex-typing/src/ex_typing/boundary/users.clj
(ns qa.boundary.typing-users
 (:require
  [environ.core :refer [env]]
  [next.jdbc :refer [get-connection]]
  [next.jdbc.result-set :as rs]
  [next.jdbc.sql :refer [query]]
  #_[duct.database.sql]
  [taoensso.timbre :refer [debug]]))

(def db-spec
 {:dbtype "postgresql"
  :dbname "typing"
  :user (env :typing-user)
  :password (env :typing-pass)})

(defn find-user-by-nick [nick]
  (let [ret (query
             (get-connection db-spec)
             ["select * from users where nick=?" nick]
             {:builder-fn rs/as-unqualified-lower-maps})]
     (debug "find-user-by-nick" ret)
     (-> ret first)))

;; (defprotocol Users
;;   (find-user-by-nick [nick]))

;; (extend-protocol Users
;;   duct.database.sql.Boundary

;;   (find-user-by-nick [nick]
;;     (let [ret (sql/query
;;                (jdbc/get-datasource db-spec)
;;                ["select * from users where nick=?" nick]
;;                bf)]
;;       (debug "find-user-by-nick" ret)
;;       (-> ret first))))
