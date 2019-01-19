import javafx.scene.Group;
import java.util.HashSet;

public class LevelBonusScene extends LevelScene {

    LevelBonusScene(String filename, Group root, Logistics logistic) throws Exception {
        super(filename, root, logistic);

    }

    @Override
    public void createLevel(){
        int numBlocks = randomNumGen(15, 179);
        HashSet<Integer> points = new HashSet<>();

        //TODO: make method
        while(points.size() < numBlocks){
            int coordinatesIndex = randomNumGen(0, 169);
            if(points.add(coordinatesIndex)){
                int health = randomNumGen(1, 5);
                Block currentBlock = new Block(health, Breakout.myAllBlockCoordinates.get(coordinatesIndex), myLogistics);
                myRoot.getChildren().add(currentBlock);
            }
        }
    }


}
