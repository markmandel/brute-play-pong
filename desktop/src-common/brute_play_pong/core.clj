(ns brute-play-pong.core
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute-play-pong.component :as c]
              [brute-play-pong.rendering :as r]
              [brute-play-pong.input :as i]
              [brute-play-pong.ai :as ai]
              [brute-play-pong.physics :as p]
              [brute-play-pong.ball :as b]
              [brute.entity :as e]
              [brute.system :as s]
              [clojure.math.numeric-tower :as m])
    (:import [com.badlogic.gdx.graphics Color]
             [brute_play_pong.component Rectangle Velocity]))

(defn- create-entities
    "Create all the initial entities with their components"
    []
    (let [player (e/create-entity!)
          cpu (e/create-entity!)
          screen-width (graphics! :get-width)
          screen-height (graphics! :get-height)
          center-x (-> screen-width (/ 2) (m/round))
          center-y (-> screen-height (/ 2) (m/round))
          paddle-width 100
          paddle-center-x (- center-x (/ paddle-width 2))
          paddle-padding 40]

        ;; Paddles
        (e/add-component! player (c/->Paddle))
        (e/add-component! player (c/->PlayerPaddle))
        (e/add-component! player (c/->Rectangle (rectangle paddle-center-x paddle-padding paddle-width 20) Color/WHITE))
        (println "Player is positioned at: " (e/get-component player Rectangle))

        (e/add-component! cpu (c/->Paddle))
        (e/add-component! cpu (c/->CPUPaddle))
        (e/add-component! cpu (c/->Rectangle (rectangle paddle-center-x (- screen-height (+ paddle-padding 20)) paddle-width 20) Color/WHITE))
        (println "CPU is positioned at: " (e/get-component cpu Rectangle))

        ;; Ball
        (b/create-ball)

        ))

(defn- create-systems
    "register all the system functions"
    []
    (r/start!)
    (s/add-system-fn i/process-one-game-tick)
    (s/add-system-fn ai/process-one-game-tick)
    (s/add-system-fn p/process-one-game-tick)
    (s/add-system-fn r/process-one-game-tick))

(defscreen main-screen
           :on-show
           (fn [screen entities]
               (println "Started v1")
               (create-entities)
               (create-systems)
               (update! screen :renderer (stage) :camera (orthographic)))
           :on-render
           (fn [screen entities]
               (clear!)
               (s/process-one-game-tick (graphics! :get-delta-time))
               (render! screen)))

(defgame brute-play-pong
         :on-create
         (fn [this]
             (set-screen! this main-screen)))