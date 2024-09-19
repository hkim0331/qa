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
   [taoensso.timbre :refer [info debug]]))

(defn unauthorized-handler
  [request _]
  (debug "request session identity" (get-in [:session :identity] request))
  (debug "authenticated?" (authenticated? request))
  (if (authenticated? request)
    (do
      (info "unauthorized-handler: authenticated")
      (throw-unauthorized))
    (do
      (info "unauthorized-handler: unauthenticated")
      [::response/found  "/login"])))

(def auth-backend
  (session-backend {:unauthorized-handler unauthorized-handler}))

(defn probe [handler]
 (fn [req]
  (debug "probe session identity:" (get-in req [:session :identity]))
  (handler req)))

(defmethod ig/init-key :qa.middleware/auth [_ _]
  (fn [handler]
    (-> handler
        (restrict {:handler authenticated?})
        (wrap-authorization  auth-backend)
        (wrap-authentication auth-backend)
        probe)))
