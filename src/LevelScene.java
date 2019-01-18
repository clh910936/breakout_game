import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class LevelScene extends Scene {
    public String myFile;
    public Group myRoot;



    protected ArrayList<Ball> myBalls= new ArrayList<>();
    protected ArrayList<Paddle> myPaddles = new ArrayList<>();
    protected ArrayList<Block> myBlocks = new ArrayList<>();

    //need to be accessed by subclasses
    protected double myElapsedTime;
    protected ArrayList<String> myNextScenesInfo;
    protected boolean timeForSceneSwitch;

    protected Logistics myLogistics;

    LevelScene(String fileName, Group root, Logistics logistic) throws Exception{
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myFile = fileName;
        myRoot = root;
        myNextScenesInfo = new ArrayList<>();
        myLogistics = logistic;

        addBall();
        addPaddle();
        createLevel();
    }


    //This needs to be public so it can be overrriden in LevelBonusScene
    public void createLevel() throws Exception {
        ArrayList<Block> blocks = generateLevelBlocks();
        myBlocks.addAll(blocks);
        for (int k = 0; k < blocks.size(); k++) {
            myRoot.getChildren().add(blocks.get(k));

        }
    }

    //Create an ArrayList of Blocks for a level
    //The first number on the line will be the index of the coordinates to the block from the myAllBlockCoordinates array
    //The second number will be the health of the block
    private ArrayList<Block> generateLevelBlocks() throws Exception{
        ArrayList<Block> result = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(myFile));
        //TODO: Make this a method
        while(in.ready()) {
            String line = in.readLine();
            String[] split = line.split(" ");
            int index = Integer.parseInt(split[0]);
            int health = Integer.parseInt(split[1]);
            Point currentPoint = Breakout.myAllBlockCoordinates.get(index);

            Block currentBlock = new Block(health, currentPoint);
            result.add(currentBlock);
        }
        return result;
    }

    public void addBall(){
        Ball tempBall = new Ball();
        myBalls.add(tempBall);
        myRoot.getChildren().add(tempBall);
    }

    public void addPaddle(){
        Paddle tempPaddle = new Paddle();
        myPaddles.add(tempPaddle);
        myRoot.getChildren().add(tempPaddle);
    }

    public void update(double elapsedTime){
        myElapsedTime = elapsedTime;
        updateBalls();
        checkLevelWon();
    }

    //Needs to be accessed by LevelThreeScene
    protected void updateBalls(){
        for(int k = 0; k < myBalls.size(); k++){
            Ball currentBall = myBalls.get(k);
            currentBall.move(myElapsedTime);

            checkAllCollisions(currentBall);
        }
    }

    protected void checkAllCollisions(Ball currentBall) {
        currentBall.checkWallCollision();
        checkPaddleCollision(currentBall);
        checkBlockCollision(currentBall);
    }

    private void checkPaddleCollision(Ball ball){
        for(int k = 0; k < myPaddles.size(); k++) {
            Paddle currentPaddle = myPaddles.get(k);
            checkShapeCollisionAndFlipSpeed(ball, currentPaddle);
        }
    }

    private void checkBlockCollision(Ball ball) {
        for (int k = 0; k < myBlocks.size(); k++) {
            Block currentBlock = myBlocks.get(k);

            if (checkShapeCollisionAndFlipSpeed(ball, currentBlock)) {
                int score = currentBlock.blockHit();
                myLogistics.increaseScore(score);
                if (score == 10) {
                    removeBlock(k);
                    k -= 1;     //a block was removed so preventing k from indexing up
                }
            }
        }
    }

    private void removeBlock(int index){
        myRoot.getChildren().remove(myBlocks.get(index));
        myBlocks.remove(index);
    }

    private boolean checkShapeCollisionAndFlipSpeed(Ball ball, Shape shape){
        Shape tempShape = Shape.intersect(ball, shape);
        double shapeHeight = tempShape.getBoundsInLocal().getHeight();
        double shapeWidth = tempShape.getBoundsInLocal().getWidth();

        if(shapeHeight != -1 || shapeWidth != -1){
            if(shapeHeight > shapeWidth){
                ball.flipXSpeed();
            }
            if(shapeHeight == shapeWidth){
                ball.flipXSpeed();
                ball.flipYSpeed();
            }
            else{
                ball.flipYSpeed();
            }
            return true;
        }
        return false;
    }

    public ArrayList<Paddle> getPaddles(){
        return myPaddles;
    }

    //Needs to be available for BonusLevel and for cheatkeys
    public int randomNumGen(int min, int max){
        Random generator = new Random();
        return generator.nextInt(max-min+1) + min;
    }

    private void handleKeyInput(KeyCode code){
        for(int k = 0; k < myPaddles.size(); k++) {
            Paddle currentPaddle = myPaddles.get(k);
            if (code == KeyCode.RIGHT) {
                currentPaddle.move(myElapsedTime, 1);
            }
            else if(code == KeyCode.LEFT) {
                currentPaddle.move(myElapsedTime, -1);
            }

//            //TODO: Glitchy needs to be dealt with
            //remove random block
            else if(code == KeyCode.R){
                int numBlocks = myBlocks.size();
                if(numBlocks > 0) {
                    int randIndex = randomNumGen(0, numBlocks - 1);
                    removeBlock(randIndex);
                }
            }

            //Clears blocks and checks if it wins - it will
            else if(code == KeyCode.N){
                myBlocks.clear();
                myRoot.getChildren().clear();
            }
        }


    }

    //needs to be accessed by LevelThreeScene
    protected void checkLevelWon(){
        if(myBlocks.size() == 0){
            myNextScenesInfo.add("Win");
            timeForSceneSwitch = true;
        }
    }

    public ArrayList<String> checkSceneSwitch(){
        return myNextScenesInfo;
    }

}
