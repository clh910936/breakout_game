/**
 * @author Carrie Hunner
 * this class is an extension of LevelScene and turns off wall collide for the paddle on the scene
 */

import javafx.scene.Group;

public class LevelOneScene extends LevelScene {
    /**
     * Calls the constructor from LevelScene and turns off wall collision for the paddle
     * @param fileName: name of the .txt file to use to create the blocks
     * @param root: root of the program
     * @param logistic: instance of Logistic class used by other classes
     * @throws Exception
     */
    LevelOneScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
        turnOffPaddleWallCollide();
    }

    //Sets paddle to not hit walls - go around the corner/teleport
    private void turnOffPaddleWallCollide(){
        for(int k = 0; k < myPaddles.size(); k++){
            myPaddles.get(k).isWallCollide(false);
        }
    }

    //Ensures that after the win scene it goes to Level Two
    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("LevelTwo");
    }


    /**
     * Calls the LevelScene reset
     * ensures that when the levels are reset, the paddles still maintain their teleportation abilities
     */
    @Override
    public void reset() throws Exception {
        super.reset();
        turnOffPaddleWallCollide();
    }
}
