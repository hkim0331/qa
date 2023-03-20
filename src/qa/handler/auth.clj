(ns qa.handler.auth
  (:require
   #_[ataraxy.response :as response]
   [buddy.hashers :as hashers]
   [hato.client :as hc]
   [integrant.core :as ig]
   #_[qa.boundary.users :refer [find-user-by-login]]
   [qa.view.page :refer [index-page]]
   [ring.util.response :refer [redirect]]
   [taoensso.timbre :as timbre]))

(defmethod ig/init-key :qa.handler.auth/login [_ _]
  (fn [req]
    (index-page req)))

;; changed 2.0.0 2022-09-26
;; (defn auth? [db login password]
;;   (let [user (find-user-by-login db login)]
;;     (timbre/debug "auth?" user)
;;     (and (some? user) (hashers/check password (:password user)))))

(def l22 "https://l22.melt.kyutech.ac.jp")

(defn auth? [login password]
  (let [ep (str l22 "/api/user/" login)
        user (:body (hc/get ep {:as :json}))]
    (timbre/debug "auth?" user)
    (and (some? user) (hashers/check password (:password user)))))
    ;; (if (ig/ref :qa.auth?)
    ;;   (and (some? user) (hashers/check password (:password user)))
    ;;   true)))

(defmethod ig/init-key :qa.handler.auth/login-post [_ _]
  (fn [{[_ {:strs [login password]}] :ataraxy/result}]
    (if (and (seq login) (auth? login password))
      (do
        (timbre/info "login success")
        (-> (redirect "/qs")
            (assoc-in [:session :identity] (keyword login))))
      (do
        (timbre/info "login failure")
        (-> (redirect "/")
            (assoc :flash "login failure"))))))

(defmethod ig/init-key :qa.handler.auth/logout [_ _]
  (fn [_]
    (-> (redirect "/")
        (assoc :session {}))))
