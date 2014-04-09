(ns ^{:doc "Physics system"}
    brute-play-pong.physics
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e])
    (:import [brute_play_pong.component Rectangle Ball Velocity]))

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
                (rectangle! geom :set-x (- screen-width geom-width))))))

(defn- move-ball
    [delta]
    (doseq [entity (e/get-all-entities-with-component Ball)]
        (let [vel (:vec (e/get-component entity Velocity))
              rect (:rect (e/get-component entity Rectangle))
              rect-x (rectangle! rect :get-x)
              rect-y (rectangle! rect :get-y)]
            (rectangle! rect :set-x (+ rect-x (* (.x vel) delta)))
            (rectangle! rect :set-y (+ rect-y (* (.y vel) delta)))
            )
        ))

(defn process-one-game-tick
    "Physics, process one game tick"
    [delta]
    (move-ball delta)
    (keep-rects-in-world))
