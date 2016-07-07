## Introduction

A Pong clone written with the [brute](https://www.github.com/markmandel/brute) Entity Component System library, and [play-clj](https://github.com/oakes/play-clj)

Currently only implements the desktop version, but have kept the stubs for iOS and android in case
I want to implement those as well at some stage.

![Screenshot](screen.png)

## Contents

* `android/src` Android-specific code
* `desktop/resources` Images, audio, and other files
* `desktop/src` Desktop-specific code
* `desktop/src-common` Cross-platform game code
* `ios/src` iOS-specific code

## Running

```
cd desktop
lein run
```

## Building

All projects can be built using [Nightcode](https://nightcode.info/), or on the command line using [Leiningen](https://github.com/technomancy/leiningen) with the [lein-droid](https://github.com/clojure-android/lein-droid) and [lein-fruit](https://github.com/oakes/lein-fruit) plugins.

## License

Copyright © 2016 Mark Mandel, Google Inc.

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.