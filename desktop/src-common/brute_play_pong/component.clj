(ns ^{:doc "All the components for out pong game"}
    brute-play-pong.component)

;; normally i'd have a position, but everything is a Rectangle in Pong.
(defrecord Rectangle [rect colour])
(defrecord Paddle [])
(defrecord Ball [])
(defrecord PlayerPaddle [])
(defrecord CPUPaddle [])