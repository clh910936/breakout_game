import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class Breakout extends Application{
    //Background and Setup
    public static final String TITLE = "Breakout";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;

    //Scenes
    private LevelScene myHomeScene;
    private LevelOneScene myLevelOneScene;
    private LevelTwoScene myLevelTwoScene;
    private LevelScene myLevelThreeScene;
    private WinLoseScene myWinScene;
    private WinLoseScene myLoseScene;
    private LevelBonusScene myBonusLevelScene;

    //keep track of all the scenes
    private HashMap<String, Scene> myScenes;

    //keeping track of current scene and scene type
    private LevelScene myCurrentLevelScene;
    private WinLoseScene myCurrentWinLoseScene;
    private boolean myLevel = true;
    private Stage myStage;


    //Scene Components
    public static ArrayList<Point> myAllBlockCoordinates = new ArrayList<>();

    private int myScore = 0;

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize stuff
        makeAllBlockCoordinates();
        initializeScenes();
        myStage = stage;

        myCurrentWinLoseScene = myWinScene;
        myCurrentLevelScene = myLevelOneScene;
        myStage.setScene(myCurrentLevelScene);
        myStage.setTitle(TITLE);
        myStage.show();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, stage));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public static void main(String args[]){
        launch(args);
    }

    private void step(double elapsedTime, Stage stage){
        if(myLevel) {
            myCurrentLevelScene.update(elapsedTime);
            ArrayList<String> nextLevels = myCurrentLevelScene.checkSceneSwitch();
            if(nextLevels.size() != 0){
                changeScene(nextLevels.get(0));
                if(myCurrentWinLoseScene.update()){
                    changeScene(nextLevels.get(1));
                }
            }
        }
    }

    private void initializeScenes() throws Exception {
        myLevelOneScene = new LevelOneScene("Level1.txt", new Group());

        myLevelTwoScene = new LevelTwoScene("Level2.txt", new Group());
        myLevelThreeScene = new LevelScene("Level3.txt", new Group());
        myBonusLevelScene = new LevelBonusScene(" ", new Group());
        myWinScene = new WinLoseScene(new Group(), myScore, "win");
        myLoseScene = new WinLoseScene(new Group(), myScore, "");

        myScenes = new HashMap<>();
        myScenes.put("LevelOne", myLevelOneScene);
        myScenes.put("LevelTwo", myLevelTwoScene);
        myScenes.put("LevelThree", myLevelThreeScene);
        myScenes.put("BonusLevel", myBonusLevelScene);
        myScenes.put("Win", myWinScene);
        myScenes.put("Lose", myLoseScene);
    }



    //Creating an Arraylist of all upper left corner points for Blocks
    private void makeAllBlockCoordinates(){
        for(int k = 0; k < SIZE-2; k += SIZE/20){
            for(int j = 0; j < SIZE; j += SIZE/10){
                Point temp = new Point(j, k);
                myAllBlockCoordinates.add(temp);
            }
        }
    }

    private void changeScene(String nextScene){
        myStage.setScene(myScenes.get(nextScene));
    }





}
