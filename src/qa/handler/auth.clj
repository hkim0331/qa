(ns qa.handler.auth
  (:require
    [ataraxy.core :as ataraxy]
    [ataraxy.response :as response]
    [qa.boundary.auth :as auth]
    [qa.view.page :refer [login-page]]
    [integrant.core :as ig]
    [ring.util.response :refer [redirect]]
    [taoensso.timbre :as timbre :refer [debug]]))

(defmethod ig/init-key :qa.handler.core/login [_ _]
  (fn [_]
   (login-page)))

(defmethod ig/init-key :qa.handler.core/login-post [_ _]
  (fn [{[_ params] :ataraxy/result}]
   (debug "login-post" params)
   [::response/ok "login-post"]))

(defmethod ig/init-key :qa.handler.core/logout [_ _]
 (fn [_]
   (-> (redirect "/login")
       (assoc :session {}))))


