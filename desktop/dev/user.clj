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

(defn set-refresh-src!
    "Just set source as the refresh dirs"
    []
    (set-refresh-dirs "./src" "./dev"))

(defn set-refresh-all!
    "Set src, dev and test as the directories"
    []
    (set-refresh-dirs "./src" "./dev" "./test"))

(defn start
    []
    (l/-main)
    :ready)

(defn reset-screen
    []
    (p/on-gl (p/set-screen! core/brute-play-pong core/main-screen))
    :ok)

(defn reset
    "Stops the system, optionally reloads modified source files, and restarts it."
    []
    (refresh :after 'user/reset-screen))

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