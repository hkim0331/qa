(ns qa.handler.auth-test
  (:require [clojure.test :refer [deftest testing is]]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [qa.handler.auth :refer :all]))

(deftest login-test
  (testing "login page exists"
    (let [handler  (ig/init-key :qa.handler.auth/login {})
          response (handler (mock/request :get "/login"))]
      (is (= :ataraxy.response/ok (first response)) "response ok"))))


(deftest auth?-test
 (testing "auth?"
   (is (= true  (auth? "hkimura" "made in hkim")))
   (is (= false (auth? "hkimura" "bad")))
   (is (= false (auth? "" "")))
   (is (= false (auth? nil nil)))))