(ns brute-play-pong.core.desktop-launcher
    (:use [brute-play-pong.core])
    (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication LwjglApplicationConfiguration]
             [org.lwjgl.input Keyboard])
    (:gen-class))

(defn -main
    []
    (let [config (LwjglApplicationConfiguration.)]
        (doto config
            (-> .width (set! 600))
            (-> .height (set! 800))
            (-> .title (set! "Brute Pong"))
            (-> .resizable (set! false)))
        (LwjglApplication. brute-play-pong config))
    (Keyboard/enableRepeatEvents true))
