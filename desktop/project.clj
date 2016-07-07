(defproject brute-play-pong "0.0.1-SNAPSHOT"
  :description "A Pong clone written with the Brute Entity Component System library, and Play-CLJ"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
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
  :aot [brute-play-pong.core.desktop-launcher]
  :main brute-play-pong.core.desktop-launcher
  :plugins [[lein-ancient "0.6.10"]
            [codox "0.6.7"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]
                                  [org.clojure/tools.trace "0.7.9"]]
                   :source-paths ["dev"]
                   :repl-options {:init-ns user}
                   :codox        {:output-dir "doc/codox"}}})