import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    public static final Paint BACKGROUND = Color.WHITE;

    //Scenes
    private LevelScene myHomeScene;
    private LevelScene myLevelOneScene;
    private LevelScene myLevelTwoScene;
    private LevelScene myLevelThreeScene;
    private LevelScene myWinScene;
    private LevelScene myLoseScene;
    private LevelScene myBonusLevelScene;
    private LevelScene myCurrentScene;


    //Scene Components
    public static ArrayList<Point> myAllBlockCoordinates = new ArrayList<>();

    private int myScore = 0;

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize stuff
        makeAllBlockCoordinates();
        createAllScenes();

        myCurrentScene = myLevelOneScene;
        stage.setScene(myCurrentScene);
        stage.setTitle(TITLE);
        stage.show();

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
        /*ArrayList<Ball> sceneBalls = mySceneBalls.get(myCurrentScene);
        ArrayList<Paddle> scenePaddles = myScenePaddles.get(myCurrentScene);
        ArrayList<Block> sceneBlocks = mySceneBlocks.get(myCurrentScene);


        for(int k = 0; k < sceneBalls.size(); k++){
            Ball currentBall = sceneBalls.get(k);
            double xCoord = currentBall.getCenterX();
            double yCoord = currentBall.getCenterY();

            Point newPosition = new Point(xCoord + currentBall.getXSpeed() * elapsedTime, yCoord + currentBall.getYSpeed() * elapsedTime);
            currentBall.setLocation(newPosition);
        }*/
    }

    private void createAllScenes() throws Exception {
        myLevelOneScene = new LevelScene("Level1.txt", new Group());
        myLevelTwoScene = new LevelScene("Level2.txt", new Group());
        myLevelThreeScene = new LevelScene("Level3.txt", new Group());
        myBonusLevelScene = new LevelScene(" ", new Group());
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




}
