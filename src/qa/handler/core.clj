(ns qa.handler.core
  (:require
    [ataraxy.core :as ataraxy]
    [ataraxy.response :as response]
    [integrant.core :as ig]
    [ring.util.response :refer [redirect]]
    [taoensso.timbre :as timbre :refer [debug]]))

(defmethod ig/init-key :qa.handler.core/question-new [_ _]
 (fn [_]
  [::response/ok "question-new"]))

(defmethod ig/init-key :qa.handler.core/question-create [_ _]
 (fn [{[_ params] :ataraxy/result}]
   (debug "question-create" params)
   [::response/ok "question-create"]))

(defmethod ig/init-key :qa.handler.core/question [_ _]
  (fn [{[_ params] :ataraxy/result}]
   (debug "question" params)
   [::response/ok "question"]))

(defmethod ig/init-key :qa.handler.core/questions [_ _]
  (fn [_]
    (debug "questions")
    [::response/ok "questions"]))

(defmethod ig/init-key :qa.handler.core/answer-new [_ _]
  (fn [_]
     [::response/ok "answer-new"]))

(defmethod ig/init-key :qa.handler.core/answer-create [_ _]
  (fn [{[_ params] :ataraxy/result}]
    (debug "answer-create" params)
    [(::response/ok "answer-create")]))

(defmethod ig/init-key :qa.handler.core/answer [_ _]
  (fn [{[_ params] :ataraxy/result}]
    (debug "answer" params)
    [::response/ok "answer"]))

(defmethod ig/init-key :qa.handler.core/answers [_ _]
  (fn [_]
    (debug "questions")
    [::response/ok "answers"]))