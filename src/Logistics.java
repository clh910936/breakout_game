import java.util.ArrayList;

public class Logistics {
    private int myLevel;
    private int myScore;
    private int myLivesLeft;
    //TODO: ask megan about naming this
    private boolean timeForSceneSwitch;
    private ArrayList<String> myNextScenes;

    Logistics(){
        myLevel = 1;
        myScore = 0;
        myLivesLeft = 2;

        timeForSceneSwitch = false;
        myNextScenes = new ArrayList<>();
    }

    public void increaseScore(int score){
        myScore += score;
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

    public void readyForSceneSwitch(){
        timeForSceneSwitch = true;
    }

    public boolean checkSceneSwitch(){
        return timeForSceneSwitch;
    }

    //assumes there is a next scene set
    public String getNextScene(){
        timeForSceneSwitch = false;
        return myNextScenes.remove(0);
    }
}


