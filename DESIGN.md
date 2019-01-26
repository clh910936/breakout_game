DESIGN.MD
=========

#High Level Design Goals
-
The overall goals of this project were to become familiar with Javafx and attempt a large-scale project. I had never created a program this large on my own from scratch, so this was an opportunity for me to attempt that and learn along the way things I should do differently in the future and things that went well.

In terms of design goals, we needed to design our own version of the game Breakout. We were given broad criteria we had to meet and were able to work creatively within that. My goal was to create the following:
- three levels
    - Design three different block configurations and implement a different paddle ability at every level
- three paddle abilities
    - Teleport paddle: when it hit a wall it would teleport to the other
    - Inverse Paddle: a second paddle was added and behaved inversely to the user input
    - Constant Movement Paddle: the player's keys could change the direction of the paddle, but it would never stop moving. If it hit a wall, it would reverse directions.
- three kinda of blocks
    - Typical one hit to break
    - Multiple hits to break
    - Bouncy Block: never breaks and deflects the ball
- three different power-ups
    - Point multiplier
    - Add a ball: starts on the player's paddle like at the beginning of the level
    - Slow the Balls: slows down all the balls on the screen for 7 seconds
- an indicator that displayed to the player their current score, their level, and the number of lives remaining
    - Create a header that is displayed on the top of the level screen
- a screen at the start explaining the rules
- cheat keys
    - Return to home screen
    - Skip to lose screen
    - Skip to win screen
    - Skip to next level
    - Remove a block at random

- Something extra:
    - Create a level that the number of blocks, their location, and their number of hits to break are all randomly generated


- Cheats Added in addition to plan:
    - Speed up ball
    - Add a life
    - Add a ball
    - Turn on point multiplier


# How to add a new feature

## Level
A LevelScene superclass was created to setup all the basic user controls, the graphics, and to add a ball and paddle. Adding another Level consists of extending the superclass, which I plan to switch to an abstract class as part of my refactoring, and making any desired adjustments. 

A new subclass could be created for the new level. From here, any special features or additional elements could be added or adjusted. Eg changing ball speeds or adding paddles.

For the block configuration, a .txt file would have to be created to indicate the index and the health of the blocks. The index corresponds to an ArrayList of Points of all possible block locations on the screen going from left to right and then top to bottom. In the .txt file each line would consist of an index, a space, and then a number between 1 and 5 to indicate the number of hits necessary to break the ball. The name of this file could then be passed into the constructor of the level.

Some methods to pay particular attention to and that would likely need to be overriden:
- addNextLevel(String "") this determines the ordering of the levels. For example, in the LevelOne class, the parameter would be "LevelTwo" as that is where the player would be directed after winning
- reset() it's imperative that all added elements or adjusted physics are accounted for in the reset. This is called when the player returns the homescreen. It resets the level so it can be played again.


## Block
Currently to add another block, either extending a shape (rectangle, circle, etc) or extending the existing Block class could work. The Block class and BouncyBlock class are unrelated, as they are different enough that a superclass likely wouldn't have been helpful. 

To add a block with more health, the only adjustment would be to the Block class and adding the new health value to the determineColor() method.

## Power ups
- Adding another paddle ability: currently the paddle abilites are all controlled by boolean instance variables in the paddle class and can be turned on and off. Creating another one of these could make it easy to apply that powerup to any paddle when a level is initialized and the paddles are added.
- Adding another ball ability: this is a similar situation as the paddle. The ball has its abilites as booleans that can easily be turned on and off when called. Adding another powerup here would be easy and then calling it on the ball at the scene initialization or where desired.
- Adding another cheat key to jump to a particular scene: the Logistics class controls which scenes are next seen. It keeps an ArrayList of String that correspond to the key of a HashMap with all the scenes. Creating a method that clears that list and adds the desired screen is easy. A method such as this already exists to jump directly to the menu.



# Major Design Choices and Trade-offs
- **Creating levels from a .txt file**: This saved a large amount of space in the code, as just one method to read the file and then create the corresponding blocks was needed. This also allowed for more intricate level designs, as an algorithms wasn't needed to determine their locations. Unfortunately this did take a large amount of time, as each level had between 50-150 blocks and these had to be typed into the file. The results was well-worth the trouble.
- **Each Level Having a Class:** this resulted in far more classes than some of my peers, however it made it easy to switch between levels. Each level had its own scenes, and once a Level SuperClass was created, making small adjustments to the objects in the level and their properties was relatively straightforward. Additionally, it's easy to add more levels.
- **Logistics Class:** This was a last minute addition to the program. I didn't plan enough ahead of time to determine where all the central variables (number of lives, score, next scene) would be stored and updated. Creating a class to keep track of all these variables was easier than passing them individually to each other class. Because it was last-minute, the organization was not very good and there are likely other methods that could be refactored into this class to improve its usefullness.
- **The Win/Lose/BeatTheGame all one scene:** They were all made into one scene. This kept the number of classes down, which I now understand is not necessarily the way to go for organization, and if/else statements were used to build the appropriate scene. This kept all of these in one place, but make reading and organizing the code harder. 


# Justify Assumptions or Decisions Made to Simplify Ambiguities in Functionality

- **Ball radius:** this was played with in terms of what it should be kept at. After some experimenting, a radius of 7 seemed to be a reasonable size with respect to the other objects.
- **Ball Speed:** This was also tinkered with quite a bit. It needed to be slow enough that the player could have a reasonable amount of time to respond to its movement but fast enough that when the end of the level was nearing and there were only a few blocks left, the player wouldn't get bored. I settled on the current speed after playing with it for a bit, but I also added a "speed up ball" key as an option for the player if they aren't challenged enough or if the end of the level is happening.
- **Point Increments:** I chose giving 10 points per block hit because then the max score a player can get is about 7,000. This seemed like a reasonable number and made the power-ups unlocked at 500, 1000, and 1500 points happen often enough without being too easy to get.
