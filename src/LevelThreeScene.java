import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class LevelThreeScene extends LevelScene {
    private BouncyBlock myBouncyBlock;


    LevelThreeScene(String fileName, Group root, Logistics logistic) throws Exception {
        super(fileName, root, logistic);
        initializeLevelThree();
    }

    private void initializeLevelThree(){
        addBouncyBlock();
        for(int k = 0; k < myPaddles.size(); k++){
            myPaddles.get(k).setConstantMovePaddle();
            myPaddles.get(k).setSpeed(150);
        }
    }

    @Override
    protected void checkAllCollisions(Ball ball){
        super.checkAllCollisions(ball);
        checkBouncyBlockCollision(ball);
    }

    @Override
    public void update(double elapsedTime){
        super.update(elapsedTime);
        updateConstantMovePaddle(elapsedTime);
    }

    private void updateConstantMovePaddle(double elapsedTime){
        for(int k = 0; k < myPaddles.size(); k++){
            Paddle currentPaddle = myPaddles.get(k);
            if(currentPaddle.isConstantMove()){
                currentPaddle.move(elapsedTime);
            }
        }
    }

    private void checkBouncyBlockCollision(Ball ball){
        Shape tempShape = Shape.intersect(ball, myBouncyBlock);
        double shapeHeight = tempShape.getBoundsInLocal().getHeight();
        double shapeWidth = tempShape.getBoundsInLocal().getWidth();

        if(shapeHeight > 0 || shapeWidth > 0){
            myBouncyBlock.ballHit(ball);
        }
    }

    private void addBouncyBlock(){
        Point center = new Point(100, 100);
        BouncyBlock bouncyBlock = new BouncyBlock();

        myBouncyBlock = bouncyBlock;
        myRoot.getChildren().add(myBouncyBlock);
    }

    @Override
    protected void addNextLevel(){
        myLogistics.addFutureScene("BonusLevel");
    }

    @Override
    protected void checkLevelWon(){
        if(myBlocks.size() == 0){
            myLogistics.addFutureScene("BeatTheGame");
            addNextLevel();
            myLogistics.nextLevel();
            myLogistics.readyForSceneSwitch();

        }
    }
}
