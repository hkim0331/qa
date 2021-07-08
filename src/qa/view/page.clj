(ns qa.view.page
 (:require
  [ataraxy.response :as response]
  [clojure.string :as str]
  [hiccup.page :refer [html5]]
  [hiccup.form :refer [form-to text-field password-field submit-button
                       label text-area file-upload hidden-field]]
  ;[qa.handler.core :refer [goods]]
  [ring.util.anti-forgery :refer [anti-forgery-field]]
  [taoensso.timbre :as timbre :refer [debug]]))

(def version "0.4.3")

(defn unescape-br
  "文字列 s 中のすべての &lt;br を<br でリプレースバック。"
  [s]
  (str/replace s #"&lt;br" "<br"))

(defn escape-html
  "文字列 s 中のすべての < を &lt; でリプレース。"
  [s]
  (str/replace s #"<" "&lt;"))

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
      "hkimura, " version "."]])])

(defn index-page []
 (page
   [:h2 "QA"]
   [:audio {:src "sounds/sorry-dave.mp3"
            :autoplay "autoplay"
            :controls "controls"}]
   [:div {:class "row"}
    [:div {:class "col-3"}
     [:img {:src "images/odyssey.jpg" :id "odyssey"}] [:br]
     [:p {:class "sm"} "2001年宇宙の旅"]]
    [:div {:class "col-9"}
      [:p "聞いたことは忘れる。" [:br]
          "やったことは覚える。" [:br]
          "人に教えたことは身に付く。"]]]
   [:div
    [:ul
     [:li "回答しやすい質問をする練習と、"]
     [:li "回答できる質問には回答する練習。"]
     [:li "語尾だけ丁寧、意味不明な質問・回答はよくない。"]
     [:li "「いいね」付いた回答にはボーナス。"]
     [:li "「いいね」付けた人と、質問出した人にもちょっとだけボーナス。"]
     [:li "イメージのアップロードはこの後プログラムの予定。"]]]
   [:p [:a {:href "/qs" :class "btn btn-primary btn-sm"} "Go!"]]))

(defn login-page []
  (page
    [:h2 "QA: Login"]
    [:p "tp.melt と同じやつで。"
     [:a {:href "/"} "注意事項"]]
    (form-to
      [:post "/login"]
      (anti-forgery-field)
      (text-field {:placeholder "ニックネーム"} "nick")
      (password-field {:placeholder "パスワード"} "password")
      (submit-button "login"))))

(defn question-new-page []
 (page
  [:h2 "QA: Create a Question"]
  [:p "具体的な質問じゃないと回答つけづらい。"
   "短すぎる質問も長すぎる質問と同じく受信しない。"
   [:a {:href "/"} "注意事項"]]
  (form-to {:enctype "multipart/form-data"
            :onsubmit "return ok()"}
           [:post "/q"]
           (anti-forgery-field)
           (text-area {:id "question"} "question")
           [:br]
           [:div (label "file" "(まだプログラムしてない)") (file-upload "file")]
           [:br]
           (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

(defn question-edit-page
  "このページは q の修正画面になる。"
  [& more]
  (page
   [:h2 "under construction"]))

(defn ss
 "文字列 s の n 文字以降を切り詰めた文字列を返す。
  文字列長さが n に満たない時はそのまま。"
  [n s]
  (subs s 0 (min n (count s))))

(defn date
 "時刻表示を短くする。
  引数 tm は time オブジェクト。"
  [tm]
  (subs (str tm) 0 10))

(defn date-time
  [tm]
  (subs (str tm) 0 19))

(defn questions-page [qs]
  ;; FIXME: もう少しコンサイスなデバッグメッセージ
  ;;(debug "qs" qs)
  (page
   [:h2 "QA: Questions"]
   [:p "👉 のクリックで回答ページへ。" [:a {:href "/"} "注意事項"]]
   (into [:ol {:reversed "reversed"}]
         (for [q qs]
           [:li [:span {:class "skyblue"} (:nick q)]
                " "
                (escape-html (ss 28 (:q q)))
                [:a {:href (str "/as/" (:id q))}
                    " 👉"]]))
   [:p [:a {:href "/q" :class "btn btn-primary btn-sm"} "new"]]))

(defn goods
  [n]
  (repeat n "👍"))

(defn answers-page [q answers]
  (page
   [:h2 "QA: Answers"]
   [:p [:a {:href "/"} "注意事項"] "・" [:a {:href "/admin"} "Admin"]]
   [:h4 (:nick q) "さんの質問 " (date-time (:ts q)) ","]
   [:p {:class "question"} (escape-html (:q q))]
   (for [a answers]
     [:div
      [:p [:span {:class "nick"} (:nick a)] "'s answer "
       (date-time (:ts a)) ","]
      [:p {:class "answer"} (unescape-br (escape-html (:a a)))]
      [:p [:a {:href (str "/good/" (:id a))} (goods (:g a))]]])
   [:p]
   [:p [:a {:href (str "/a/" (:id q))
            :class "btn btn-primary btn-sm"}
        "answer"]]
   [:p [:a {:href "/qs" :class "btn btn-success btn-sm"} "questions"]]))

(defn answer-page [nick q]
  (debug q)
  (page
   [:h2 "QA: Please, " nick, "!"]
   [:p [:a {:href "/"} "注意事項"]]
   [:p (escape-html (:q q))]
   [:h4 "your answer:"]
   (form-to {:enctype "multipart/form-data"
             :onsubmit "return ok()"}
            [:post "/a"]
            (anti-forgery-field)
            (hidden-field "q_id" (:id q))
            (text-area {:id "answer"} "answer")
            [:br]
            [:div (label "file" "(必要なら)") (file-upload "file")]
            [:br]
            (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

(defn admin-page []
  (page
    [:h2 "QA Admin"]
    [:p "who goods?"]
    (form-to
      [:post "/admin/goods"]
      (anti-forgery-field)
      "good " (text-field {:id "n" :size 3} "n")
      " "
      (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

(defn goods-page [goods]
  (page
   [:h2 "QA: goods"]
   [:table
     (for [g goods]
       [:tr
        [:td (:nick g)]
        [:td (date-time (:ts g))]])]))
