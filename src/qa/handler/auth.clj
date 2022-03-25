(ns qa.handler.auth
  (:require
   [ataraxy.response :as response]
   [buddy.hashers :as hashers]
   [qa.boundary.users :refer [find-user-by-login]]
   [qa.view.page :refer [login-page]]
   [integrant.core :as ig]
   [ring.util.response :refer [redirect]]
   [taoensso.timbre :as timbre :refer [debug]]))

;;(timbre/set-level! :debug)

(defmethod ig/init-key :qa.handler.auth/login [_ _]
  (fn [_]
    (login-page)))

(defn auth? [db login password]
  (let [user (find-user-by-login db login)]
    (timbre/debug "auth?" user)
    (and (some? user) (hashers/check password (:password user)))))

(defmethod ig/init-key :qa.handler.auth/login-post [_ {:keys [db]}]
  (fn [{[_ {:strs [login password]}] :ataraxy/result}]
    (timbre/debug "login-post" login password)
    (if (and (seq login) (auth? db login password))
      (-> (redirect "/qs")
          (assoc-in [:session :identity] (keyword login))) ; keyword の必要性
      (-> (redirect "/")
          (assoc :flash "login failure")))))

(defmethod ig/init-key :qa.handler.auth/logout [_ _]
  (fn [_]
    (-> (redirect "/")
        (assoc :session {}))))
