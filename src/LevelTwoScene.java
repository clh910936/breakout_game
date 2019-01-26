/**
 * @author Carrie Hunner
 *This class extends LevelScene and makes small adjustments to increase the ball speed and
 * add a second paddle that behaves inversely to user input
 * Dependent on LevelScene and an instance of Logistics
 */

import javafx.scene.Group;

public class LevelTwoScene extends LevelScene {

    //Experimented with Paddle starts and this works best
    private final double PADDLE_ONE_X_START = 125;
    private final double PADDLE_TWO_X_START = 225;

    /**
     *Calls LevelScene constructor and initializes LevelTwoScene
     * @param fileName: name of the .txt file to read for generating the blocks
     * @param root: root of the program
     * @param logistic: instance of logistics class used by other classes
     */
    LevelTwoScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
        initializeLevelTwo();
    }

    private void initializeLevelTwo() {
        addPaddle();
        setupPaddles();
        increaseBallSpeed();
    }

    private void increaseBallSpeed() {
        for(int k = 0; k < myBalls.size(); k++){
            myBalls.get(k).setYSpeed(-100);
            myBalls.get(k).setXSpeed(-100);
        }
    }

    //sets paddle locations and sets up second paddle to be inverted
    private void setupPaddles(){
        Paddle paddle1 = myPaddles.get(0);
        paddle1.setLocation(PADDLE_ONE_X_START);

        Paddle paddle2 = myPaddles.get(1);
        paddle2.setLocation(PADDLE_TWO_X_START);
        paddle2.invertPaddle();
        paddle2.isWallCollide(true);
    }


    //ensures after winning, the player goes to Level Three
    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("LevelThree");
    }

    /**
     * Calls the LevelScene reset() and re-initializes LevelTwo
     */
    @Override
    public void reset() throws Exception {
        super.reset();
        initializeLevelTwo();
    }
}
