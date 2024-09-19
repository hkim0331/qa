(ns qa.handler.auth
  (:require
   [buddy.hashers :as hashers]
   [environ.core :refer [env]]
   [hato.client :as hc]
   [integrant.core :as ig]
   [qa.view.page :refer [index-page]]
   [ring.util.response :as resp]
   [taoensso.timbre :refer [info debug] :as timbre]))

(def l22 "https://l22.melt.kyutech.ac.jp")

(comment
  (env :qa-dev)
  :rcf)

(defmethod ig/init-key :qa.handler.auth/login [_ _]
  (fn [req]
    (index-page req)))

(defn auth? [login password]
  (debug "auth?" login password)
  (or (env :qa-dev)
      (let [ep (str l22 "/api/user/" login)
            user (:body (hc/get ep {:as :json}))]
        (and (some? user) (hashers/check password (:password user))))))

(defmethod ig/init-key :qa.handler.auth/login-post [_ _]
  (fn [{[_ {:strs [login password]}] :ataraxy/result}]
    (if (and (seq login) (auth? login password))
      (do
        (info "login success" login)
        ;; in safari, session :identity becomes nil. why?
        (-> (resp/redirect "/qs")
            (assoc-in [:session :identity] login))) ; was (keyword login)
      (do
        (info "login failure")
        (-> (resp/redirect "/")
            (assoc :flash "login failure"))))))

(defmethod ig/init-key :qa.handler.auth/logout [_ _]
  (fn [_]
    (-> (resp/redirect "/")
        (assoc :session {}))))
