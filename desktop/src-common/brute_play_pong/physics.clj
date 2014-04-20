(ns ^{:doc "Physics system"}
    brute-play-pong.physics
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute.entity :as e]
              [clojure.math.numeric-tower :as m])
    (:import [brute_play_pong.component Rectangle Ball Velocity Paddle]))

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

(defn- keep-rects-in-world!
    "Make sure all rectangles stay inside the walls"
    [system]
    (doseq [entity (e/get-all-entities-with-component system Rectangle)]
        (let [rect (e/get-component system entity Rectangle)
              geom (:rect rect)]
            (cond
                (touching-left-wall geom) (rectangle! geom :set-x 0)
                (touching-right-wall geom) (rectangle! geom :set-x (- (graphics! :get-width) (rectangle! geom :get-width)))))))

(defn- touching-paddle
    "Are we touching a paddle? If so, return it."
    [system ball-rect]
    (some (fn [paddle]
              (when (rectangle! (:rect (e/get-component system paddle Rectangle)) :overlaps ball-rect)
                  paddle))
          (e/get-all-entities-with-component system Paddle)))

(defn- pos-multiplier
    "If n1 is less than n2, return 1, otherwise return -1"
    [n1 n2]
    (if (< n1 n2) 1 -1))

(defn- bounce-ball!
    "Bounce the ball. Returns :top-bottom if hits the top or bottom :side if hits the side."
    [p-rect b-rect vel]

    ; http://gamedev.stackexchange.com/questions/25642/breakout-collision-using-2d-rectangles

    ; It is quite intuitive that if the ball collides with the top or bottom of the block,
    ; this intersection's width will be greater than its height.

    ; Conversely, if the ball collides with the left or right side of the block, this
    ; intersection will have a greater height than width.

    (let [intersec (rectangle*)
          _ (intersector! :intersect-rectangles p-rect b-rect intersec)
          width (rectangle! intersec :get-width)
          height (rectangle! intersec :get-height)]

        ; need to change the velocity, and also move it outside of the paddle, as it
        ; is overlapping, which can cause all sorts of weirdness.

        (if (>= width height)
            ; mutable data
            (do (set! (.y vel) (* -1 (vector-2! vel :y)))
                (let [y (rectangle! b-rect :get-y)]
                    (rectangle! b-rect :set-y (-> (* (pos-multiplier (rectangle! p-rect :get-y) y) (rectangle! intersec :get-height)) (+ y))))
                :top-bottom)
            (do (set! (.x vel) (* -1 (vector-2! vel :x)))
                (let [x (rectangle! b-rect :get-x)]
                    (rectangle! b-rect :set-x (-> (* (pos-multiplier (rectangle! p-rect :get-x) x) (rectangle! intersec :get-width)) (+ x))))
                :side))))

(defn- ball-collision!
    "Bounces the ball off the rectangle, accounting for the angle of hit"
    [system ball-rect paddle vel]
    (let [p-rect (:rect (e/get-component system paddle Rectangle))
          ;; positive is right, negative is left
          center-diff (vector-2! (rectangle! ball-rect :get-center (vector-2*)) :sub (rectangle! p-rect :get-center (vector-2*)))
          abs-x (m/abs (vector-2! center-diff :x))
          rotation (* -1 (/ (vector-2! center-diff :x) 50) max-rotate-on-bounce)
          bounce-side (bounce-ball! p-rect ball-rect vel)]

        ;; rotate the velocity depending on where it is, and only for top/bottom collisions
        (when (= bounce-side :top-bottom)
            (vector-2! vel :rotate rotation))
        ;when on the out edge, speed the ball up a bit. When near the center, slow it down a touch.
        (cond (> abs-x 35) (vector-2! vel :scl (float 1.2))
              (< abs-x 5) (vector-2! vel :scl (float 0.95)))))

(defn- move-ball!
    "Move the ball based on it's velocity, and if it bounces into anything"
    [system delta]
    (doseq [entity (e/get-all-entities-with-component system Ball)]
        (let [vel (:vec (e/get-component system entity Velocity))
              rect (:rect (e/get-component system entity Rectangle))
              rect-x (rectangle! rect :get-x)
              rect-y (rectangle! rect :get-y)]

            ;; velocity movement
            (rectangle! rect :set-x (+ rect-x (* (.x vel) delta)))
            (rectangle! rect :set-y (+ rect-y (* (.y vel) delta)))

            ;; boucing off walls
            (when (or (touching-left-wall rect) (touching-right-wall rect))
                (set! (.x vel) (* -1 (.x vel))))

            ;;bouncing off paddles
            (when-let [paddle (touching-paddle system rect)]
                (ball-collision! system rect paddle vel)))))

(defn process-one-game-tick
    "Physics, process one game tick"
    [system delta]
    (move-ball! system delta)
    (keep-rects-in-world! system)
    system)
