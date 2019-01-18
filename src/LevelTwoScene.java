import javafx.scene.Group;

import java.util.ArrayList;

public class LevelTwoScene extends LevelScene {

    LevelTwoScene(String fileName, Group root) throws Exception {
        super(fileName, root);
    }

    @Override
    public ArrayList<String> checkSceneSwitch(){
        if(timeForSceneSwitch) {
            myNextScenes.add("LevelThree");
            return myNextScenes;
        }
        return new ArrayList<String>();
    }
}
