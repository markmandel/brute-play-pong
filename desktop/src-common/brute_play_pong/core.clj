(ns brute-play-pong.core
    (:use [play-clj.core]
          [play-clj.ui])
    (:import [com.badlogic.gdx.graphics.glutils ShapeRenderer ShapeRenderer$ShapeType]
             [com.badlogic.gdx.graphics Color]))

(defn do-render [screen]
    (clear!)
    (let [shapes (ShapeRenderer.)]
        (doto shapes
            (.begin ShapeRenderer$ShapeType/Filled)
            (.setColor Color/WHITE)
            (.rect 10 10 100 100)
            (.end)))
    (render! screen))

(defscreen main-screen
           :on-show
           (fn [screen entities]
               (println "Showing! YYY")
               (update! screen :renderer (stage) :camera (orthographic)))
           :on-render
           (fn [screen entities]
               (do-render screen)))

(defgame brute-play-pong
         :on-create
         (fn [this]
             (set-screen! this main-screen)))