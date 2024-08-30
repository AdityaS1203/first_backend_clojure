(ns first-backend-clojure.routes
(:require [first-backend-clojure.handlers.users :as fbch]
          [compojure.core :refer [defroutes GET POST PUT DELETE ANY]]
          [first-backend-clojure.handlers.mongoHandler :as fbchm]))


(defroutes app-routes

  ;;;;;;;;;;ATOM ROUTES
  (GET "/" _ fbch/get-home-handler)
  (GET "/details" _ fbch/get-app-details)
  (POST "/add" _ fbch/post-app-details)
  (PUT "/update"  _ fbch/update-app-details)
  (DELETE "/delete" _ fbch/delete-app-details)

  ;;;;;;;;;MONGODB ROUTES
  (GET "/details/mongo" _ fbchm/get-app-details)
  (POST "/add/mongo" _ fbchm/post-app-details)
  (PUT "/update/mongo" _ fbchm/update-app-details)
  (DELETE "/delete/mongo" _ fbchm/delete-app-details)
  (ANY "*" _ {:status 404}))

