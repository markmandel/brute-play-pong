(defproject brute-play-pong "0.0.1-SNAPSHOT"
    :description "A Pong Clone written with Brute Entity System and Play-CLJ"
    :dependencies [[com.badlogicgames.gdx/gdx "0.9.9"]
                   [com.badlogicgames.gdx/gdx-backend-lwjgl "0.9.9"]
                   [com.badlogicgames.gdx/gdx-platform "0.9.9"
                    :classifier "natives-desktop"]
                   [org.clojure/clojure "1.6.0"]
                   [play-clj "0.2.4"]
                   [brute "0.1.0"]
                   [org.clojure/math.numeric-tower "0.0.4"]]
    :source-paths ["src" "src-common"]
    :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
    :aot [brute-play-pong.core.desktop-launcher]
    :main brute-play-pong.core.desktop-launcher
    :plugins [[lein-midje "3.1.1"]
              [lein-ancient "0.5.5"]
              [codox "0.6.7"]]
    :profiles {:dev {:dependencies [[midje "1.6.3"]
                                    [org.clojure/tools.namespace "0.2.4"]
                                    [org.clojure/tools.trace "0.7.8"]
                                    [org.clojars.gjahad/debug-repl "0.3.3"]]
                     :source-paths ["dev"]
                     :repl-options {:init-ns user}
                     :codox        {:output-dir "doc/codox"}}})
