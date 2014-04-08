(ns ^{:doc "All the components for out pong game"}
    brute-play-pong.component
    (:import (com.badlogic.gdx.graphics Color)))

;; normally i'd have a position, but everything is a Rectangle in Pong.
(defrecord Rectangle [^com.badlogic.gdx.math.Rectangle rect ^Color colour])
(defrecord Paddle [])
(defrecord Ball [])
(defrecord PlayerPaddle [])
(defrecord CPUPaddle [])