import javafx.scene.Group;

import java.util.ArrayList;

public class LevelOneScene extends LevelScene {

    LevelOneScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
        turnOffPaddleWallCollide();
    }

    //Sets paddle to not hit walls - go around the corner
    private void turnOffPaddleWallCollide(){
        for(int k = 0; k < myPaddles.size(); k++){
            myPaddles.get(k).isWallCollide(false);
        }
    }

    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("LevelTwo");
    }

    @Override
    public void reset() throws Exception {
        super.reset();
        turnOffPaddleWallCollide();
    }
}
