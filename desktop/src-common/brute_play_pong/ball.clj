(ns ^{:doc "Functions for managing the onscreen ball"}
    brute-play-pong.ball
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e]
              [brute-play-pong.component :as c]
              [clojure.math.numeric-tower :as m])
    (:import [brute_play_pong.component Ball Velocity]))

(defn- create-random-angle
    "Pick an angle, but anything close to 90 or 180 degress is totally out."
    []
    (let [attempt (* (rand) 360)]
        (cond
            (and (>= attempt 0) (< attempt 10)) (create-random-angle)
            (and (> attempt 350) (<= attempt 360)) (create-random-angle)
            (and (> attempt 170) (< attempt 190)) (create-random-angle)
            :else attempt)))

(defn create-ball
    "Creates a ball entity"
    [system]
    (let [ball (e/create-entity)
          center-x (-> (graphics! :get-width) (/ 2) (m/round))
          center-y (-> (graphics! :get-height) (/ 2) (m/round))
          ball-size 20
          ball-center-x (- center-x (/ ball-size 2))
          ball-center-y (- center-y (/ ball-size 2))
          angle (create-random-angle)]
        (-> system
            (e/add-entity ball)
            (e/add-component ball (c/->Ball))
            (e/add-component ball (c/->Rectangle (rectangle ball-center-x ball-center-y ball-size ball-size) (color :white)))
            (e/add-component ball (c/->Velocity (vector-2 0 300 :set-angle angle))))))

(defn destroy-ball
    "Kills the ball! Oh No!"
    [system]
    (reduce (fn [sys ball] (e/kill-entity sys ball))
            system (e/get-all-entities-with-component system Ball)))
