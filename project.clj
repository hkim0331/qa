(defproject qa "v2.10.773"
  :description "qa system for my literacy classes"
  :url "https://qa.melt.kyutech.ac.jp"
  :min-lein-version "2.0.0"

  :dependencies
  [[buddy/buddy-auth "3.0.323"]
   [buddy/buddy-hashers "2.0.167"]
   [clojure.java-time "1.4.2"]
   [com.github.seancorfield/next.jdbc "1.3.939"]
   [com.fasterxml.jackson.core/jackson-core "2.17.2"]
   [duct/core "0.8.1"]
   [duct/module.ataraxy "0.3.0"]
   [duct/module.logging "0.5.0"]
   [duct/module.sql "0.6.1"]
   [duct/module.web "0.7.3"]
   [environ "1.2.0"]
   [hiccup "1.0.5"]
   [markdown-clj "1.12.1"]
   [org.clojure/clojure "1.12.0"]
   [org.postgresql/postgresql "42.7.4"]
   ;;
   [cheshire/cheshire "5.13.0"]
   [hato/hato "1.0.0"]
   ;; [ring "1.12.2"] ;; no 1.12.1
   ;; feature/safari, 2024-09-20
   [ring/ring-anti-forgery "1.3.1"]
   [ring/ring-core "1.12.2"]
   [ring/ring-defaults "0.5.0"]
   [ring/ring-jetty-adapter "1.12.2"]
   ;;
   [dev.weavejester/medley "1.8.1"]
   [com.taoensso/timbre "6.5.0"]
   ]

  :plugins [[duct/lein-duct "0.12.3"]]
  :main ^:skip-aot qa.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.3"]
                                   [hawk "0.2.11"]
                                   [eftest "0.6.0"]
                                   [kerodon "0.9.1"]]}})
