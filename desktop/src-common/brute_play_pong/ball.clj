(ns ^{:doc "Functions for managing the onscreen ball"}
    brute-play-pong.ball
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e]
              [brute-play-pong.component :as c]
              [clojure.math.numeric-tower :as m])
    (:import [brute_play_pong.component Ball Velocity]))


(defn create-ball
    "Creates a ball entity"
    []
    (let [ball (e/create-entity!)
          center-x (-> (graphics! :get-width) (/ 2) (m/round))
          center-y (-> (graphics! :get-width) (/ 2) (m/round))
          ball-size 20
          ball-center-x (- center-x (/ ball-size 2))
          ball-center-y (- center-y (/ ball-size 2))]

        (e/add-component! ball (c/->Ball))
        (e/add-component! ball (c/->Rectangle (rectangle ball-center-x ball-center-y ball-size ball-size) (color :white)))
        (e/add-component! ball (c/->Velocity (vector-2 0 200 :set-angle (* (rand) 360))))
        (println "Ball Velocity is: " (e/get-component ball Velocity))))

(defn destroy-ball
    "Kills the ball! Oh No!"
    []
    (doseq [ball (e/get-all-entities-with-component Ball)]
        (e/kill-entity! ball)))
