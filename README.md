Breakout
====

This project implements the game of Breakout.

Name: Carrie Hunner

### Timeline

Start Date: 1/12/19

Finish Date: 1/20/19

Hours Spent: ~35

### Resources Used
* Oracle
* StackOverflow
* Piazza

### Running the Program

Main class: Breakout

Data files needed:   
* Level1.txt
* Level2.txt
* Level3.txt
* Rules.txt  
Note: all these files are accessed in the program and need to 
remain in their current locations.

Key/Mouse inputs:  
* Left Arrow: moves paddle left
* Right Arrow: moves paddle right
* Up Arrow: releases the ball when attached to paddle


Cheat keys:
* [A] adds a life
* [B] adds a ball
* [H] returns to Home screen
* [L] auto loses the level
* [M] turns on a point multiplier of x5
* [N] auto to next level
* [R] removes a block at random from the level - no points are awarded for this
* [S] speeds up the ball each time pressed
* [W] auto wins the level

Known Bugs:
* When the ball hits two blocks at once, the speed direction is flipped
twice in quick succession and this results in no apparent change in direction
* On Level 2, if the paddles overlap sometimes the ball's direction
gets screwed up and it falls through the paddles and off the screen
* Sometimes the ball rolls on the paddle as opposed to shoots off, 
moving the paddle so the ball hits the edge knocks it off

Extra credit:
* **Thing Extra:** Added a bonus level scene that has a random number
of randomly generated blocks. Each of which has a random number
of hits assigned to break it
* Cheat key [S] to speed up the balls on the screen
* Cheat key [A] to add a life


### Notes
The general flow of my game consists of creating a scene associated
with each level or screen to be displayed. Each scene is then 
programmed to handle user input, as it differs from scene to scene, as
well as a reset and update method when necessary.

The Breakout Class is where all the animation happens. It initializes
all the scenes and steps through the timeline, but the most important
variables of the game are stored in the Logistics class. This class 
keeps track of the score, lives left, when it's time to switch scenes,
and which scenes to switch to. The instance of the Logistics Class used
communicates with nearly every class and brings all the most important
information together in one area.

**Changes from the Plan:** In the Plan, I claimed I would make
cheatkeys that would activate the paddle not moving ability as well
as adding another paddle that would behave inversely. I did code both
of these paddle abilities, however they are associated with Levels,
not cheat keys. The inverse paddle happens on level 2 and the 
constant moving paddle happens on level 3. My original intent
in claiming that these would be cheat keys was to troubleshoot, however
this proved to be unnecessary for confirming their functionality.
Given the time constraint and the loss of the usefulness of those
cheats, they were not created. 

I did add a cheat key that gives the player an extra life as well
as a key that adds another ball. Both of these proved to be invaluable
in troubleshooting and general enjoyment of gameplay. 

**Things I would change with more time:**
* Eliminate the BetterText Class
* Either commit to or eliminate the Point Class - right now
I use it in about half my classes
* Make an interface for the scenes that change, as they have
several of the same methods, though executed differently



### Impressions
* I learned a lot in a very short period of time and feel far more
comfortable with JavFx
* An overview of public/private/protected variables/methods
would have been helpful earlier in the project
* Stressing the importance of planning ahead how to organize the
project. This is the first independent coding project of this
scale that most of us had done and I fumbled my way through organization,
as I didn't do enough planning initially.