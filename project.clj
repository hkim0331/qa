(defproject qa "1.7.6"
  :description "qa system for my literacy classes"
  :url "https://qa.melt.kyutech.ac.jp"
  :min-lein-version "2.0.0"
  :dependencies [[buddy/buddy-auth "3.0.323"]
                 [buddy/buddy-hashers "1.8.158"]
                 [clojure.java-time "0.3.3"]
                 [com.github.seancorfield/next.jdbc "1.2.780"]
                 [com.fasterxml.jackson.core/jackson-core "2.13.3"]
                 [duct/core "0.8.0"]
                 [duct/module.ataraxy "0.3.0"]
                 [duct/module.logging "0.5.0"]
                 [duct/module.sql "0.6.1"]
                 [duct/module.web "0.7.3"]
                 [environ "1.2.0"]
                 [hiccup "1.0.5"]
                 [markdown-clj "1.11.2"]
                 [org.clojure/clojure "1.11.1"]
                 [org.postgresql/postgresql "42.4.0"]]
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
                  :dependencies   [[integrant/repl "0.3.2"]
                                   [hawk "0.2.11"]
                                   [eftest "0.5.9"]
                                   [kerodon "0.9.1"]]}})
