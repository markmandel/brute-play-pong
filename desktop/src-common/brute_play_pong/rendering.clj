(ns ^{:doc "The rendering system. Keeping it simple for pong"}
    brute-play-pong.rendering
    (:use [play-clj.math]
          [play-clj.core])
    (:require [brute.entity :as e])
    (:import [com.badlogic.gdx.graphics.glutils ShapeRenderer ShapeRenderer$ShapeType]
             [com.badlogic.gdx.graphics.g2d SpriteBatch BitmapFont]
             [brute_play_pong.component Rectangle Score PlayerScore]))

(defonce ^ShapeRenderer shape-renderer nil)
(defonce ^SpriteBatch sprite-batch nil)
(defonce ^BitmapFont font nil)

(defn start!
    "Start this system"
    []
    (alter-var-root #'shape-renderer (constantly (ShapeRenderer.)))
    (alter-var-root #'sprite-batch (constantly (SpriteBatch.)))
    (alter-var-root #'font (constantly (BitmapFont.))))

(defn- render-rectangles
    "Render all the rectangles"
    []
    (.begin shape-renderer ShapeRenderer$ShapeType/Filled)
    (doseq [entity (e/get-all-entities-with-component Rectangle)]
        (let [rect (e/get-component entity Rectangle)
              colour (:colour rect)
              geom (:rect rect)]
            (doto shape-renderer
                (.setColor (:colour rect))
                (.rect (rectangle! geom :get-x)
                       (rectangle! geom :get-y)
                       (rectangle! geom :get-width)
                       (rectangle! geom :get-height)))))
    (.end shape-renderer))

(defn- render-scores
    "Render the scores"
    []
    (.begin sprite-batch)
    (doseq [entity (e/get-all-entities-with-component Score)]
        (let [score (e/get-component entity Score)
              is-player (e/get-component entity PlayerScore)
              screen-width (graphics! :get-width)
              screen-height (graphics! :get-height)
              str-score (str @(:score score))]
            (if is-player
                (.draw font sprite-batch str-score 15 30)
                (.draw font sprite-batch str-score (- screen-width 30) (- screen-height 15)))))
    (.end sprite-batch))

(defn process-one-game-tick
    "Render all the things"
    [delta]
    (render-scores)
    (render-rectangles))


