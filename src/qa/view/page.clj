(ns qa.view.page
 (:require
  [ataraxy.response :as response]
  [hiccup.page :refer [html5]]
  [hiccup.form :refer [form-to text-field password-field submit-button label]]
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
      :href "/css/style.css"}]
    [:title "QA"]
    [:body
     [:div {:class "container"}
       contents
      [:hr]
      "hkimura."]])])

(defn login-page []
  (page
    [:h2 "QA: Login"]
    (form-to
      [:post "/login"]
      (anti-forgery-field)
      (text-field {:placeholder "ニックネーム"} "nick")
      (password-field {:placeholder "パスワード"} "password")
      (submit-button "login"))))
