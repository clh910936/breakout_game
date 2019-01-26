/**
 * @author Carrie Hunner
 * This class drives the game and is dependent on every other class in the project
 * It initializes its scenes, creates the levels, and then runs between them and updates
 * the scenes accordingly
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;


public class Breakout extends Application{
    //Background and Setup
    private static final String TITLE = "Breakout";
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    public static final int SIZE = 400;
    public static final Paint BACKGROUND = Color.BLACK;

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

    private HashMap<String, Scene> myScenes;

    //Other
    private Stage myStage;
    private Logistics myLogistics;

    /**
     * Initializes all of the instance variables and creates all of the scenes
     * @param stage: takes in the stage such that this can be saved to an instance
     *             variable and it can be called later to get the scene that is currently up.
     */
    @Override
    public void start(Stage stage) throws Exception {
        myStage = stage;
        myLogistics = new Logistics();
        hasBeenReset = true;    //Doesn't need to reset at the very beginning

        initializeScenes();

        myStage.setScene(myScenes.get("Menu"));
        myStage.setTitle(TITLE);
        myStage.show();

        initializeAndStartAnimation(stage);
    }


    //Updates all the scenes and their children
    private void step(double elapsedTime, Stage stage) throws Exception {
        if(myLogistics.isTimeForSceneSwitch()){
            handleSceneSwitch();
        }

        if(myStage.getScene() instanceof LevelScene){
            updateLevel(elapsedTime);
        }
        else if(myStage.getScene() instanceof WinLoseScene){
            updateWinLoseScene();
        }
        else if(myStage.getScene() instanceof MenuScene && !hasBeenReset){
            resetScenesAndLogistics();
        }
    }

    //Creates all the scenes and adds them to myScenes HashMap
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

    //Used when returning to the Menu screen and starting the game again
    private void resetScenesAndLogistics() throws Exception {
        myLogistics.reset();
        myLevelOneScene.reset();
        myLevelTwoScene.reset();
        myLevelThreeScene.reset();
        myBonusLevelScene.reset() ;

        hasBeenReset = true;
    }

    private void updateWinLoseScene() {
        WinLoseScene tempScene = (WinLoseScene) myStage.getScene();
        tempScene.updateScoreText();
    }

    private void updateLevel(double elapsedTime) {
        LevelScene tempScene = (LevelScene) myStage.getScene();
        tempScene.update(elapsedTime);
    }

    //Switches to the next scene using Logistics Class
    private void handleSceneSwitch() {
        String sceneKey = myLogistics.getNextScene();
        System.out.println(sceneKey);
        Scene nextScene = myScenes.get(sceneKey);
        myStage.setScene(nextScene);
        if(nextScene instanceof MenuScene){
            hasBeenReset = false;
        }
    }


    private void initializeAndStartAnimation(Stage stage) {
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
}