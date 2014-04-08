(ns user
    "Tools for interactive development with the REPL. This file should
    not be included in a production build of the application."
    (:use [midje.repl :only (autotest load-facts)]
          [clojure.pprint :only (pprint)]
          [clojure.repl]
          [clojure.tools.namespace.repl :only (refresh refresh-all set-refresh-dirs)]
          [clojure.tools.trace])
    (:require
        [brute-play-pong.core :as core]
        [play-clj.core :as p]
        [brute-play-pong.core.desktop-launcher :as l]))

(def game nil)

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

(defn restart-game!
    []
    (when game
        (.stop game))
    (start!))

(defn restart-screen!
    []
    (when game
        (p/on-gl (p/set-screen! core/brute-play-pong core/main-screen))
        :ok))

(defn reset-game!
    "Stops the system, optionally reloads modified source files, and restarts it."
    []
    (refresh :after 'user/restart-game!))

(defn reset-screen!
    "Stops the system, optionally reloads modified source files, and restarts it."
    []
    (refresh :after 'user/restart-screen!))

;; helper functions
(defn autotest-focus
    "Only autotest on the focused item"
    []
    (autotest :stop)
    (autotest :filter :focus))

(defn load-facts-focus
    "Only load tests under focus"
    []
    (load-facts :filter :focus))