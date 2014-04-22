(ns ^{:doc "Manages scoring of the game. Also recreates balls when they go out of bounds"}
    brute-play-pong.scoring
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e]
              [brute-play-pong.ball :as b])
    (:import [brute_play_pong.component Ball Rectangle Score PlayerScore CPUScore]))

(def buffer -100)

(defn- ^Boolean ball-has-scored
    "Is the ball at a point where it has scored?"
    [system ball]
    (let [rect (:rect (e/get-component system ball Rectangle))
          y (rectangle! rect :get-y)
          screen-height (graphics! :get-height)]
        (cond
            (< y buffer) :cpu
            (> (+ y (rectangle! rect :get-height) buffer) screen-height) :player
            :else nil)))

(defn- increment-score!
    "Increment the score for whichever player scored"
    [system player-scored]
    (let [type (if (= :player player-scored) PlayerScore CPUScore)
          entity (first (e/get-all-entities-with-component system type))]
        (swap! (:score (e/get-component system entity Score)) inc))
    system)

(defn process-one-game-tick
    "Physics, process one game tick"
    [system _]
    (reduce (fn [sys ball]
                (if-let [player-scored (ball-has-scored system ball)]
                    (-> sys
                        (increment-score! player-scored)
                        b/destroy-ball
                        b/create-ball)
                    sys))
            system (e/get-all-entities-with-component system Ball)))