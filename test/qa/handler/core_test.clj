(ns qa.handler.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [qa.handler.core :refer :all]))

;; DB が関係して来た時、duct の枠組みはテストしにくくないか？
;; (deftest questions-test
;;   (testing "/qs returns page"
;;     (let [handler (ig/init-key :qa.handler.core/questions {db})])))

(deftest question-test
  (testing "question-new-page exists"
    (let [handler  (ig/init-key :qa.handler.core/question-new {})
          response (handler (mock/request :get "/q"))]
      (is (= :ataraxy.response/ok (first response)) "response ok"))))

(deftest get-login-test
  (testing "get-login returns login?"
    (let [req (mock/request :get "/q")
          req2 (assoc-in req [:session :identity] (keyword "val"))]
      (is (= nil (get-login req)))
      (is (= "val" (get-login req2))))))

;; DB が絡むテストはどう書く？
;; (deftest questios-test
;;  (testing "questions returns page"
;;    (let [handler (ig/init-key :qa.handler.core/questions {db})])))