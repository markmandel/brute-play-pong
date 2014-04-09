(ns ^{:doc "Helper methods for dealing with paddles"}
    brute-play-pong.paddle
    (:require [brute.entity :as e])
    (:import [brute_play_pong.component Rectangle]))

(defn move-paddle
    "move the paddle to the left or the right"
    [speed delta paddle-type]
    (let [movement (* speed delta)]
        (doseq [paddle (e/get-all-entities-with-component paddle-type)]
            (let [rectangle (e/get-component paddle Rectangle)
                  geom (:rect rectangle)]
                (.setX geom (-> geom .getX (+ movement)))))))