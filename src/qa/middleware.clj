(ns qa.middleware
  (:require
   #_[ataraxy.core :as ataraxy]
   [ataraxy.response :as response]
   [buddy.auth :refer [authenticated? throw-unauthorized]]
   [buddy.auth.accessrules :refer [restrict]]
   [buddy.auth.backends.session :refer [session-backend]]
   [buddy.auth.middleware :refer [wrap-authorization wrap-authentication]]
   [integrant.core :as ig]
   #_[ring.middleware.params :refer [wrap-params]]
   #_[taoensso.timbre :refer [debug]]))

(defn unauthorized-handler
  [request _]
  (if (authenticated? request)
    (throw-unauthorized) ;{:status 403 :body "error"}
    [::response/found  "/login"]))

(def auth-backend
  (session-backend {:unauthorized-handler unauthorized-handler}))

(defmethod ig/init-key :qa.middleware/auth [_ _]
  (fn [handler]
    (-> handler
        (restrict {:handler authenticated?})
        (wrap-authorization  auth-backend)
        (wrap-authentication auth-backend))))
