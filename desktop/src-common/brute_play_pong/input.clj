(ns ^{:doc "Input system"}
    brute-play-pong.input
    (:use [play-clj.core])
    (:require [brute.entity :as e])
    (:import [brute_play_pong.component PlayerPaddle Rectangle]))

(def speed 150)

(defn- move-paddle
    "move the paddle to the left or the right"
    [speed delta]
    (let [movement (* speed delta)]
        (doseq [paddle (e/get-all-entities-with-component PlayerPaddle)]
            (let [rectangle (e/get-component paddle Rectangle)
                  geom (:rect rectangle)]
                (.setX geom (-> geom .getX (+ movement)))))))

(defn process-one-game-tick
    "Render all the things"
    [delta]
    (when (key-pressed? :dpad-left)
        (move-paddle (* -1 speed) delta))
    (when (key-pressed? :dpad-right)
        (move-paddle speed delta)))
