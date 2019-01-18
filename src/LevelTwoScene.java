import javafx.scene.Group;

import java.util.ArrayList;

public class LevelTwoScene extends LevelScene {

    LevelTwoScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
    }

    @Override
    public ArrayList<String> checkSceneSwitch(){
        if(timeForSceneSwitch) {
            myNextScenesInfo.add("LevelThree");
            System.out.println("Level Three Added");
            return myNextScenesInfo;

        }
        return new ArrayList<String>();
    }
}
