(ns first-backend-clojure.core
  (:gen-class)
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.params :as rmp]
            [ring.middleware.keyword-params :as rmkp]
            [first-backend-clojure.routes :as fbcr]))




(defn -main
  []
  (raj/run-jetty (-> fbcr/app-routes
                     rmkp/wrap-keyword-params
                     rmp/wrap-params)
                 {:port 65535
                  :join? false}))
