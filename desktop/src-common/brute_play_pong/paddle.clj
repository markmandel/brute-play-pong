(ns ^{:doc "Helper methods for dealing with paddles"}
    brute-play-pong.paddle
    (:use [play-clj.math])
    (:require [brute.entity :as e])
    (:import [brute_play_pong.component Rectangle]))

(defn move-paddle!
    "move the paddle to the left or the right"
    [system speed delta paddle-type]
    (let [movement (* speed delta)]
        (doseq [paddle (e/get-all-entities-with-component system paddle-type)]
            (let [rectangle (e/get-component system paddle Rectangle)
                  geom (:rect rectangle)]
                (rectangle! geom :set-x (-> geom (rectangle! :get-x) (+ movement)))))))