(ns first-backend-clojure.handlers.mongoHandler
  (:require [first-backend-clojure.db.mongo :as fbcdbm]
            [cheshire.core :as json]
            [clojure.string :as cs]))


  (defn post-app-details
    [{:keys [body] :as request}]
    (let [data (json/parse-string (slurp body) true)
          {:keys [app supported-platform creator]} data
          supported-platform-vector (cs/split (str supported-platform) #",")
          success? (fbcdbm/create! fbcdbm/db (str app) supported-platform-vector (str creator))]
      (if success?
        {:status 201
         :body "Successfully added"}
        {:status 400
         :body "App already exist"})))


  (defn get-app-details
    [{:keys [params] :as request}]
    (println  params)
    (let [{:keys [app]} params
          details (fbcdbm/reading fbcdbm/db app)]
      (if (not= details false)
        {:status 200
       :body (json/generate-string details)}
        {:status 404
         :body "App doesn't exist"})))


  (defn update-app-details
    [{:keys [params] :as request}]
    (let [{:keys [app supported-platform]} params
          supported-platform-vector (cs/split (str supported-platform) #",")
          success? (fbcdbm/update! fbcdbm/db app supported-platform-vector)]
      (if success?
        {:status 200
       :body "Updation successful"}
        {:status 404
         :body "App doesn't exist"})))


  (defn delete-app-details
    [{:keys [params] :as request}]
    (let [{:keys [app]} params
          success? (fbcdbm/delete! fbcdbm/db app)]
      (if success?
        {:status 200
         :body "Deletion successful"}
        {:status 404
         :body "There is no such app in db"})))