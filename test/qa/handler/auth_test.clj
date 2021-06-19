(ns qa.handler.auth-test
  (:require [clojure.test :refer [deftest testing is]]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [qa.handler.auth :as auth]))

(deftest login-test
  (testing "login page exists"
    (let [handler  (ig/init-key :qa.handler.auth/login {})
          response (handler (mock/request :get "/login"))]
      (is (= :ataraxy.response/ok (first response)) "response ok"))))


(deftest auth?-test
 (testing "auth?"
   (is (= true  (auth/auth? "hkimura" "made in hkim")))
   (is (= false (auth/auth? "hkimura" "bad")))
   (is (= false (auth/auth? "" "")))
   (is (= false (auth/auth? nil nil)))))