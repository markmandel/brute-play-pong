(defproject brute-play-pong "0.0.1-SNAPSHOT"
  :description "FIXME: write description"

  :dependencies [[com.badlogicgames.gdx/gdx "1.9.3"]
                 [com.badlogicgames.gdx/gdx-backend-lwjgl "1.9.3"]
                 [com.badlogicgames.gdx/gdx-box2d "1.9.3"]
                 [com.badlogicgames.gdx/gdx-box2d-platform "1.9.3"
                  :classifier "natives-desktop"]
                 [com.badlogicgames.gdx/gdx-bullet "1.9.3"]
                 [com.badlogicgames.gdx/gdx-bullet-platform "1.9.3"
                  :classifier "natives-desktop"]
                 [com.badlogicgames.gdx/gdx-platform "1.9.3"
                  :classifier "natives-desktop"]
                 [org.clojure/clojure "1.8.0"]
                 [play-clj "1.1.0"]
                 [brute "0.4.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]]

  :source-paths ["src" "src-common"]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [brute-play-pong.core.desktop-launcher]
  :main brute-play-pong.core.desktop-launcher
  :plugins [[lein-midje "3.1.1"]
            [lein-ancient "0.6.10"]
            [codox "0.6.7"]]
  :profiles {:dev   {:dependencies [[midje "1.8.3"]
                                    [org.clojure/tools.namespace "0.2.10"]
                                    [org.clojure/tools.trace "0.7.9"]
                                    [org.clojars.gjahad/debug-repl "0.3.3"]]
                     :source-paths ["dev"]
                     :repl-options {:init-ns user}
                     :codox        {:output-dir "doc/codox"}}})