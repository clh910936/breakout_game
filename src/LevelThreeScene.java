/**
 * @author Carrie Hunner
 * This is a subclass of the LevelScene class
 * It is dependent on the BouncyBlock and Logistics class
 * It adds a bouncy block and adjusts the paddle to be constantly moving
 * for this scene
 */

import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class LevelThreeScene extends LevelScene {
    private BouncyBlock myBouncyBlock;

    LevelThreeScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
        initializeLevelThree();
    }

    //Adds a bouncy block and sets the paddles to be constantly moving
    private void initializeLevelThree(){
        addBouncyBlock();
        for(int k = 0; k < myPaddles.size(); k++){
            myPaddles.get(k).setConstantMovePaddle();
            myPaddles.get(k).setSpeed(150);
        }
    }

    //adds checking for collision with BouncyBlock
    @Override
    protected void checkAllCollisions(Ball ball){
        super.checkAllCollisions(ball);
        checkBouncyBlockCollision(ball);
    }

    //Adjusts the update for the paddle being in constant motion
    @Override
    public void update(double elapsedTime){
        super.update(elapsedTime);
        updateConstantMovePaddle(elapsedTime);
    }

    //moves the paddle
    private void updateConstantMovePaddle(double elapsedTime){
        for(int k = 0; k < myPaddles.size(); k++){
            Paddle currentPaddle = myPaddles.get(k);
            if(currentPaddle.isConstantMove()){
                currentPaddle.move(elapsedTime);
            }
        }
    }

    //checks and handles ball hitting bouncy block
    private void checkBouncyBlockCollision(Ball ball){
        Shape tempShape = Shape.intersect(ball, myBouncyBlock);
        double shapeHeight = tempShape.getBoundsInLocal().getHeight();
        double shapeWidth = tempShape.getBoundsInLocal().getWidth();

        if(shapeHeight > 0 || shapeWidth > 0){
            myBouncyBlock.ballHit(ball);
        }
    }

    //creates and adds a bouncy block to the scene
    private void addBouncyBlock(){
        BouncyBlock bouncyBlock = new BouncyBlock();

        myBouncyBlock = bouncyBlock;
        myRoot.getChildren().add(myBouncyBlock);
    }


    //ensures Menu is after win/lose scene
    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("Menu");
    }

    //goes to BeatTheGame scene instead of typical win scene
    @Override
    protected void checkLevelWon(){
        if(myBlocks.size() == 0){
            myLogistics.addFutureScene("BeatTheGame");
            addNextLevel();
            myLogistics.nextLevel();
            myLogistics.setReadyForSceneSwitch();

        }
    }

    @Override
    public void reset() throws Exception {
        super.reset();
        initializeLevelThree();
    }
}
