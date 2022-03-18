# ABOUT THE PROJECT
This is a continuation of the previous two projects we have been working on. In the first part we 
created a dungeon and a player that moves through the dungeon. In the second part we created an 
interactive story driven exploration game where you(the player) take the role of the protagonist 
exploring your way through the dungeon and are sometimes encountered by monsters called Otyughs 
that smell. You have the ability to shoot arrows that can be picked up inside the dungeon.
The dungeon also has treasure that you can pick up along the way.

In this third part, we add a thief to the mix, who also entered the dungeon for the treasure and 
steals it from you whenever you encounter him. Then, we transfer all this back-end to
a user interface that is easy to interact with and play. The objective of the game remains the same,
which is, You should successfully navigate through the dungeon and get out of it alive.

# LIST OF FEATURES
Adding to the features from the previous two projects, we now have,
1. The dungeon will also feature a thief that tries to steal your bag of treasure.
2. A brand new, easy-to-use user interface that will give all the information you need to play and
enjoy what the game offers.
   1. The cells that are explored until now are displayed to the screen.
   2. You can now see where treasure and arrows are.
   3. You can smell the stench and take action if you think a monster is nearby.
   4. The outcome of your arrow shoot is displayed in the details panel located conveniently to the
   right of your dungeon panel so that you won't miss any small detail that helps you.
   5. The controls to operate the game are located in the bottom right panel that clearly shows how
   to play the game.
   6. You now have the option to reset the game or play a completely new game which you can choose 
   from the Options Menu.
   7. You can move through the dungeon even using your mouse.
   8. There is a scroll option that will help you scroll through the information if it does not fit
   to the window.

# HOW TO RUN
1. The JAR file can be run by specifying the following command:
    * Open the terminal/command line and navigate to the folder that contains jar on your computer.
    * The insert the below command to run the project.
    * The jar can be run from anywhere. It should as far as I have tested should work without any 
   issues.
### Command to run for Console Mode
    java -jar Project-5-Dungeon-View.jar <width> <height> <wrapping> <interconnectivity> <treasure_percentage> <number_of_otyughs>
### Command to run for UI Mode
    java -jar Project-5-Dungeon-View.jar

# HOW TO USE THE PROGRAM
The game can be run in both console mode and UI mode. The mode will depend on whether the user
entered the CLI arguments or not. The console based game will be the same as the previous one with
added features. The User Interface game consists of all features mentioned above.

The player can move, shoot, or pickup the treasure/arrows in the dungeon.

### Console Mode:
    If the player selects move, the direction in which to go has to be specified.
    If the player selects shoot, the direction in which to shoot and the distance has to be specified.
    The user will be asked if he wants to pickup the treasure [1-yes / 0-no].

    End location reached - GAME OVER! You escaped out of the dungeon.
    Eaten by an Otyugh - GAME OVER! Eaten by an Otyugh.

### User Interface Mode:
    Arrows keys to move the player through the dungeon.
    'E' to pickup arrows and/or treasure.
    'S' + Arrow Keys + Num Keys to shoot an arrow.

    End location reached - GAME OVER! You escaped out of the dungeon :)
    Eaten by an Otyugh - GAME OVER! Eaten by an Otyugh :(

# DESCRIPTION OF EXAMPLES
I am submitting 3 example runs. What each of the runs demonstrate is given below.
### RUN 1
    /res/ExampleRun_Console_1.txt -  
    
    1. A non-wrapping dungeon,
    2. Shows the player navigating through the dungeon,
    3. Shows the player picking up arrows, and
    4. Shows the player being eaten by a Otyugh.
### RUN 2
    /res/ExampleRun_Console_2.txt -  
    
    1. A wrapping dungeon,
    2. Shows the player navigating through the dungeon,
    3. Shows the player picking up treasure,
    4. Shows the player picking up arrows,
    5. Shows the player killing an Otyugh, and
    6. Shows the player winning the game by reaching the end.
### RUN 3
    /res/ExampleRuns_View.pdf
    
    1. Starting the game
    2. Picking up treasure and/or arrows
    3. Shooting an Otyugh
    4. Encountered Thief and bog getting empty
    5. Eaten by an Otyugh
    6. Escaped out of the dungeon
    7. Exposing all Dungeon Settings
    8. Reset Game & Quit Game options in Menu
    9. New Game option in Menu

# DESIGN/MODEL CHANGES
1. Added a new interface and class for Thief.
2. Added a new ReadOnlyDungeonModel interface that has all the getter methods necessary for the view
to display information to the screen.
3. Added a new package called view which contains all the classes that together make the UI.
   1. Dungeon Panel class to display the game to the screen.
   2. Details Panel class to display the up-to-date details of game proceedings.
   3. Options Menu class which contains New Game, Reset Game, and Quit Game options.
   4. Instructions Panel class to display the instructions on how to play the game.

# ASSUMPTIONS
1. There will only be one thief, and he is in a fixed location.
2. Player cannot fight with the thief nor can he kill him.

# LIMITATIONS
1. User interface will not adjust the display depending on the size of the game window. 

# CITATIONS
    Resizing the Image - https://stackoverflow.com/questions/5895829/resizing-image-in-java
    Side-by-side Layout - https://stackoverflow.com/questions/33318644/how-do-i-add-jpanel-jsplitpane-components-side-by-side-in-a-frame
    Box Layout - https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
    New Game Form - https://www.geeksforgeeks.org/java-swing-simple-user-registration-form/
    Exceptions because of ClassLoader - Java's ExceptionInInitializerError.java, ImageIO.java class' javadocs.
    ClassLoader to get images - https://www.tutorialspoint.com/java/lang/classloader_getresource.htm
