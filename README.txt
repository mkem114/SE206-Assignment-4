README

AUTHOR: mkem114

NOTES TO MARKER:
- Only on the unity desktop manager in ubuntu 14.04 does the text randomly change size. Use gnome or any other desktop manager to resolve

Running the application
- Please launch the command "./RUN.sh" in this directory.
- This application is designed to run with Java 1.8, and assumes that the JDK is installed at /usr/lib/jvm/jdk8. (Path to Java is /usr/lib/jvm/jdk8/bin/java)
- The above command has been tested on the images in UG4 (Linux).

Playing
-Any words with spaces in them are not loaded like "New Zealand" to avoid confusion and frustration about the number of spaces
-Replace the background music called music.wav if you wish to listen to something else or delete it to listen to nothing
-All words are turned into all lower case, this is about spelling not grammer
-Any words that are homophones (sound the same but spelt differently e.g I and eye) are excluded to avoid exclusion, if you would like to override a particular word just delete it from the homophones.txt file. Or add the word in to exclude it
-Any words that consist of more than just letters are excluded because this is about spelling not grammer

Dependencies
- This application depends on "nzcer-wordlist.txt" as the wordlist and "hey.mp4" as the video file in the same directory as the RUN.sh executable.
- Java 1.8
- Additional files may be generated during the game.

Save Game
- We have designed the app to save your game history (statistics) until the reset button has been pressed. This was a design decision as we want the user to be able to keep their stats even if they close the application (just like Assignment 2), and as it will be a crucial feature of the final project. In addition, we have local statistics stored per quiz (i.e. a playing level, 10 words). Early in the development process, we decided that a “session” was until the user pressed the reset button in the options menu (like A2).
- The saved game is stored in the spelling.savegame file that is in the same directory. If the user wishes to reset the game without opening the game, they can simply delete spelling.savegame. (As this is a prototype we opted to not make this a hidden file for testability).

Music is sourced from: (open source)
http://xplsv.com/prods/music/sole/mp3/s0le%20-%20City%20Ambient%20EP%20-%20intro.mp3
Homophones sourced from:
http://people.sc.fsu.edu/~jburkardt/fun/wordplay/multinyms.html
Icon pack is sourced from: (open source)
https://useiconic.com/open

Git Repo:
https://github.com/mkem114/SE206-Assignment-4