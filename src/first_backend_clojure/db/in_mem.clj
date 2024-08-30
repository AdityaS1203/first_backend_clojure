(ns first-backend-clojure.db.in-mem
  (:require [clojure.string :as cs]))


(def conn (atom {}))


(defn check-app?
  [store app_name]
  (let [exist? (get @store app_name)]
    (println exist?)
    (if exist?
      exist?
      false)))


(defn create!
  [store name plat creator]
  (let [details (check-app? store name)]
    (if details
      false
      (do (swap! store assoc name [plat creator])
          true))))


(defn reads
  [store name]
  (let [details (check-app? store name)]
    (if details
      (do (println details)
      details)
      false)))


(defn update!
  [store name new-platform]
  (let [details (check-app? store name)
        new-platform-vector (cs/split new-platform #",")]
    (if details
      (do (swap! store assoc name [new-platform-vector (second details)])
          true)
      false)))


(defn delete!
  [store app-name]
  (let [details (check-app? store app-name)]
    (if details
      (do (swap! store dissoc app-name)
          true)
      false)))
