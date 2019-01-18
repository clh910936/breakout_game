import javafx.scene.Group;

public class LevelOneScene extends LevelScene {

    LevelOneScene(String fileName, Group root) throws Exception {
        super(fileName, root);
        initializeLevelOne();
    }

    private void initializeLevelOne(){
        turnOffPaddleWallCollide();
    }

    //Sets paddle to not hit walls - go around the corner
    private void turnOffPaddleWallCollide(){
        for(int k = 0; k < myPaddles.size(); k++){
            myPaddles.get(k).setWallCollideOn(false);
        }
    }
}
