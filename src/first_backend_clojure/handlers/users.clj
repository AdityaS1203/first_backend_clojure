(ns first-backend-clojure.handlers.users
  (:require [first-backend-clojure.db.in-mem :as fbcim]
            [cheshire.core :as json]
            [clojure.string :as cs]))


(defn get-home-handler
  [request]
  {:status 200
   :body "This is home route"})


(defn post-app-details
  [{:keys [body] :as request}]
  (let [data (json/parse-string (slurp body) true)
        {:keys [app supported-platform creator]} data
        supported-platform-vector (cs/split (str supported-platform) #",")
        success? (fbcim/create! fbcim/conn (str app) supported-platform-vector (str creator))]
    (if success?
      {:status 201
       :body "App creation successful"}
      {:status 400
       :body "App already exist"})))


(defn get-app-details
  [{:keys [params] :as request}]
  (let [{:keys [app]} params
        result (fbcim/reads fbcim/conn app)]
    (println (json/generate-string result))
    (if result
      {:status 200
       :headers {"content-type" "application/json"}
       :body (json/generate-string result)}
      {:status 404
       :body "App doesn't exist"})))


(defn update-app-details
  [{:keys [params]}]
  (let [{:keys [app supported-platform]} params
        success? (fbcim/update! fbcim/conn app supported-platform)]
    (if success?
      {:status 200
       :body "Update successful"}
      {:staus 404
       :body "App doesn't exist"})))


(defn delete-app-details
  [{:keys [params]}]
  (let [{:keys [app]} params]
  (assert (some? app) "app_name can't be nil")
  (let [success? (fbcim/delete! fbcim/conn app)]
    (if success?
      {:status 200
     :body "App deleted successfully"}
      {:status 404
       :body "App doesn't exist"}))))