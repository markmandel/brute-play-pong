(ns ^{:doc "The rendering system. Keeping it simple for pong"}
    brute-play-pong.rendering
    (:use [play-clj.math]
          [play-clj.core])
    (:require [brute.entity :as e])
    (:import [com.badlogic.gdx.graphics.glutils ShapeRenderer ShapeRenderer$ShapeType]
             [com.badlogic.gdx.graphics.g2d SpriteBatch BitmapFont]
             [brute_play_pong.component Rectangle Score PlayerScore]))

(defn start
    "Start this system"
    [system]
    (as-> {:shape-renderer (ShapeRenderer.)
           :sprite-batch   (SpriteBatch.)
           :font           (BitmapFont.)} renderer
          (assoc system :renderer renderer)))

(defn- render-rectangles
    "Render all the rectangles"
    [system]
    (let [shape-renderer (:shape-renderer (:renderer system))]
        (.begin shape-renderer ShapeRenderer$ShapeType/Filled)
        (doseq [entity (e/get-all-entities-with-component system Rectangle)]
            (let [rect (e/get-component system entity Rectangle)
                  geom (:rect rect)]
                (doto shape-renderer
                    (.setColor (:colour rect))
                    (.rect (rectangle! geom :get-x)
                           (rectangle! geom :get-y)
                           (rectangle! geom :get-width)
                           (rectangle! geom :get-height)))))
        (.end shape-renderer)))

(defn- render-scores
    "Render the scores"
    [system]
    (let [renderer (:renderer system)
          sprite-batch (:sprite-batch renderer)
          font (:font renderer)]
        (.begin sprite-batch)
        (doseq [entity (e/get-all-entities-with-component system Score)]
            (let [score (e/get-component system entity Score)
                  is-player (e/get-component system entity PlayerScore)
                  screen-width (graphics! :get-width)
                  screen-height (graphics! :get-height)
                  str-score (str @(:score score))]
                (if is-player
                    (.draw font sprite-batch str-score 15 30)
                    (.draw font sprite-batch str-score (- screen-width 30) (- screen-height 15)))))
        (.end sprite-batch)))

(defn process-one-game-tick
    "Render all the things"
    [system _]
    (render-scores system)
    (render-rectangles system)
    system)


