package brute_play_pong.core;

import clojure.lang.RT;
import clojure.lang.Symbol;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.Game;

public class AndroidLauncher extends AndroidApplication {
	public void onCreate (android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
          RT.var("clojure.core", "require").invoke(Symbol.intern("brute-play-pong.core"));
		try {
			Game game = (Game) RT.var("brute-play-pong.core", "brute-play-pong").deref();
			initialize(game, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
