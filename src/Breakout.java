import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;


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
    private LevelThreeScene myLevelThreeScene;
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

    private int myScore;

    @Override
    public void start(Stage stage) throws Exception {
        //Initialize stuff
        makeAllBlockCoordinates();
        initializeScenes();
        myStage = stage;
        myScore = 0;

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
            ArrayList<String> nextLevelsInfo = myCurrentLevelScene.checkSceneSwitch();
            if(nextLevelsInfo.size() != 0){
                myScore = Integer.parseInt(nextLevelsInfo.get(0));
                changeScene(nextLevelsInfo.get(1));
                if(myCurrentWinLoseScene.update()){
                    changeScene(nextLevelsInfo.get(2));
                }
            }
        }
    }

    private void initializeScenes() throws Exception {
        myLevelOneScene = new LevelOneScene("Level1.txt", new Group());

        myLevelTwoScene = new LevelTwoScene("Level2.txt", new Group());
        myLevelThreeScene = new LevelThreeScene("Level3.txt", new Group());
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
        System.out.println(myAllBlockCoordinates.get(95).myX);
        System.out.println(myAllBlockCoordinates.get(95).myY);
    }


    //TODO: not updating current level scene
    private void changeScene(String nextScene){
        Scene next = myScenes.get(nextScene);
        if(next instanceof LevelScene){
            myCurrentLevelScene = (LevelScene) next;
            myCurrentLevelScene.setScore(myScore);
        }
        else{
            myCurrentWinLoseScene = (WinLoseScene) next;
            myCurrentWinLoseScene.setScore(myScore);
        }
        myStage.setScene(myScenes.get(nextScene));
    }





}
