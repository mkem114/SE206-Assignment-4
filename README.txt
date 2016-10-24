VOXSPELL README

AUTHOR: mkem114

NOTES TO MARKER:
- Only on the unity desktop manager in ubuntu 14.04 does the text randomly change size. Use gnome or any other desktop manager to resolve the issue, the issue is not with my program nor Java FX.
- On a similar note the application icon appears in other desktop managers but not unity
- The user manual would have many more screenshots of things like clicked buttons and and open comboboxes but this is very hard on UG4 computers (I don't know of a way because they undo themselves).

Running the application:
- You may need to run the command "chmod 777 RUN.sh" in this folder
- Please launch the command "./RUN.sh" in this folder (the one VOXSpell.jar is in).
- VOXSpell needs to run with Java 1.8, and is installed at /usr/lib/jvm/jdk8. (Path to Java binary is /usr/lib/jvm/jdk8/bin/java).
- VOXSpell needs to be run in UG4 on linux (press "l" when starting the computer).

Playing:
- Any words with spaces in them are not loaded like "New Zealand" to avoid confusion and frustration about the number of spaces.
- All words are turned into all lower case, this game is about spelling not grammar.
- Replace the background music called music.wav if you wish to listen to something else.
- Any words that are homophones (sound the same but spelt differently e.g I and eye) are excluded to avoid exclusion, if you would like to override a particular word just delete it from the homophones.txt file. Or add the word in to exclude it.
- Any words that consist of more than just letters are excluded because this is about spelling not grammar.

Dependencies:
- VOXSpell/music/music.wav ~ Music in main menu.
- VOXSpell/homophones.txt ~ Words that sound the same and not quizzed.
- VOXSpell/.reward.mp4 ~ Video reward.
- VOXSpell/.voice.scm ~ Can be deleted when game is closed. This allows the game to speak.
- VOXSpell/.spelling.savegame ~ Can be deleted when game is closed. This is the in game progress.
- Java 1.8 ~ 1.8_91 is what VOXSpell is tested on.
- Festival ~ With Auckland and Rab voices installed to be used

Save-game:
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