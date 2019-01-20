import javafx.scene.Group;

import java.util.ArrayList;

public class LevelTwoScene extends LevelScene {

    //Experimented with Paddle starts and this works best
    private final double PADDLE_ONE_START = 125;
    private final double PADDLE_TWO_START = 225;

    LevelTwoScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
        addPaddle();
        setupPaddles();
    }

    private void setupPaddles(){
        Paddle paddle1 = myPaddles.get(0);
        paddle1.setLocation(PADDLE_ONE_START);

        Paddle paddle2 = myPaddles.get(1);
        paddle2.setLocation(PADDLE_TWO_START);
        paddle2.invertPaddle();
        paddle2.isWallCollide(true);
    }


    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("LevelThree");
    }

    @Override
    public void reset() throws Exception {
        super.reset();

        addPaddle();
        setupPaddles();
    }
}
