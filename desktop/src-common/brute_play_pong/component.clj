(ns ^{:doc "All the components for out pong game"}
    brute-play-pong.component
    (:import (com.badlogic.gdx.graphics Color)
             (com.badlogic.gdx.math Vector2)))

;; normally i'd have a position, but everything is a Rectangle in Pong.
(defrecord Rectangle [^com.badlogic.gdx.math.Rectangle rect ^Color colour])
(defrecord Paddle [])
(defrecord Ball [])
(defrecord PlayerPaddle [])
(defrecord CPUPaddle [])
(defrecord Velocity [^Vector2 vec])
(defrecord PlayerScore [])
(defrecord CPUScore [])
(defrecord Score [score])