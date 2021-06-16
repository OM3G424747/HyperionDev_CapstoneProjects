# hyperionDev Capstone Project 2 - Level 1 :sparkles: :rocket: :sparkles:

## How to play

### Requirements
Before attempting to run the python file please make sure you have pygame installed on your system.
You can install pygame by opening up your CMD and entering "pip install pygame"

## Controls

Use the arrow keys or W,A,S,D to move.
W = Up
S = Down
A = Left
D = Right

## Game Objective

You need to collect 10 parts while avoiding astroids in space.
If you collect 10 parts you win.
If you get hit by a meteor, your mech's shields will weaken and after the 3rd meteor hit your sheilds will be deactivated. 
After your shields are deactivated, another hit will mean your mech will be destroyed and you'll lose. 

## Code explained

The objective for coding this game was to create a basic game that had:
1) - One "player" object that should be moved when pressing the up, down, left and right arrow keys.
2) - Have at least 3 "enemy" objects that the player should avoid. 
3) - Have at least 1 "prize" object that the player should collect.

### Code functions
All objects in the game are coded to react when player input is detected to move using the arrow keys or W,A,S,D keys.
For example, if the player presses "UP" or "W" all objects move down to create an illusion of the player moving up.
This is done my adding a possitive or negative "direction" for the Y Direction axis.
The X asix speed remains constant for all objects, expcept the player that can move forward or backwards. 

The meteors and collectable parts are created using lists. 
When the items reach 0 on the X axis they are reset to the maximum width of the screen + the width of the item being spawned.
This creates an illusion of having an infinate meteor field. 

If a game-over state is reached due to a win or a loss, the game loops in on itself if the player hits enter to try again. 
