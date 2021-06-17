(ns qa.handler.example
  (:require
   [ataraxy.response :as response]
   [clojure.java.io :as io]
   [integrant.core :as ig]))

(defmethod ig/init-key :qa.handler/example [_ _]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (io/resource "qa/handler/example/example.html")]))
