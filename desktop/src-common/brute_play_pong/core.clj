(ns brute-play-pong.core
    (:use [play-clj.core]
          [play-clj.math])
    (:require [brute-play-pong.component :as c]
              [brute-play-pong.rendering :as r]
              [brute-play-pong.input :as i]
              [brute-play-pong.ai :as ai]
              [brute-play-pong.physics :as p]
              [brute-play-pong.ball :as b]
              [brute-play-pong.scoring :as sc]
              [brute.entity :as e]
              [brute.system :as s]
              [clojure.math.numeric-tower :as m])
    (:import [brute_play_pong.component Rectangle Velocity]))

(def sys (atom 0))

(defn- start
    "Create all the initial entities with their components"
    [system]
    (let [player (e/create-entity)
          cpu (e/create-entity)
          screen-width (graphics! :get-width)
          screen-height (graphics! :get-height)
          center-x (-> screen-width (/ 2) (m/round))
          paddle-width 100
          paddle-center-x (- center-x (/ paddle-width 2))
          paddle-padding 40
          player-score (e/create-entity)
          cpu-score (e/create-entity)]

        (-> system
            (e/add-entity player)
            (e/add-entity cpu)
            (e/add-entity player-score)
            (e/add-entity cpu-score)

            ;; Paddles
            (e/add-component player (c/->Paddle))
            (e/add-component player (c/->PlayerPaddle))
            (e/add-component player (c/->Rectangle (rectangle paddle-center-x paddle-padding paddle-width 20) (color :white)))

            (e/add-component cpu (c/->Paddle))
            (e/add-component cpu (c/->CPUPaddle))
            (e/add-component cpu (c/->Rectangle (rectangle paddle-center-x (- screen-height (+ paddle-padding 20)) paddle-width 20) (color :white)))

            ;; Ball
            (b/create-ball)

            ;; Scores
            (e/add-component player-score (c/->Score (atom 0)))
            (e/add-component player-score (c/->PlayerScore))
            (e/add-component cpu-score (c/->Score (atom 0)))
            (e/add-component cpu-score (c/->CPUScore)))))

(defn- create-systems
    "register all the system functions"
    [system]
    (-> system
        (r/start)
        (s/add-system-fn sc/process-one-game-tick)
        (s/add-system-fn i/process-one-game-tick)
        (s/add-system-fn ai/process-one-game-tick)
        (s/add-system-fn p/process-one-game-tick)
        (s/add-system-fn r/process-one-game-tick)))

(defscreen main-screen
           :on-show
           (fn [screen entities]
               (println "Started")
               (-> (e/create-system)
                   (start)
                   (create-systems)
                   (as-> s (reset! sys s)))
               (update! screen :renderer (stage) :camera (orthographic))
               ;; return nil, as we're not using the entity system
               nil)
           :on-render
           (fn [screen entities]
               (clear!)
               (reset! sys (s/process-one-game-tick @sys (graphics! :get-delta-time)))
               (render! screen)
               ;; return nil, as we're not using the entity system
               nil))

(defgame brute-play-pong
         :on-create
         (fn [this]
             (set-screen! this main-screen)))