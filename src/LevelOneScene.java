import javafx.scene.Group;

import java.util.ArrayList;

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

    @Override
    public ArrayList<String> checkSceneSwitch(){
        if(timeForSceneSwitch) {
            System.out.println("Made it to checkSceneSwitch called by Breakout");
            myNextScenes.add("LevelTwo");
            return myNextScenes;
        }
        return new ArrayList<String>();
    }
}
