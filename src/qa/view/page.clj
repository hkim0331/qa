(ns qa.view.page
 (:require
  [ataraxy.response :as response]
  [clojure.string :as str]
  [hiccup.page :refer [html5]]
  [hiccup.form :refer [form-to text-field password-field submit-button
                       label text-area file-upload hidden-field]]
  [ring.util.anti-forgery :refer [anti-forgery-field]]
  [taoensso.timbre :as timbre :refer [debug]]))

(def version "0.2.2")

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

(defn login-page []
  (page
    [:h2 "QA: Login"]
    [:p {:class "red"} "例によってオープン戦。来週から本番？"]
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
  [:p "具体的な質問じゃないと回答つけづらいだろう。"
   "短すぎる質問も長すぎる質問と同じく受信しない。"]
  (form-to {:enctype "multipart/form-data"}
           [:post "/q"]
           (anti-forgery-field)
           (text-area {:id "question"} "question")
           [:br]
           [:div (label "file" "(まだプログラムしてない)") (file-upload "file")]
           [:br]
           (submit-button  {:class "btn btn-primary btn-sm"}　"submit"))))

(defn question-edit-page [& more]
 (page
  [:h2 "under construction"]
  [:p "このページは q の修正画面になる。"]))

;;FIXME: htmlはエスケープしなくちゃ。
(defn ss
 "文字列 s の n 文字以降を '...' でリプレースした文字列を返す。
  文字列長さが n に満たない時はそのまま文字列を返す。"
  [n s]
  (if (< (count s) n)
    s
    (str (subs s 0 n) "...")))

(defn st
 "時刻表示を短くする。関数名は iso でもいいかも。
  引数 tm は time オブジェクト。"
  [tm]
  (subs (str tm) 0 10)) ; hh:mm:ss を入れるなら s/10/19/

(defn questions-page [qs]
 (debug "qs" qs)
 (page
  [:h2 "QA: Questions"]
  [:p "質問をクリックしたら回答ページへ飛ぶ。"]
  (into [:ol]
        (for [q qs]
          [:li (str (escape-html (ss 20 (:q q)))
                    " by " (:nick q)
                    " at " (st (:ts q)))
               [:a {:href (str "/as/" (:id q))} " 👉"]]))
  [:p [:a {:href "/q" :class "btn btn-primary btn-sm"} "new"]]))

(defn answers-page [q answers]
  (page
   [:h2 "QA: Answers"]
   [:p "いいねができるように。"]
   [:h4 (:nick q) "さんの質問"]
   [:p {:class "question"} (escape-html (:q q))]
   (for [a answers]
     [:div
      [:p {:class "nick"} "from " (:nick a) ","]
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

