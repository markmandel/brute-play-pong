(ns ^{:doc "Physics system"}
    brute-play-pong.physics
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e]
              [clojure.math.numeric-tower :as m])
    (:import [brute_play_pong.component Rectangle Ball Velocity Paddle]
             [com.badlogic.gdx.math Vector2]))

(def max-rotate-on-bounce 55)

(defn- ^Boolean touching-left-wall
    [^com.badlogic.gdx.math.Rectangle geom]
    (let [geom-x (rectangle! geom :get-x)]
        (< geom-x 0)))

(defn- ^Boolean touching-right-wall
    [^com.badlogic.gdx.math.Rectangle geom]
    (let [geom-x (rectangle! geom :get-x)
          geom-width (rectangle! geom :get-width)
          screen-width (graphics! :get-width)]
        (> (+ geom-x geom-width) screen-width)))

(defn- keep-rects-in-world
    []
    (doseq [entity (e/get-all-entities-with-component Rectangle)]
        (let [rect (e/get-component entity Rectangle)
              colour (:colour rect)
              geom (:rect rect)]
            (cond
                (touching-left-wall geom) (rectangle! geom :set-x 0)
                (touching-right-wall geom) (rectangle! geom :set-x (- (graphics! :get-width) (rectangle! geom :get-width)))))))

(defn- touching-paddle
    "Are we touching a paddle? If so, return it."
    [ball-rect]
    (some (fn [paddle]
              (when (rectangle! (:rect (e/get-component paddle Rectangle)) :overlaps ball-rect)
                  paddle))
          (e/get-all-entities-with-component Paddle)))

;; TODO fix glitch in which the ball gets stuck in the paddle

(defn- bounce-ball
    "Bounces the ball off the rectangle, accounting for the angle of hit"
    [ball-rect paddle vel]
    (let [p-rect (:rect (e/get-component paddle Rectangle))
          center-diff (vector-2! (rectangle! ball-rect :get-center (Vector2.)) :sub (rectangle! p-rect :get-center (Vector2.)))
          abs-x (m/abs (.x center-diff))
          rotation (* -1 (/ (.x center-diff) 50) max-rotate-on-bounce)]
        ;; positive is right, negative is left
        (println "diff: " center-diff, "abs:" abs-x)
        ;;mutable data
        (set! (.y vel) (* -1 (.y vel)))
        ;; rotate the velocity depending on where it is
        (vector-2! vel :rotate rotation)
        ;when on the out edge, speed the ball up a bit
        (cond (> abs-x 35) (do (println "FASTER!!!")
                               (vector-2! vel :scl (float 1.1)))
              (< abs-x 5) (do (println "SLOWER!")
                              (vector-2! vel :scl (float 0.95)))
              )
        )
    )

(defn- move-ball
    "Move the ball based on it's velocity, and if it bounces into anything"
    [delta]
    (doseq [entity (e/get-all-entities-with-component Ball)]
        (let [vel (:vec (e/get-component entity Velocity))
              rect (:rect (e/get-component entity Rectangle))
              rect-x (rectangle! rect :get-x)
              rect-y (rectangle! rect :get-y)]

            ;; velocity movement
            (rectangle! rect :set-x (+ rect-x (* (.x vel) delta)))
            (rectangle! rect :set-y (+ rect-y (* (.y vel) delta)))

            ;; boucing off walls
            (when (or (touching-left-wall rect) (touching-right-wall rect))
                (set! (.x vel) (* -1 (.x vel))))

            ;;bouncing off paddles
            (when-let [paddle (touching-paddle rect)]
                (bounce-ball rect paddle vel)))))

(defn process-one-game-tick
    "Physics, process one game tick"
    [delta]
    (move-ball delta)
    (keep-rects-in-world))
