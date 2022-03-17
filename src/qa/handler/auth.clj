(ns qa.handler.auth
  (:require
   [ataraxy.response :as response]
   [buddy.hashers :as hashers]
   [qa.boundary.users :refer [find-user-by-nick]]
   [qa.view.page :refer [login-page]]
   [integrant.core :as ig]
   [ring.util.response :refer [redirect]]
   #_[taoensso.timbre :as timbre :refer [debug]]))

(defmethod ig/init-key :qa.handler.auth/login [_ _]
  (fn [_]
    (login-page)))

(defn auth? [db nick password]
  (let [user (find-user-by-nick db nick)]
    (and (some? user) (hashers/check password (:password user)))))

(defmethod ig/init-key :qa.handler.auth/login-post [_ {:keys [db]}]
  (fn [{[_ {:strs [nick password]}] :ataraxy/result}]
    (if (and (seq nick) (auth? db nick password))
      (-> (redirect "/qs")
          (assoc-in [:session :identity] (keyword nick))) ; keyword の必要性
      [::response/found "/login"])))

(defmethod ig/init-key :qa.handler.auth/logout [_ _]
  (fn [_]
    (-> (redirect "/login")
        (assoc :session {}))))
