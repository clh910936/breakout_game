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
}
