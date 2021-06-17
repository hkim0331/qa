;;; auth.clj (this file) is an excerpt from
;;; ex-typing/src/ex_typing/boundary/users.clj
(ns qa.boundary.auth
 (:require
  [environ.core :refer [env]]
  [next.jdbc.sql :as sql]
  [duct.database.sql]
  [qa.boundary.utils :refer [ds bf]]
  [taoensso.timbre :refer [debug]]))

(def db
 {:dbtype "postgresql"
  :dbname "typing"
  :user (env :typing-user)
  :passwo (env :typing-password)})

(defprotocol Users
  (find-user-by-nick [nick]))

(extend-protocol Users
  duct.database.sql.Boundary
  ;; FIXME: exception?
  (find-user-by-nick [nick]
    (let [ret (sql/query)]
         (ds db)
         ["select * from users where nick=?" nick]
         bf
      (debug "find-user-by-nick" ret)
      (-> ret first))))
