(defproject first_backend_clojure "0.1.0-SNAPSHOT"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring "1.8.0"]
                 [compojure "1.6.1"]
                 [cheshire "5.9.0"]
                 [org.xerial/sqlite-jdbc "3.30.1"]
                 [com.novemberain/monger "3.1.0"]]
  :main ^:skip-aot first-backend-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
