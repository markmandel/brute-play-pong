(ns ^{:doc "Crazy smart AI for the CPU paddle"}
    brute-play-pong.ai
    (:use [play-clj.math])
    (:require [brute-play-pong.paddle :as p]
              [brute.entity :as e])
    (:import [brute_play_pong.component CPUPaddle Ball Rectangle]
             [com.badlogic.gdx.math Vector2]))

(def speed 200)

(defn process-one-game-tick
    "Render all the things"
    [delta]
    (let [paddles (e/get-all-entities-with-component CPUPaddle)
          ;; not very smart, always goes after the first ball
          ball (first (e/get-all-entities-with-component Ball))
          b-center (.getCenter (:rect (e/get-component ball Rectangle)) (Vector2.))]
        (doseq [paddle paddles]
            (let [p-center (.getCenter (:rect (e/get-component paddle Rectangle)) (Vector2.))]
                (if (< (.x p-center) (.x b-center))
                    (p/move-paddle speed delta CPUPaddle)
                    (p/move-paddle (* -1 speed) delta CPUPaddle))))))
