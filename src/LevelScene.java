import javafx.scene.Group;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class LevelScene extends Scene {
    public String myFile;
    public Group myRoot;

    public ArrayList<Ball> myBalls= new ArrayList<>();
    public ArrayList<Paddle> myPaddles = new ArrayList<>();
    public ArrayList<Block> myBlocks = new ArrayList<>();

    LevelScene(String fileName, Group root) throws Exception{
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        myFile = fileName;
        myRoot = root;

        addBall();
        addPaddle();
        createLevel();
    }

    //Generates a scene for a level with a text file as the parameter
    //Used for Levels 1, 2, 3
    private void createLevel() throws Exception{
        if(myFile.equals(" ")) {
            createBonusLevel();
        }
        else {
            createRegularLevels();
        }
    }

    private void createRegularLevels() throws Exception {
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

    private void createBonusLevel() {
        int numBlocks = randomNumGen(15, 179);
        HashSet<Integer> points = new HashSet<>();

        //TODO: make method
        while(points.size() < numBlocks){
            int coordinatesIndex = randomNumGen(0, 179);
            if(points.add(coordinatesIndex)){
                int health = randomNumGen(1, 5);
                Block currentBlock = new Block(health, Breakout.myAllBlockCoordinates.get(coordinatesIndex));
                myRoot.getChildren().add(currentBlock);
            }
        }
    }

    private int randomNumGen(int min, int max){
        Random generator = new Random();
        return generator.nextInt(max-min+1) + min;
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
}
