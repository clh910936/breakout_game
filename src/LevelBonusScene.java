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
            int coordinatesIndex = randomNumGen(10, 159);   //ignoring the topmost row for the score header
            if(points.add(coordinatesIndex)){
                int health = randomNumGen(1, 5);
                Block currentBlock = new Block(health, myAllBlockCoordinates.get(coordinatesIndex), myLogistics);
                myBlocks.add(currentBlock);
                myRoot.getChildren().add(currentBlock);
            }
        }
    }

    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("Menu");
    }



}
