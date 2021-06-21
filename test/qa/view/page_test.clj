(ns qa.view.page-test
  (:require [clojure.test :refer [deftest testing is]]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [qa.view.page :refer :all]))

(deftest ss-test
  (testing "shorten string test"
    (let [s "0123456789"]
      (is (= s (ss 20 s)))
      (is (= "0123..." (ss 4 s)))
      (is (= "" (ss 10 ""))))))

(deftest escape-html-test
 (testing "escape html string"
  (let [s1 "<p>abc</p>"]
    (is (= "&lt;p>abc&lt;/p>" (escape-html s1)))
    (is (= "hello" (escape-html "hello"))))))
