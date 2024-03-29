/**
 * @author Carrie Hunner
 * Logistics handles the variables that are intrinsic to the progression of the game.
 * These include the score, number of lives, and the switching and order of scenes
 */

import java.util.ArrayList;

public class Logistics {
    private int myLevel;
    private int myScore;
    private int myLivesLeft;
    private int myPointMultiplier;
    private boolean isTimeForSceneSwitch;
    private ArrayList<String> myNextScenes;

    /**
     * Initializes all of the variables to their starting values
     * Level: 1
     * Score: 0
     * Lives Left: 2
     */
    Logistics(){
        initializeAllVariables();
        myNextScenes = new ArrayList<>();
    }

    //sets all variables to starting values
    private void initializeAllVariables() {
        myLevel = 1;
        myScore = 0;
        myLivesLeft = 2;
        myPointMultiplier = 1;

        isTimeForSceneSwitch = false;
    }

    /**
     * Adds to the score
     * @param score integer value to add to the score
     *              This value is multiplied by the pointMultiplier
     *              before being added. Assumes the @param score is positive
     */
    public void increaseScore(int score){
        myScore += score * myPointMultiplier;
    }

    /**
     * Returns integer value of the score
     * @return score
     */
    public int getScore(){
        return myScore;
    }

    /**
     * Decreases the number of lives by one
     */
    public void loseLife(){
        myLivesLeft -= 1;
    }

    /**
     * Increases the number of lives by one
     */
    public void addLife(){
        myLivesLeft += 1;
    }

    /**
     * Increases the integer value of the level by 1
     */
    public void nextLevel(){
        myLevel += 1;
    }

    /**
     * @return int of the number of lives remaining
     */
    public int numLivesLeft(){
        return myLivesLeft;
    }

    /**
     * Sets the level int to 1
     */
    public void resetLevels(){
        myLevel = 1;
    }

    /**
     * @return int of current level
     */
    public int getLevel(){
        return myLevel;
    }

    /**
     * @param name of scene to add
     * This string will later be used as a key in the HashMap of scenes in the Breakout Class
     */
    public void addFutureScene(String name){
        myNextScenes.add(name);
    }

    /**
     * Sets the Class to be ready for a scene switch
     */
    public void setReadyForSceneSwitch(){
        isTimeForSceneSwitch = true;
    }

    /**
     * @return boolean of if it's time for a scene switch
     */
    public boolean isTimeForSceneSwitch(){
        return isTimeForSceneSwitch;
    }

    //returns the string of the next scene on the ArrayList
    //assumes there is a next scene set

    /**
     * Turns off being ready for a scene switch and returns next scene
     * @return String: name of the next scene corresponding to the Key of that scene
     * in Breakout's HashMap containing all the scenes
     */
    public String getNextScene(){
        isTimeForSceneSwitch = false;
        return myNextScenes.remove(0);
    }

    /**
     * Flips the point multiplier on/off
     */
    public void flipPointMultiplier(){
        if(myPointMultiplier == 1){
            myPointMultiplier = 5;
        }
        else{
            myPointMultiplier = 1;
        }
    }

    /**
     * Re-initializes all the variables to their starting values.
     * Clears the list of future scenes
     */
    public void reset(){
        initializeAllVariables();
        myNextScenes.clear();
    }

    /**
     * Puts the Menu as the next scene.
     * Used for the [M] cheat key.
     * assumes that Breakout will call to clear the rest of the scenes on the ArrayList.
     */
    public void setMenuNext(){
        myNextScenes.add(0, "Menu");
    }

    /**
     * Removes all remaining lives.
     * used in cheats to skip to the end of the level.
     */
    public void loseAllLives(){
        myLivesLeft = 0;
    }
}