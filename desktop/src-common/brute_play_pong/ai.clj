(ns ^{:doc "Crazy smart AI for the CPU paddle"}
    brute-play-pong.ai
    (:use [play-clj.math])
    (:require [brute-play-pong.paddle :as p]
              [brute.entity :as e])
    (:import [brute_play_pong.component CPUPaddle Ball Rectangle]))

(def speed 200)

(defn process-one-game-tick
    "Render all the things"
    [system delta]
    (let [paddles (e/get-all-entities-with-component system CPUPaddle)
          ;; not very smart, always goes after the first ball
          ball (first (e/get-all-entities-with-component system Ball))
          b-center (-> (e/get-component system ball Rectangle) :rect (rectangle! :get-center (vector-2*)))]
        (doseq [paddle paddles]
            (let [p-center (-> (e/get-component system paddle Rectangle) :rect (rectangle! :get-center (vector-2*)))]
                (if (< (vector-2! p-center :x) (vector-2! b-center :x))
                    (p/move-paddle! system speed delta CPUPaddle)
                    (p/move-paddle! system (* -1 speed) delta CPUPaddle)))))
    system)