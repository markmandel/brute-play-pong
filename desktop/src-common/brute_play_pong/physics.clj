(ns ^{:doc "Physics system"}
    brute-play-pong.physics
    (:use [play-clj.math])
    (:require [brute.entity :as e])
    (:import [brute_play_pong.component Rectangle]))

(defn- keep-rects-in-world
    []
    (doseq [entity (e/get-all-entities-with-component Rectangle)]
        (let [rect (e/get-component entity Rectangle)
              colour (:colour rect)
              geom (:rect rect)
              screen-width (graphics! :get-width)
              geom-x (rectangle! geom :get-x)
              geom-width (rectangle! geom :get-width)]
               (when (< geom-x 0)
                   (rectangle! geom :set-x 0))
               (when (> (+ geom-x geom-width) screen-width)
                   (rectangle! geom :set-x (- screen-width geom-width)))


            )))

(defn process-one-game-tick
    "Physics, process one game tick"
    [delta]
    (keep-rects-in-world))
