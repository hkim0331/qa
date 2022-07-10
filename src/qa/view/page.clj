(ns qa.view.page
  (:require
   [ataraxy.response :as response]
   [clojure.string :as str]
   [hiccup.page :refer [html5]]
   [hiccup.form :refer [form-to text-field password-field submit-button
                        text-area hidden-field]]
   [hiccup.util :refer [escape-html]]
   ;;[qa.handler.core :refer [goods]]
   [markdown.core :refer [md-to-html-string]]
   [ring.util.anti-forgery :refer [anti-forgery-field]]
   #_[taoensso.timbre :as timbre]))


(def version "1.6.1")

;; from r99c.route.home/wrap
(defn- wrap-aux
  [n s]
  (if (< (count s) n)
    s
    (str (subs s 0 n) "\n" (wrap-aux n (subs s n)))))

(defn- wrap
  "fold string `s` at column `n`"
  [n s]
  (str/join "\n" (map (partial wrap-aux n) (str/split-lines s))))

(defn ss
  "文字列 s の n 文字以降を切り詰めた文字列を返す。
   文字列長さが n に満たない時はそのまま。"
  [n s]
  (subs s 0 (min n (count s))))

(defn date-time
  "timestamp 文字列から YYYY/MM/DD hh:mm:ss を抜き出す"
  [tm]
  (subs (str tm) 0 19))

(defn page [& contents]
  [::response/ok
   (html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]]
    [:link
     {:rel "stylesheet"
      :href "https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
      :integrity "sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
      :crossorigin "anonymous"}]
    [:link
     {:rel "stylesheet"
      :type "text/css"
      :href "/css/styles.css"}]
    [:script {:type "text/javascript"}
     "function ok() {return window.confirm('OK?');}"]
    [:title "QA"]
    [:body
     [:div {:class "container"}
      contents
      [:p]
      [:p [:a {:href "/logout" :class "btn btn-warning btn-sm"} "logout"]]
      [:hr]
      "hkimura, " version]])])

(defn index-page [req]
  (page
   [:h2 "QA"]
   [:div.text-danger (:flash req)]
   (form-to
    [:post "/login"]
    (anti-forgery-field)
    (text-field {:placeholder "アカウント"} "login")
    (password-field {:placeholder "パスワード"} "password")
    (submit-button "login"))
   [:br]
   [:div {:class "row"}
    [:div {:class "col-3"}
     [:a {:href "https://www.youtube.com/watch?v=JktXHKx3r20"}
      [:img {:src "images/odyssey.jpg" :id "odyssey"}]] [:br]
     [:p {:class "sm"} "2001年宇宙の旅"]]
    [:div {:class "col-9"}
     [:p "聞いたことは忘れる。" [:br]
      "やったことは覚える。" [:br]
      "人に教えたことは身に付く。"]]]
   [:audio {:src "sounds/sorry-dave.mp3"
            :autoplay false
            :controls "controls"}]
   [:div
    [:ul
     [:li "回答しやすい質問をする練習と、回答できる質問には回答する練習。"]
     [:li "質問はテキスト、回答は Markdown で。"]
     [:li "「👍」は一回答に一回だけです。"]
     [:li "「👍」付いた回答にはちょびっとボーナス。"]]]))

