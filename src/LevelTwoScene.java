import javafx.scene.Group;

import java.util.ArrayList;

public class LevelTwoScene extends LevelScene {

    LevelTwoScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
    }

    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("LevelThree");
    }
}
