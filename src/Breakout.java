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

    private Logistics myLogistics;

    //Scenes
    private MenuScene myHomeScene;
    private LevelOneScene myLevelOneScene;
    private LevelTwoScene myLevelTwoScene;
    private LevelThreeScene myLevelThreeScene;
    private WinLoseScene myWinScene;
    private WinLoseScene myLoseScene;
    private LevelBonusScene myBonusLevelScene;
    private WinLoseScene myBeatTheGameScene;
    private boolean hasBeenReset;

    //keep track of all the scenes
    private HashMap<String, Scene> myScenes;

    //keeping track of current scene and scene type
    private boolean myLevel = true;
    private Stage myStage;

    //Scene Components
    public static ArrayList<Point> myAllBlockCoordinates = new ArrayList<>();


    @Override
    public void start(Stage stage) throws Exception {
        //Initialize stuff
        makeAllBlockCoordinates();

        myStage = stage;
        myLogistics = new Logistics();
        hasBeenReset = true;    //Doesn't need to reset at the very beginning
        initializeScenes();

        myStage.setScene(myScenes.get("Menu"));
        myStage.setTitle(TITLE);
        myStage.show();

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY, stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public static void main(String args[]){
        launch(args);
    }

    private void step(double elapsedTime, Stage stage) throws Exception {
        if(myLogistics.checkSceneSwitch()){
            String sceneKey = myLogistics.getNextScene();
            System.out.println(sceneKey);
            Scene nextScene = myScenes.get(sceneKey);
            myStage.setScene(nextScene);
            if(nextScene instanceof MenuScene){
                hasBeenReset = false;
            }
        }

        if(myStage.getScene() instanceof LevelScene){
            LevelScene tempScene = (LevelScene) myStage.getScene();
            tempScene.update(elapsedTime);
        }
        else if(myStage.getScene() instanceof WinLoseScene){
            WinLoseScene tempScene = (WinLoseScene) myStage.getScene();
            tempScene.updateScoreText();
        }
        else if(myStage.getScene() instanceof MenuScene && !hasBeenReset){
            myLogistics.reset();
            myLevelOneScene.reset();
            myLevelTwoScene.reset();
            myLevelThreeScene.reset();
            myBonusLevelScene.reset() ;


            hasBeenReset = true;
        }

    }


    private void switchScene() {
        String sceneKey = myLogistics.getNextScene();
        Scene nextScene = myScenes.get(sceneKey);
        myStage.setScene(nextScene);
    }

    private void initializeScenes() throws Exception {
        myLevelOneScene = new LevelOneScene("Level1.txt", new Group(), myLogistics);
        myLevelTwoScene = new LevelTwoScene("Level2.txt", new Group(), myLogistics);
        myLevelThreeScene = new LevelThreeScene("Level3.txt", new Group(), myLogistics);
        myBonusLevelScene = new LevelBonusScene(" ", new Group(), myLogistics);
        myWinScene = new WinLoseScene(new Group(), "win", myLogistics);
        myLoseScene = new WinLoseScene(new Group(), "lose", myLogistics);
        myHomeScene = new MenuScene(new Group(), myLogistics);
        myBeatTheGameScene = new WinLoseScene(new Group(), "", myLogistics);

        myScenes = new HashMap<>();
        myScenes.put("LevelOne", myLevelOneScene);
        myScenes.put("LevelTwo", myLevelTwoScene);
        myScenes.put("LevelThree", myLevelThreeScene);
        myScenes.put("BonusLevel", myBonusLevelScene);
        myScenes.put("Win", myWinScene);
        myScenes.put("Lose", myLoseScene);
        myScenes.put("Menu", myHomeScene);
        myScenes.put("BeatTheGame", myBeatTheGameScene);
    }



    //TODO: move to LevelScene
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
