(ns ^{:doc "The rendering system. Keeping it simple for pong"}
    brute-play-pong.render-system
    (:use [play-clj.math])
    (:require [brute.entity :as e])
    (:import [com.badlogic.gdx.graphics.glutils ShapeRenderer ShapeRenderer$ShapeType]
             [brute_play_pong.component Rectangle]))

(defonce shape-renderer nil)

(defn start!
    "Start this system"
    []
    (alter-var-root #'shape-renderer (constantly (ShapeRenderer.))))

(defn render-rectangles
    "Render all the rectangles"
    []
    (.begin shape-renderer ShapeRenderer$ShapeType/Filled)
    (doseq [entity (e/get-all-entities-with-component Rectangle)]
        (let [rect (e/get-component entity Rectangle)
              colour (:colour rect)
              geom (:rect rect)]
            (doto shape-renderer
                (.setColor (:colour rect))
                (.rect (rectangle! geom :get-x) (rectangle! geom :get-y) (rectangle! geom :get-width) (rectangle! geom :get-height))))
        )
    (.end shape-renderer))

(defn process-one-game-tick
    "Render all the things"
    [delta]
    (render-rectangles))


