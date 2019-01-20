import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LevelScene extends Scene {
    protected String myFile;
    public Group myRoot;


    protected ArrayList<Ball> myBalls= new ArrayList<>();
    protected ArrayList<Paddle> myPaddles = new ArrayList<>();
    protected ArrayList<Block> myBlocks = new ArrayList<>();
    protected ArrayList<Text> myText = new ArrayList<>();

    private HashSet<Integer> myPowerUpsEarned;

    //need to be accessed by subclasses
    protected double myElapsedTime;

    protected Logistics myLogistics;

    private final Paint HEADER_COLOR = Color.WHITE;

    LevelScene(String fileName, Group root, Logistics logistic) throws Exception{
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myFile = fileName;
        myLogistics = logistic;
        myRoot = root;
        myPowerUpsEarned = new HashSet<>();

        addAllChildren();
    }

    private void addAllChildren() throws Exception {
        addPaddle();
        addBall();
        createLevel();
        createAndAddHeader();
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

            Block currentBlock = new Block(health, currentPoint, myLogistics);
            result.add(currentBlock);
        }
        return result;
    }

    public void addBall(){
        Ball tempBall = new Ball(myPaddles.get(0));
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
        updateHeader();
        checkForPowerUps();
    }

    //Needs to be accessed by LevelThreeScene
    protected void updateBalls(){
        for(int k = 0; k < myBalls.size(); k++){
            Ball currentBall = myBalls.get(k);
            currentBall.move(myElapsedTime);
            checkAllCollisions(currentBall);

            if(currentBall.isBallLost()){
                myBalls.remove(currentBall);
                myRoot.getChildren().remove(currentBall);

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
        }
    }

    private void endLevelAddLoseScene() {
        myLogistics.addFutureScene("Lose");
        myLogistics.addFutureScene("Menu");
        myLogistics.resetLevels();
        myLogistics.isReadyForSceneSwitch();
    }

    protected void checkAllCollisions(Ball currentBall) {
        currentBall.checkWallCollision();
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

    private void removeBlock(int index){
        myRoot.getChildren().remove(myBlocks.get(index));
        myBlocks.remove(index);
    }

    public ArrayList<Paddle> getPaddles(){
        return myPaddles;
    }

    //Needs to be available for BonusLevel and for cheatkeys
    public int randomNumGen(int min, int max){
        Random generator = new Random();
        return generator.nextInt(max-min+1) + min;
    }


    //needs to be accessed by LevelThreeScene
    protected void checkLevelWon(){
        if(myBlocks.size() == 0){
            //TODO: addFutureScene Win
            myLogistics.addFutureScene("Win");
            addNextLevel();
            myLogistics.nextLevel();
            myLogistics.isReadyForSceneSwitch();
        }
    }

    //assumes #balls = 0 because it's called only after that's confirmed
    private boolean checkLevelLost(){
        return myLogistics.numLivesLeft() == 0;
    }


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

    private void updateHeader(){
        myText.get(0).setText("Level: " + myLogistics.getLevel());
        myText.get(1).setText("Score: " + myLogistics.getScore());
        myText.get(2).setText("Lives Left: " + myLogistics.numLivesLeft());
    }

    //Overriden by each subclass
    protected void addNextLevel(){
        myLogistics.addFutureScene("");
    }

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

    public void reset() throws Exception {
        myRoot.getChildren().clear();
        myPowerUpsEarned.clear();
        myBalls.clear();
        myPaddles.clear();
        myBlocks.clear();
        myText.clear();

        addAllChildren();
    }

    private void handleKeyInput(KeyCode code){
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

        //Auto to nect level - skip win scene
        if(code == KeyCode.N){
            addNextLevel();
            myLogistics.isReadyForSceneSwitch();
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
            myLogistics.isReadyForSceneSwitch();
        }

        //Speed up the balls
        if(code == KeyCode.S){
            for(int k = 0; k <myBalls.size(); k++){
                myBalls.get(k).increaseSpeed();
            }
        }
    }

}
