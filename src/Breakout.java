import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


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
    public static final double BLOCK_HEIGHT = SIZE/20;
    public static final double BLOCK_WIDTH = SIZE/10;

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize stuff
        makeAllBlockCoordinates();

        myLevelOneScene = createLevel("Level1.txt");
        myLevelTwoScene = createLevel("Level2.txt");
        stage.setScene(myLevelTwoScene);
        stage.setTitle(TITLE);
        stage.show();
    }


    public static void main(String args[]){
        launch(args);
    }

    private Scene createLevel(String file) throws Exception{
        var root = new Group();
        var scene = new Scene(root, SIZE, SIZE, BACKGROUND);
        ArrayList<Block> blocks = generateBlocks(file);
        for(int k = 0; k < blocks.size(); k++){
            root.getChildren().add(blocks.get(k));
        }
        return scene;
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
        while(in.ready()) {
            String line = in.readLine();
            String[] split = line.split(" ");
            int index = Integer.parseInt(split[0]);
            int health = Integer.parseInt(split[1]);
            Point currentPoint = myAllBlockCoordinates.get(index);

            Block currentBlock = new Block(BLOCK_HEIGHT, BLOCK_WIDTH, health, currentPoint);
            result.add(currentBlock);
        }
        return result;
    }


}
