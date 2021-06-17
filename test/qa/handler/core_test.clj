(ns qa.handler.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [qa.handler.core :as core]))

;; (defn db []
;;   (-> system (ig/find-derived-1 :duct.database/sql) val :spec))

;; (defn q [sql]
;;   (jdbc/query (db) sql))

(deftest smoke-test
  (testing "login page exists"
    (let [handler  (ig/init-key :qa.handler.core/login {})
          response (handler (mock/request :get "/login"))]
      (is (= :ataraxy.response/ok (first response)) "response ok"))))

(deftest smoke-test-2
  (testing "question-new-page exists"
    (let [handler  (ig/init-key :qa.handler.core/question-new {})
          response (handler (mock/request :get "/q"))]
      (is (= :ataraxy.response/ok (first response)) "response ok"))))

;; (deftest db-test
;;   (testing "insert questions"
;;    (let)))