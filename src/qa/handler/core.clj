(ns qa.handler.core
  (:require
    [ataraxy.core :as ataraxy]
    [ataraxy.response :as response]
    [integrant.core :as ig]
    [qa.boundary.questions :as questions]
    [qa.view.page :refer [question-new-page]]
    [ring.util.response :refer [redirect]]
    [taoensso.timbre :as timbre :refer [debug]]))

(defn get-nick
  "request ヘッダの id 情報を文字列で返す。
   FIXME: develop ではエラーでも nobody を返したいが。"
  [req]
  (try
    (name (get-in req [:session :identity]))
    (catch Exception e (debug "get-nick" (.getMessage e)))
    (finally "nobody")))

(defmethod ig/init-key :qa.handler.core/question-new [_ _]
 (fn [_]
  (question-new-page)))

(defmethod ig/init-key :qa.handler.core/question-create [_ {:keys [db]}]
 (fn [{[_ params] :ataraxy/result :as req}]
   (let [nick (get-nick req)
         question (get params "question")]
     (debug "question-create" "nick" nick "question" question)
     (questions/create db nick question)
     [::response/ok "question-create"])))

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