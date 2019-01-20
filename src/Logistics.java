import java.util.ArrayList;

public class Logistics {
    private int myLevel;
    private int myScore;
    private int myLivesLeft;
    private int myPointMultiplier;
    private boolean isTimeForSceneSwitch;
    private ArrayList<String> myNextScenes;

    Logistics(){
        initializeAllVariables();
        myNextScenes = new ArrayList<>();
    }

    private void initializeAllVariables() {
        myLevel = 1;
        myScore = 0;
        myLivesLeft = 2;
        myPointMultiplier = 1;

        isTimeForSceneSwitch = false;
    }

    public void increaseScore(int score){
        myScore += score * myPointMultiplier;
    }

    public int getScore(){
        return myScore;
    }

    public boolean checkGameOver(){
        return myLivesLeft < 0;
    }

    public void loseLife(){
        myLivesLeft -= 1;
    }

    public void addLife(){
        myLivesLeft += 1;
    }

    public void nextLevel(){
        myLevel += 1;
    }

    public int numLivesLeft(){
        return myLivesLeft;
    }

    public void resetLevels(){
        myLevel = 1;
    }

    public int getLevel(){
        return myLevel;
    }

    public void addFutureScene(String name){
        myNextScenes.add(name);
    }

    public void isReadyForSceneSwitch(){
        isTimeForSceneSwitch = true;
    }

    public boolean isTimeForSceneSwitch(){
        return isTimeForSceneSwitch;
    }

    //assumes there is a next scene set
    public String getNextScene(){
        isTimeForSceneSwitch = false;
        return myNextScenes.remove(0);
    }

    //TODO: deal with points getting off with multiplier
    public void flipPointMultiplier(){
        if(myPointMultiplier == 1){
            myPointMultiplier = 5;
        }
        else{
            myPointMultiplier = 1;
        }
    }

    public void reset(){
        initializeAllVariables();
        myNextScenes.clear();
    }

    //Used for "M" cheat key to go directly to the menu
    public void setMenuNext(){
        myNextScenes.add(0, "Menu");
    }

    //used in cheats to skip to the end of the level
    public void loseAllLives(){
        myLivesLeft = 0;
    }
}


