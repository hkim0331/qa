(ns qa.view.page
 (:require
  [ataraxy.response :as response]
  [hiccup.page :refer [html5]]
  [hiccup.form :refer [form-to text-field password-field submit-button
                       label text-area file-upload]]
  [ring.util.anti-forgery :refer [anti-forgery-field]]
  [taoensso.timbre :as timbre :refer [debug]]))

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
      :href "/css/style.css"}]
    [:title "QA"]
    [:body
     [:div {:class "container"}
       contents
      [:p]
      [:p [:a {:href "/logout" :class "btn btn-warning btn-sm"} "logout"]]
      [:hr]
      "hkimura."]])])

(defn login-page []
  (page
    [:h2 "QA: Login"]
    [:p "tp.melt と同じやつ。"]
    (form-to
      [:post "/login"]
      (anti-forgery-field)
      (text-field {:placeholder "ニックネーム"} "nick")
      (password-field {:placeholder "パスワード"} "password")
      (submit-button "login"))))

(defn question-new-page []
 (page
  [:h2 "QA: Create a Question"]
  (form-to {:enctype "multipart/form-data"}
           [:post "/q"]
           (anti-forgery-field)
           (text-area "question")
           [:br]
           (file-upload "file")
           [:br]
           (submit-button "submit"))))

(defn question-edit-page [& more]
 (page
  [:h2 "under construction"]
  [:p "このページは q の修正画面になる。"]))

(defn questions-page [qs]
 (debug "qs" qs)
 (page
  [:h2 "QA: Questions"]
  (into [:ol]
        (for [q qs]
          [:li (str (:q q) (:ts q))]))))