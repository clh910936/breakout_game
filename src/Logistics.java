public class Logistics {
    private int myLevel;
    private int myScore;
    private int myLivesLeft;

    Logistics(){
        myLevel = 1;
        myScore = 0;
        myLivesLeft = 2;
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
}


