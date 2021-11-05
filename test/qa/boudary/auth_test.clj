(ns qa.boudary.auth-test)
  ;; (:require [clojure.test :refer [deftest testing is]]
  ;;           [integrant.core :as ig]
  ;;           [ring.mock.request :as mock]
  ;;           [qa.handler.core :as core]))

;; (deftest smoke-test
;;   (testing "login page exists"
;;     (let [handler  (ig/init-key :qa.handler.core/login {})
;;           response (handler (mock/request :get "/login"))]
;;       (is (= :ataraxy.response/ok (first response)) "response ok"))))