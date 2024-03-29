/**
 * @author Carrie Hunner
 * This Class serves as a superclass for all the Level Scenes
 * It sets up all the children within the scene, handles user input and powerups
 * It is dependent on am instance of the Logistics class for several of its methods
 */

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LevelScene extends Scene {
    //These variables need to be accessed by Level subclasses
    protected String myFile;
    protected Group myRoot;
    protected double myElapsedTime;
    protected Logistics myLogistics;


    protected ArrayList<Ball> myBalls= new ArrayList<>();
    protected ArrayList<Paddle> myPaddles = new ArrayList<>();
    protected ArrayList<Block> myBlocks = new ArrayList<>();
    protected ArrayList<Text> myText = new ArrayList<>();

    protected ArrayList<Point> myAllBlockCoordinates = new ArrayList<>();

    //Specific to LevelScene class
    private HashSet<Integer> myPowerUpsEarned;

    /**
     * Sets all of the instance variables and sets up the elements of the level
     * @param fileName: name of the .txt file to read for generating the blocks
     * @param root: root of the program
     * @param logistic: instance of logistics class used by other classes
     */
    LevelScene(String fileName, Group root, Logistics logistic) throws Exception{
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        myFile = fileName;
        myLogistics = logistic;
        myRoot = root;
        myPowerUpsEarned = new HashSet<>();

        makeAllBlockCoordinates();
        addAllChildren();
    }

    //adds all the graphics to the level
    private void addAllChildren() throws Exception {
        addPaddle();
        addBall();
        createLevel();
        createAndAddHeader();
    }


    //This needs to be protected so it can be overrriden in LevelBonusScene
    //Generates and adds all blocks to the level
    protected void createLevel() throws Exception {
        ArrayList<Block> blocks = readFileAndGenerateCorrespondingBlocks();
        myBlocks.addAll(blocks);
        for (int k = 0; k < blocks.size(); k++) {
            myRoot.getChildren().add(blocks.get(k));
        }

    }

    //Create an ArrayList of Blocks for a level
    //The first number on the line will be the index of the coordinates to the block from the myAllBlockCoordinates array
    //The second number will be the health of the block
    private ArrayList<Block> readFileAndGenerateCorrespondingBlocks() throws Exception{
        ArrayList<Block> result = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(myFile));
        while(in.ready()) {
            String line = in.readLine();
            String[] split = line.split(" ");
            int index = Integer.parseInt(split[0]);
            int health = Integer.parseInt(split[1]);
            Point currentPoint = myAllBlockCoordinates.get(index);

            Block currentBlock = new Block(health, currentPoint, myLogistics);
            result.add(currentBlock);
        }
        return result;
    }


    /**
     * Adds a ball to the scene
     */
    public void addBall(){
        Ball tempBall = new Ball(myPaddles.get(0));
        myBalls.add(tempBall);
        myRoot.getChildren().add(tempBall);
    }

    /**
     * adds a generic paddle to the scene and ArrayList
     */
    public void addPaddle(){
        Paddle tempPaddle = new Paddle();
        myPaddles.add(tempPaddle);
        myRoot.getChildren().add(tempPaddle);
    }

    /**
     * Updates all the children that change in the scene
     * Moves all objects that need to move
     * @param elapsedTime: double of the amount if time passed
     */
    public void update(double elapsedTime){
        myElapsedTime = elapsedTime;
        updateBalls();
        checkLevelWon();
        updateHeader();
        checkForPowerUps();
    }

    //Checks all balls for movement and collisions
    private void updateBalls(){
        for(int k = 0; k < myBalls.size(); k++){
            Ball currentBall = myBalls.get(k);

            currentBall.move(myElapsedTime);
            checkAllCollisions(currentBall);

            if(currentBall.isBallLost()){
                myBalls.remove(currentBall);
                myRoot.getChildren().remove(currentBall);

                checksAndDealsWithLifeLost();
            }
        }
    }

    private void checksAndDealsWithLifeLost() {
        if(myBalls.size() == 0){
            myLogistics.loseLife();
            if(checkLevelLost()){
                endLevelAddLoseScene();
            }
            else{
                addBall();
            }
        }
    }

    //Sets the Lose scene to be next and the menu after that
    private void endLevelAddLoseScene() {
        myLogistics.addFutureScene("Lose");
        myLogistics.addFutureScene("Menu");
        myLogistics.resetLevels();
        myLogistics.setReadyForSceneSwitch();
    }

    //checks for collisions with walls, paddles, and blocks
    //Overriden in Level 3
    protected void checkAllCollisions(Ball currentBall) {
        currentBall.checkAndHandleWallCollision();
        checkPaddleCollision(currentBall);
        checkBlockCollision(currentBall);
    }

    private void checkPaddleCollision(Ball ball){
        for(int k = 0; k < myPaddles.size(); k++) {
            Paddle currentPaddle = myPaddles.get(k);
            ball.checkShapeCollisionAndFlipSpeed(currentPaddle);
        }
    }

    private void checkBlockCollision(Ball ball) {
        for (int k = 0; k < myBlocks.size(); k++) {
            Block currentBlock = myBlocks.get(k);

            if (ball.checkShapeCollisionAndFlipSpeed(currentBlock)) {
                currentBlock.blockHit();
                if (currentBlock.isBlockDestroyed()) {
                    removeBlock(k);
                    k -= 1;     //a block was removed so preventing k from indexing up
                }
            }
        }
    }

    //removes block from scene and myBlocks ArrayList
    private void removeBlock(int index){
        myRoot.getChildren().remove(myBlocks.get(index));
        myBlocks.remove(index);
    }


    //Generates a random number between the two ints - inclusive
    //Needs to be available for BonusLevel and for cheatkeys
    protected int randomNumGen(int min, int max){
        Random generator = new Random();
        return generator.nextInt(max-min+1) + min;
    }


    //Checks if the level is won and adds the next level to the future scenes
    //needs to be accessed by LevelThreeScene
    protected void checkLevelWon(){
        if(myBlocks.size() == 0){
            myLogistics.addFutureScene("Win");
            addNextLevel();
            myLogistics.nextLevel();
            myLogistics.setReadyForSceneSwitch();
        }
    }

    //assumes #balls = 0 because it's called only after that's confirmed
    private boolean checkLevelLost(){
        return myLogistics.numLivesLeft() == 0;
    }

    //score header
    private void createAndAddHeader(){
        Text levelText = new Text("Level: " + myLogistics.getLevel());
        levelText.setFont(new Font(15));
        levelText.setFill(Color.WHITE);
        levelText.setX(0);
        levelText.setY(15);

        Text scoreText = new Text("Score: " + myLogistics.getScore());
        scoreText.setFont(new Font(15));
        scoreText.setFill(Color.WHITE);
        scoreText.setX(100);
        scoreText.setY(15);

        Text livesText = new Text("Lives Left: " + myLogistics.getScore());
        livesText.setFont(new Font(15));
        livesText.setFill(Color.WHITE);
        livesText.setX(200);
        livesText.setY(15);

        myText.add(levelText);
        myText.add(scoreText);
        myText.add(livesText);


        myRoot.getChildren().add(levelText);
        myRoot.getChildren().add(scoreText);
        myRoot.getChildren().add(livesText);
    }

    //updates score header
    private void updateHeader(){
        myText.get(0).setText("Level: " + myLogistics.getLevel());
        myText.get(1).setText("Score: " + myLogistics.getScore());
        myText.get(2).setText("Lives Left: " + myLogistics.numLivesLeft());
    }

    //Overriden by each subclass
    //Holds place in other methods where it needs to be called
    protected void addNextLevel(){
        myLogistics.addFutureScene("");
    }

    //deals with powerups at 500, 100, 1500 pts
    private void checkForPowerUps(){
        int currentScore = myLogistics.getScore();
        if(currentScore != 0) {     //prevents powerups from all being added at the beginning
            if (currentScore % 1500 == 0 && !myPowerUpsEarned.contains(currentScore)) {
                myLogistics.addLife();
            }
            if (currentScore % 1000 == 0 && !myPowerUpsEarned.contains(currentScore)) {
                for (int k = 0; k < myBalls.size(); k++) {
                    myBalls.get(k).setSlowBall();
                }
            }
            if (currentScore % 500 == 0 && !myPowerUpsEarned.contains(currentScore)) {
                addBall();
                myPowerUpsEarned.add(currentScore);
            }
        }
    }

    /**
     * removes all the children from the root and re-initializes every object
     * that should be in the scene
     */
    public void reset() throws Exception {
        myRoot.getChildren().clear();
        myPowerUpsEarned.clear();
        myBalls.clear();
        myPaddles.clear();
        myBlocks.clear();
        myText.clear();

        addAllChildren();
    }

    //generates all possible block coordinates - used in the creation of the Levels
    private void makeAllBlockCoordinates(){
        for(int k = 0; k < Breakout.SIZE-2; k += Breakout.SIZE/20){
            for(int j = 0; j < Breakout.SIZE; j += Breakout.SIZE/10){
                Point temp = new Point(j, k);
                myAllBlockCoordinates.add(temp);
            }
        }
    }

    private void handleKeyInput(KeyCode code){
        //Paddle motion and ball release
        for(int k = 0; k < myPaddles.size(); k++) {
            Paddle currentPaddle = myPaddles.get(k);

            if (code == KeyCode.RIGHT) {
                if(currentPaddle.isConstantMove()){
                    currentPaddle.setDirection(1);
                }
                else {
                    currentPaddle.move(myElapsedTime, 1);
                }
            } else if (code == KeyCode.LEFT) {
                if(currentPaddle.isConstantMove()){
                    currentPaddle.setDirection(-1);
                }
                else {
                    currentPaddle.move(myElapsedTime, -1);
                }
            }
        }
        //releases ball at start
        if (code == KeyCode.UP) {
            for (int k = 0; k < myBalls.size(); k++) {
                if (myBalls.get(k).isSticky()) {
                    myBalls.get(k).turnStickyOff();
                    break;
                }
            }
        }
        //remove random block
        if(code == KeyCode.R){
            int numBlocks = myBlocks.size();
            if(numBlocks > 0) {
                int randIndex = randomNumGen(0, numBlocks - 1);
                removeBlock(randIndex);
            }
        }

        //adds a ball
        if (code == KeyCode.B){
            addBall();
        }

        //Auto wins the level
        if(code == KeyCode.W){
            myBlocks.clear();
            myRoot.getChildren().clear();
        }

        //Auto loses the level
        if(code == KeyCode.L){
            myLogistics.loseAllLives();
            endLevelAddLoseScene();
        }

        //Auto to next level - skip win scene
        if(code == KeyCode.N){
            addNextLevel();
            myLogistics.setReadyForSceneSwitch();
        }
        //Adds a life
        if(code == KeyCode.A){
            myLogistics.addLife();
        }

        //Point multiplier
        if(code == KeyCode.M){
            myLogistics.flipPointMultiplier();
        }

        //return home
        if(code == KeyCode.H){
            myLogistics.setMenuNext();
            myLogistics.setReadyForSceneSwitch();
        }

        //Speed up the balls
        if(code == KeyCode.S){
            for(int k = 0; k <myBalls.size(); k++){
                myBalls.get(k).increaseSpeed();
            }
        }
    }
}
