(ns qa.view.page
 (:require
  [ataraxy.response :as response]
  [clojure.string :as str]
  [hiccup.page :refer [html5]]
  [hiccup.form :refer [form-to text-field password-field submit-button
                       label text-area file-upload hidden-field]]
  [ring.util.anti-forgery :refer [anti-forgery-field]]
  [taoensso.timbre :as timbre :refer [debug]]))

(def version "0.3.0-SNAPSHOT")

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
    [:div {:class "col-2"}
     [:img {:src "images/odyssey.jpg" :id "odyssey"}] [:br]
     [:p {:class "sm"} "2001年宇宙の旅"]]
    [:div {:class "col-10"}
      [:p "聞いたことは忘れる。"　[:br]
          "やったことは覚える。" [:br]
          "人に教えたことは身に付く。"]]]
   [:div
    [:ul
     [:li {:class "red"} "例によってオープン戦。6/23から本番。"]
     [:li "回答しやすい質問をする練習と、"]
     [:li "回答できる質問には回答する練習。"]
     [:li "語尾だけ丁寧、意味不明な質問・回答はよくない。"]
     [:li "「いいね」着いた回答にはボーナス。（まだプログラムしてない 6/19）"]
     [:li "イメージのアップロードは、「いいね」の後にプログラムの予定。"]]]
   [:p [:a {:href "/qs" :class "btn btn-primary btn-sm"} "go!"]]))

(defn login-page []
  (page
    [:h2 "QA: Login"]
    [:p "tp.melt と同じやつで。"]
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
   "短すぎる質問も長すぎる質問と同じく受信しない。"]
  (form-to {:enctype "multipart/form-data"}
           [:post "/q"]
           (anti-forgery-field)
           (text-area {:id "question"} "question")
           [:br]
           [:div (label "file" "(まだプログラムしてない)") (file-upload "file")]
           [:br]
           (submit-button  {:class "btn btn-primary btn-sm"}　"submit"))))

(defn question-edit-page
  "このページは q の修正画面になる。"
  [& more]
  (page
   [:h2 "under construction"]))

(defn ss
 "文字列 s の n 文字以降を '...' でリプレースした文字列を返す。
  文字列長さが n に満たない時はそのまま文字列を返す。"
  [n s]
  (if (< (count s) n)
    s
    (str (subs s 0 n) "...")))

(defn date
 "時刻表示を短くする。
  引数 tm は time オブジェクト。"
  [tm]
  (subs (str tm) 0 10))

(defn date-time
  [tm]
  (subs (str tm) 0 19))

(defn questions-page [qs]
 (debug "qs" qs)
 (page
  [:h2 "QA: Questions"]
  [:p "👉 のクリックで回答ページへ。"]
  (into [:ol]
        (for [q qs]
          [:li (escape-html (ss 20 (:q q)))
               [:a {:href (str "/as/" (:id q))} " 👉"]]))
  [:p [:a {:href "/q" :class "btn btn-primary btn-sm"} "new"]]))

(defn answers-page [q answers]
  (page
   [:h2 "QA: Answers"]
   [:h4 (:nick q) "さんの質問 " (date-time (:ts q)) ","]
   [:p {:class "question"} (escape-html (:q q))]
   (for [a answers]
     [:div
      [:p [:span {:class "nick"} (:nick a)] "'s answer "
          (date-time (:ts a)) ","]
      [:p {:class "answer"} (escape-html (:a a))]
      [:p {:class "good"} [:a {:href "/good"} "いいね"] " まだ動作しません"]])
   [:p]
   [:p [:a {:href (str "/a/" (:id q))
            :class "btn btn-primary btn-sm"}
        "answer"]]))

(defn answer-page [nick q]
  (debug q)
  (page
   [:h2 "QA: Please, " nick, "!"]
   [:p (escape-html (:q q))]
   [:h4 "your answer:"]
   (form-to {:enctype "multipart/form-data"}
            [:post "/a"]
            (anti-forgery-field)
            (hidden-field "q_id" (:id q))
            (text-area {:id "answer"} "answer")
            [:br]
            [:div (label "file" "(必要なら)") (file-upload "file")]
            [:br]
            (submit-button {:class "btn btn-primary btn-sm"} "submit"))))

