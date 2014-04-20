(ns ^{:doc "Input system"}
    brute-play-pong.input
    (:use [play-clj.core])
    (:require [brute-play-pong.paddle :as p])
    (:import [brute_play_pong.component PlayerPaddle]))

(def speed 200)

(defn process-one-game-tick
    "Render all the things"
    [system delta]
    (when (key-pressed? :dpad-left)
        (p/move-paddle! system (* -1 speed) delta PlayerPaddle))
    (when (key-pressed? :dpad-right)
        (p/move-paddle! system speed delta PlayerPaddle))
    system)