(ns qa.boundary.goods
  (:require
   [duct.database.sql]
   [next.jdbc.sql :as sql]
   [next.jdbc.result-set :as rs]
   [qa.boundary.utils :refer [ds-opt]]
   #_[taoensso.timbre :refer [debug]]))

(def ^:private bfn {:builder-fn rs/as-unqualified-lower-maps})

(defprotocol Goods
  (create! [db q-id a-id nick])
  (found? [db a-id nick])
  (find-goods [db a-id])
  (count-sent [db nick])
  (count-received [db nick])
  (recents [db]))

(extend-protocol Goods
  duct.database.sql.Boundary
  (create!
   [db q-id a-id nick]
   (sql/insert! (ds-opt db) :goods {:q_id q-id :a_id a-id :nick nick}))

  (found?
   [db a-id nick]
   (let [ret (sql/find-by-keys
              (ds-opt db)
              :goods
              {:a_id a-id :nick nick})]
     (boolean (seq ret))))

  (find-goods
   [db a-id]
   (let [ret (sql/find-by-keys
              (ds-opt db)
              :goods {:a_id a-id})]
     ret))

  (count-sent
   [db nick]
   (let [ret (sql/query
              (ds-opt db)
              ["select count(*) from goods where nick=?" nick])]
     (:count (first ret))))

  (count-received
   [db nick]
   (let [ret (sql/query
              (ds-opt db)
              ["select count(*) from goods
                inner join answers on answers.id=goods.a_id
                where answers.nick=?" nick])]
     (:count (first ret))))

  (recents
   [db]
   (let [ret (sql/query
              (ds-opt db)
              ["select goods.q_id, questions.q from goods
inner join questions on goods.q_id=questions.id
order by goods.id desc
limit 30"])]
     ret)))
