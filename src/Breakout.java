import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class Breakout extends Application{
    //Background and Setup
    public static final String TITLE = "Breakout";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.WHITE;

    //Scenes
    private Scene myHomeScene;
    private Scene myLevelOneScene;
    private Scene myLevelTwoScene;
    private Scene myLevelThreeScene;
    private Scene myWinScene;
    private Scene myLoseScene;
    private Scene myBonusLevelScene;

    //Scene Components
    public ArrayList<Point> myAllBlockCoordinates = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize stuff
        makeAllBlockCoordinates();

        //TODO: make this a method
        myLevelOneScene = createLevel("Level1.txt");
        myLevelTwoScene = createLevel("Level2.txt");
        myLevelThreeScene = createLevel("Level3.txt");
        myBonusLevelScene = createLevel(" ");

        stage.setScene(myLevelTwoScene);
        stage.setTitle(TITLE);
        stage.show();
    }



    public static void main(String args[]){
        launch(args);
    }

    //Generates a scene for a level with a text file as the parameter
    //Used for Levels 1, 2, 3
    private Scene createLevel(String file) throws Exception{
        var root = new Group();
        var scene = new Scene(root, SIZE, SIZE, BACKGROUND);

        Ball test = new Ball(new Point(200.0, 390.0));
        root.getChildren().add(test);

        if(file.equals(" ")) {
            //TODO: make this its own method
            int numBlocks = randomNumGen(15, 179);
            HashSet<Integer> points = new HashSet<>();
            while(points.size() < numBlocks){
                int coordinatesIndex = randomNumGen(0, 179);
                if(points.add(coordinatesIndex)){
                    int health = randomNumGen(1, 5);
                    //TODO: maybe make block dimensions intrinsic to the block class
                    Block currentBlock = new Block(health, myAllBlockCoordinates.get(coordinatesIndex));
                    root.getChildren().add(currentBlock);
                }
            }
        }
        else {
            //TODO: make this its own method
            ArrayList<Block> blocks = generateBlocks(file);
            for (int k = 0; k < blocks.size(); k++) {
                root.getChildren().add(blocks.get(k));
            }
        }
        return scene;
        }

    private int randomNumGen(int min, int max){
        Random generator = new Random();
        return generator.nextInt(max-min+1) + min;
    }

    //Creating an Arraylist of all upper left corner points for Blocks
    private void makeAllBlockCoordinates(){
        for(int k = 0; k < SIZE; k += (SIZE/20)-2){
            for(int j = 0; j < SIZE; j += SIZE/10){
                Point temp = new Point(j, k);
                myAllBlockCoordinates.add(temp);
            }
        }
    }

    //Create an ArrayList of Blocks for a level
    //The first number on the line will be the index of the coordinates to the block from the myAllBlockCoordinates array
    //The second number will be the health of the block
    private ArrayList<Block> generateBlocks(String file) throws Exception{
        ArrayList<Block> result = new ArrayList<>();

        BufferedReader in = new BufferedReader(new FileReader(file));
        //TODO: Make this a method
        while(in.ready()) {
            String line = in.readLine();
            String[] split = line.split(" ");
            int index = Integer.parseInt(split[0]);
            int health = Integer.parseInt(split[1]);
            Point currentPoint = myAllBlockCoordinates.get(index);

            Block currentBlock = new Block(health, currentPoint);
            result.add(currentBlock);
        }
        return result;
    }


}
