(ns qa.main
  (:require [duct.core :as duct])
  (:gen-class))

(duct/load-hierarchy)

(defn -main [& args]
  (let [keys     (or (duct/parse-keys args) [:duct/daemon])
        profiles [:duct.profile/prod]]
    (-> (duct/resource "qa/config.edn")
        (duct/read-config)
        (duct/exec-config profiles keys))
    (System/exit 0)))
