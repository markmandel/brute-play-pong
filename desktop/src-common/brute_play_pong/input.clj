(ns ^{:doc "Input system"}
    brute-play-pong.input
    (:use [play-clj.core])
    (:require [brute-play-pong.paddle :as p]))

(def speed 150)

(defn process-one-game-tick
    "Render all the things"
    [delta]
    (when (key-pressed? :dpad-left)
        (p/move-paddle (* -1 speed) delta))
    (when (key-pressed? :dpad-right)
        (p/move-paddle speed delta)))