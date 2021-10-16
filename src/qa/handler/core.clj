(ns qa.handler.core
  (:require
   #_[ataraxy.core :as ataraxy]
   [ataraxy.response :as response]
   [integrant.core :as ig]
   [qa.boundary.answers :as answers]
   [qa.boundary.goods :as goods]
   [qa.boundary.questions :as questions]
   [qa.view.page :refer [question-new-page question-edit-page
                         questions-page answers-page answer-page
                         index-page admin-page goods-page
                         recents-page]]
   #_[ring.util.response :refer [redirect]]
   [taoensso.timbre :as timbre :refer [debug]]))

(timbre/set-level! :debug)

(defn get-nick
  "request ヘッダの id 情報を文字列で返す。
   FIXME: develop ではエラーでも nobody を返したいが。"
  [req]
  (try
    (name (get-in req [:session :identity]))
    (catch Exception e (debug "get-nick" (.getMessage e)))
    (finally "nobody")))

(defmethod ig/init-key :qa.handler.core/index [_ _]
  (fn [_]
    (index-page)))

(defmethod ig/init-key :qa.handler.core/question-new [_ _]
  (fn [_]
    (question-new-page)))

(defmethod ig/init-key :qa.handler.core/question-create [_ {:keys [db]}]
  (fn [{[_ params] :ataraxy/result :as req}]
    (let [nick (get-nick req)
          question (get params "question")]
      (debug "question-create" "nick" nick "question" question)
      (questions/create db nick question)
      [::response/found "/qs"])))

(defmethod ig/init-key :qa.handler.core/question [_ {:keys [db]}]
  (fn [{[_ n] :ataraxy/result}]
    (debug ":qa.handler.core/question" n)
    (let [ret (questions/fetch db n)]
      (debug "ret" ret)
      (question-edit-page ret))))

(defmethod ig/init-key :qa.handler.core/questions [_ {:keys [db]}]
  (fn [_]
    (debug "questions")
    (let [ret (questions/fetch-all db)]
      (questions-page ret))))

;;;
;;; answer/answers
;;;

(defmethod ig/init-key :qa.handler.core/answer-new [_ _]
  (fn [_]
    [::response/ok "answer-new"]))

(defmethod ig/init-key :qa.handler.core/answer-create [_ {:keys [db]}]
  (fn [{[_ {:strs [q_id answer]}] :ataraxy/result :as req}]
    (let [nick (get-nick req)]
      (answers/create db (Integer/parseInt q_id) nick answer)
      [::response/found (str "/as/" q_id)])))

(defmethod ig/init-key :qa.handler.core/answer [_ {:keys [db]}]
  (fn [{[_ n] :ataraxy/result :as req}]
    (debug "answer" n)
    (let [q (questions/fetch db n)]
      (answer-page (get-nick req) q))))

;; /as/3 のように呼ばれる。
(defmethod ig/init-key :qa.handler.core/answers [_ {:keys [db]}]
  (fn [{[_ n] :ataraxy/result :as req}]
    (debug ":qa.handler.core/answers" n)
    (let [q (questions/fetch db n)
          answers (answers/find-by-keys db n)
          nick (get-nick req)]
      (debug "/as q:" q "nick:" nick "answers:" answers)
      (answers-page q answers nick))))

;; goods と answers の二つを書き換える。
(defmethod ig/init-key :qa.handler.core/good [_ {:keys [db]}]
  (fn [{[_ q-id a-id] :ataraxy/result :as req}]
   (let [from (get-nick req)
         ans (answers/find-one db a-id)
         g (:g ans)]
     (when-not (goods/found? db a-id from)
       (answers/update-answer! db {:g (inc g)} a-id)
       (goods/create! db a-id from))
     ;;BUG here? not a-id, q-id is required.
     (debug "req" req)
     [::response/found (str "/as/" q-id)])))

(defmethod ig/init-key :qa.handler.core/admin [_ _]
 (fn [req]
   (if (= (get-nick req) "hkimura")
    (admin-page)
    [::response/forbidden "access denied"])))

(defmethod ig/init-key :qa.handler.core/admin-goods [_ {:keys [db]}]
 (fn [{[_ {:strs [n]}] :ataraxy/result}]
   (let [goods (goods/find-goods db (Integer. n))]
     (debug "goods:" goods)
     (goods-page goods))))

(defmethod ig/init-key :qa.handler.core/who-goods [_ {:keys [db]}]
  (fn [{[_ n] :ataraxy/result}]
    (let [goods (goods/find-goods db (Integer/parseInt n))]
      (goods-page goods))))

(defmethod ig/init-key :qa.handler.core/my-goods [_ {:keys [db]}]
  (fn [{[_ nick] :ataraxy/result}]
    (let [s (goods/count-sent db nick)
          r (goods/count-received db nick)
          q (questions/count-my-questions db nick)
          a (answers/count-my-answers db nick)]
      [::response/ok
       (str "<h2>"nick ": A/Q = " a "/" q ", recv/sent = " r "/" s "</h2>")])))

;; recent n items? or
;; recent n mins?
(defmethod ig/init-key :qa.handler.core/recents [_ {:keys [db]}]
  (fn [_]
    (recents-page (answers/find-recents db 40))))
