/**
 * @author Carrie Hunner
 * This class is an extension of LevelScene
 * It creates a level of randomly generated blocks
 */

import javafx.scene.Group;
import java.util.HashSet;

public class LevelBonusScene extends LevelScene {

    /**
     * Calls the constructor from LevelScene
     * @param filename: in this case, the filename doesn't matter because it's never used
     * @param root: root of the program
     * @param logistic: instance of logistic used by the other classes
     */
    LevelBonusScene(String filename, Group root, Logistics logistic) throws Exception {
        super(filename, root, logistic);

    }

    //Generates a random number of blocks at random coordinates with random health to create the level
    //Overrides the createLevel() in LevelScene

    /**
     * Overrides the createLevel() from the LevelScene class and sets up the number of random blocks
     * to be generated. It then calls a private method to actually create those blocks
     */
    @Override
    public void createLevel(){
        //numbers chosen so the whole screen can't be too full or too empty
        int numBlocks = randomNumGen(15, 149);
        HashSet<Integer> points = new HashSet<>();

        generateAndAddRandomBlocks(numBlocks, points);
    }

    private void generateAndAddRandomBlocks(int numBlocks, HashSet<Integer> points) {
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

    //ensures the Menu is the scene after the bonus level Win/Lose scene
    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("Menu");
    }
}
