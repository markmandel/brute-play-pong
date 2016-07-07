(ns user
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:use [clojure.pprint :only (pprint)]
        [clojure.repl]
        [clojure.tools.namespace.repl :only (refresh refresh-all set-refresh-dirs)]
        [clojure.tools.trace])
  (:require
    [brute-play-pong.core :as core]
    [play-clj.core :as p]
    [brute-play-pong.core.desktop-launcher :as l]
    [brute.entity :as e]
    [brute.system :as s]))

(defonce game nil)

(defn set-refresh-src!
  "Just set source as the refresh dirs"
  []
  (set-refresh-dirs "./src" "./dev"))

(defn set-refresh-all!
  "Set src, dev and test as the directories"
  []
  (set-refresh-dirs "./src" "./dev" "./test"))

(defn start!
  []
  (let [game (l/run-game)]
    (alter-var-root #'game (constantly game)))
  :ready)

(defn restart-screen!
  []
  (when game
    (p/on-gl (p/set-screen! core/brute-play-pong core/main-screen))
    :ok))

(defn restart-game!
  []
  (restart-screen!))