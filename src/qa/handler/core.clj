(ns qa.handler.core
  (:require
   #_[ataraxy.core :as ataraxy]
   [ataraxy.response :as response]
   #_[clojure.java.io :as io]
   [integrant.core :as ig]
   [java-time.api :as jt]
   [next.jdbc :as jdbc]
   [next.jdbc.sql :as sql]
   [next.jdbc.result-set :as rs]
   [qa.boundary.answers :as answers]
   [qa.boundary.goods :as goods]
   [qa.boundary.questions :as questions]
   [qa.boundary.readers :as readers]
   [qa.view.page :refer
    [about-page
     admin-page
     answers-page
     goods-page
     index-page
     markdown-page
     markdown-preview-page
     question-new-page #_question-edit-page
     questions-page
     recents-page
     recent-goods-page
     readers-page
     points-page
     preview-page]]
   #_[ring.util.response :refer [redirect]]
   [taoensso.timbre :as timbre :refer [debug]]))

(defn get-login
  "request ヘッダの id 情報を文字列で返す。
   FIXME: develop ではエラーでも nobody を返したいが。"
  [req]
  (try
    (name (get-in req [:session :identity]))
    (catch Exception e (debug "get-login" (.getMessage e)))
    (finally "nobody")))

(defmethod ig/init-key :qa.handler.core/about [_ _]
  (fn [_]
    (about-page "2.3.12")))

(defmethod ig/init-key :qa.handler.core/index [_ _]
  (fn [req]
    (index-page req)))

(defmethod ig/init-key :qa.handler.core/question-new [_ _]
  (fn [_]
    (question-new-page)))

(defmethod ig/init-key :qa.handler.core/question-create [_ {:keys [db]}]
  (fn [{[_ params] :ataraxy/result :as req}]
    (let [nick (get-login req)
          question (get params "question")]
      (debug "question-create" "nick" nick "question" question)
      (questions/create db nick question)
      [::response/found "/qs"])))

;; questions-start 以降の q をリストする
(def ^:private questions-start "2023-04-01")
(defmethod ig/init-key :qa.handler.core/questions [_ {:keys [db]}]
  (fn [request]
    (timbre/info "questions")
    (let [ret (questions/fetch-after db questions-start)
          counts (answers/count-answers db)]
      (readers/create-reader db (get-login request) "qs" 0)
      (questions-page ret counts))))

(defmethod ig/init-key :qa.handler.core/questions-all [_ {:keys [db]}]
  (fn [_]
    (let [ret (questions/fetch-all db)
          counts (answers/count-answers db)]
      (questions-page ret counts))))
;;;
;;; answer/answers
;;;

(defmethod ig/init-key :qa.handler.core/answer-new [_ _]
  (fn [_]
    [::response/ok "answer-new"]))

;; changed 2023-03-21
(defmethod ig/init-key :qa.handler.core/answer-create [_ {:keys [db]}]
  (fn [{{:keys [q_id answer]} :params :as req}]
    (let [nick (get-login req)]
      ;;(timbre/debug "req" req)
      (timbre/debug "answer-create: q_id" q_id "nick" nick "answer" answer)
      (answers/create db (Integer/parseInt q_id) nick answer)
      [::response/found (str "/as/" q_id)])))

;; (defmethod ig/init-key :qa.handler.core/answer [_ {:keys [db]}]
;;   (fn [{[_ n] :ataraxy/result :as req}]
;;     (debug "answer" n)
;;     (let [q (questions/fetch db n)]
;;       (answer-page (get-login req) q))))

;; /as/3 のように呼ばれる。
(defmethod ig/init-key :qa.handler.core/answers [_ {:keys [db]}]
  (fn [{[_ n] :ataraxy/result :as req}]
    (debug ":qa.handler.core/answers" n)
    (let [q (questions/fetch db n)
          answers (answers/find-by-keys db n)
          nick (get-login req)]
      (readers/create-reader db nick "as" n)
      ;;(debug "/as q:" q "nick:" nick "answers:" answers)
      (answers-page q answers nick))))

;; 2023-03-21
(defmethod ig/init-key :qa.handler.core/markdown-preview [_ _]
  (fn [{[_ req] :ataraxy/result}]
    (debug "makrdown-preview" req)
    (preview-page (select-keys req ["q_id" "answer"]))))

;; goods と answers の二つを書き換える。
(defmethod ig/init-key :qa.handler.core/good [_ {:keys [db]}]
  (fn [{[_ q-id a-id] :ataraxy/result :as req}]
    (let [from (get-login req)
          ans (answers/find-one db a-id)
          g (:g ans)]
      ;;(debug "good" from ans g)
      (when-not (goods/found? db a-id from)
        (answers/update-answer! db {:g (inc g)} a-id)
        (goods/create! db q-id a-id from))
      [::response/found (str "/as/" q-id)])))

(defmethod ig/init-key :qa.handler.core/admin [_ _]
  (fn [req]
    (if (= (get-login req) "hkimura")
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
       (str "<h2>" nick "</h2>"
            "<p>Answered : " a "</p>"
            "<p>Questions: " q "</p>"
            "<p>Goods(received): " r "</p>"
            "<p>Goods(sent)    : " s "</p>")])))

(defmethod ig/init-key :qa.handler.core/recents [_ {:keys [db]}]
  (fn [_]
    (recents-page (answers/find-recents db 40))))

(defmethod ig/init-key :qa.handler.core/goods [_ {:keys [db]}]
  (fn [_]
    (let [ret (goods/recents db)]
      ;;(debug "goods ret" ret)
      (recent-goods-page ret))))

(def since (atom (->> (jt/zoned-date-time)
                      (jt/format "yyyy-MM-dd"))))

(defmethod ig/init-key :qa.handler.core/readers [_ {:keys [db]}]
  (fn [{[_ path n] :ataraxy/result}]
    (readers-page (readers/fetch-readers db path n @since) @since)))

(defmethod ig/init-key :qa.handler.core/since [_ _]
  (fn [{[_ timestamp] :ataraxy/result :as request}]
    (if (= "hkimura" (get-login request))
      (do
        (reset! since timestamp)
        [::response/found "/qs"])
      [::response/forbidden "forbidden"])))

;; DRY!
(defmethod ig/init-key :qa.handler.core/set-since [_ _]
  (fn [_]
    [::response/found (str "/since/" (jt/local-date))]))

(defmethod ig/init-key :qa.handler.core/md [_ _]
  (fn [req]
    (markdown-page (get-login req))))

(defmethod ig/init-key :qa.handler.core/md-post [_ {:keys [db]}]
  (fn [{[_ {:strs [md]}] :ataraxy/result :as request}]
    (readers/create-reader db (get-login request) "md" 0)
    (markdown-preview-page md)))

;;(def ^:private grading "jdbc:sqlite:db/grading.sqlite3")
(def grading
  (-> (jdbc/get-datasource
       {:dbtype "sqlite"
        :dbname "db/grading.sqlite3"})
      (jdbc/with-options
        {:builder-fn rs/as-unqualified-lower-maps})))

(defmethod ig/init-key :qa.handler.core/points [_ _]
  (fn [request]
    (let [login (get-login request)
          ret (sql/query
               grading
               ["select * from grading where login=?" login])]
      ;;(println "first ret" (first ret))
      (if (empty? ret)
        [::response/ok "no data"]
        (let [ret (first ret)]
          (points-page
           (:name ret)
           (:sid ret)
           (->> (dissoc ret
                        :created_at
                        :id
                        :login
                        :name
                        :sid
                        :updated_at)
                (sort-by key))))))))
