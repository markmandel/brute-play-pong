(ns ^{:doc "Manages scoring of the game. Also recreates balls when they go out of bounds"}
    brute-play-pong.scoring
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e]
              [brute-play-pong.ball :as b])
    (:import [brute_play_pong.component Ball Rectangle]))

(def buffer -100)

(defn- ^Boolean ball-has-scored
    "Is the ball at a point where it has scored?"
    [ball]
    (let [rect (:rect (e/get-component ball Rectangle))
          y (rectangle! rect :get-y)
          screen-height (graphics! :get-height)]
        (cond
            (< y buffer) :cpu
            (> (+ y (rectangle! rect :get-height) buffer) screen-height) :player
            :else nil)))

(defn process-one-game-tick
    "Physics, process one game tick"
    [delta]
    (doseq [ball (e/get-all-entities-with-component Ball)]
        (when (ball-has-scored ball)
            (println "SCORED!!!")
            (b/destroy-ball)
            (b/create-ball))))



