import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Line;



public class Breakout extends Application{

    //Background and Setup
    public static final String TITLE = "Breakout";
    public static final int SIZE = 1000;
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


    @Override
    public void start(Stage stage) throws Exception {

    }


    public static void main(String args[]){
        launch(args);
    }
}