(defn question-new-page []
  (page
   [:h2 "QA: Create a Question"]
   [:p "具体的な質問じゃないと回答つけにくい。"
    "短すぎる質問も長すぎる質問と同じく受信しない。"
    [:a {:href "/"} "注意事項"]]
   (form-to {:enctype "multipart/form-data"
             :onsubmit "return confirm('その質問は具体的か？')"}
            [:post "/q"]
            (anti-forgery-field)
            (text-area {:id "question"
                        :placeholder "テキストで。60 文字以内に改行するように。"}
                       "question")
            [:br]
            (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

;; 回答がついてなかったら 0 を表示する。
(defn- answer-count
  [cs q_id]
  (:count (first (filter #(= (:q_id %) q_id) cs)) 0))

(defn questions-page [qs cs]
  (page
   [:h2 "QA: Questions"]
   [:p "👉 のクリックで回答ページへ。"
    [:a {:href "/recents" :class "btn btn-success btn-sm"} "最近の回答"]
    "&nbsp;"
    [:a {:href "/goods" :class "btn btn-warning btn-sm"} "最近のいいね"]
    "&nbsp;"
    [:a {:href "/q" :class "btn btn-primary btn-sm"} "new question"]
    [:p [:a {:href "/readers/qs/0"} "readers"]]
    (for [q qs]
      [:p
       (:id q)
       ", "
       (escape-html (-> (:q q) str/split-lines first))
            ;;(escape-html (ss 30 (:q q)))
       "&nbsp;"
       [:a {:href (str "/my-goods/" (:nick q))} "[" (:nick q) "]"]
       "&nbsp;"
       [:a {:href (str "/as/" (:id q))}
        (str " 👉" (answer-count cs (:id q)))]])
    [:p [:a {:href "/q" :class "btn btn-primary btn-sm"} "new question"]]]))

(defn goods
  [n]
  (repeat n "👍"))

;; 0.7.6, p ではなく pre でメッセージを表示したことに伴い、
;; 過去に入れてもらった <br> を取り除く。
(defn- my-escape-html [s]
  (-> (str/replace s #"<br>" "")
      escape-html))

(defn answers-page [q answers nick]
  (page
   [:h2 "QA: Answers"]
   [:div [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]
   [:h4 (:nick q) "さんの質問 " (date-time (:ts q)) ","]
   [:pre {:class "question"} (my-escape-html (wrap 60 (:q q)))]
   [:p [:a {:href (str "/readers/as/" (:id q))} "readers"]]
   [:hr]
   [:h4 "Answers"]
   (for [a answers]
     (let [goods (goods (:g a))]
       [:div
        [:p [:span {:class "nick"} (:nick a)] "'s answer " (date-time (:ts a)) ","]
        (md-to-html-string (:a a))
        [:p [:a {:href (str "/good/" (:id q) "/" (:id a))
                 :onclick "alert('いいと思うところは何？ Markdown で書けないか'); return true;"} 
                goods]
         (when (= nick "hkimura")
           [:a {:href (str "/who-goods/" (:id a)) :class "red"}
            " &nbsp; "])]]))
   [:p
    (form-to {:enctype "multipart/form-data"
              :onsubmit "return confirm('その回答で OK ですか？')"}
             [:post "/a"]
             (anti-forgery-field)
             (hidden-field "q_id" (:id q))
             (text-area {:id "answer"
                         :placeholder "markdown OK"}
                        "answer")
             [:br]
             (submit-button {:class "btn btn-primary btn-sm"} "submit"))]
   [:p]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]))

(defn admin-page []
  (page
   [:h2 "QA Admin"]
   [:p "who goods?"]
   (form-to)
   [:post "/admin/goods"
    (anti-forgery-field)
    "good " (text-field {:id "n" :size 3} "n")
    " "
    (submit-button {:class "btn btn-primary btn-sm"} "submit")]))

(defn goods-page [goods]
  (page
   [:h2 "QA: goods"]
   [:table
    (for [g goods]
      [:tr
       [:td (:nick g)]
       [:td (date-time (:ts g))]])]))

(defn recents-page [answers]
  (page
   [:h2 "QA: recent answers"]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]
   [:ol
    (for [a answers]
      [:li (:nick a)
       " "
       [:a {:href (str "/as/" (:q_id a))} (escape-html (ss 20 (:a a)))]
       " "
       (date-time (:ts a))])]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]))

(defn recent-goods-page [answers]
  (page
   [:h2 "QA: recent goods"]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "QA Top"]]
   (into
    [:ol]
    (for [a answers]
      [:li
       (date-time (:ts a))
       " "
       [:a {:href  (str "/as/" (:q_id a))} (ss 28 (:q a))]]))))

(defn readers-page [readers since]
  (page
   [:h2 "QA: Who read since " since]
   [:p "ほんと、みんな、QA 読まないんだな。点数稼ぎの 👍 はさらに冷えるよ。"]
   [:p (->> (mapv :login readers)
            (interpose " ")
            (apply str))
    "(合計 " (count readers) ")"]))


