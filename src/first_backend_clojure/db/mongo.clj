(ns first-backend-clojure.db.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :refer [$set]])
  (:import [com.mongodb MongoOptions ServerAddress DB WriteConcern]
           [org.bson.types ObjectId]))


(def db
  (let [opts (mg/mongo-options {:threads-allowed-to-block-for-connection-multiplier 300})
        sa   (mg/server-address "127.0.0.1" 27017)
        conn (mg/connect sa opts)]
    (mg/get-db conn "mongo-for-fbc")))



(defn check-app?
  [db name]
  (let [result (mc/find-one-as-map db "app" {:app name})]
    (if (= result nil)
      false
      true)))


  (defn create!
    [db name platform creator]
    (if (check-app? db name)
      false
      (do (mc/insert db "app" {:_id (ObjectId.) :app name :supported-platform platform :creator creator})
       true)))


  (defn reading
    [db name]
    (let [result (mc/find-one-as-map db "app" {:app name})]
      (if (= result nil)
        false
        (let [{:keys [_id]} result]
          {:id (.toString _id)
           :app (:app result)
           :supported-platform (:supported-platform result)
           :creator (:creator result)}))))


  (defn update!
    [db name platform]
    (if (check-app? db name)
       (do (mc/update db "app" {:app name} {$set {:supported-platform platform}} {:multi true})
        true)
      false))


  (defn delete!
    [db name]
    (if (check-app? db name)
      (do (mc/remove db "app" {:app name})
        true)
      false))
