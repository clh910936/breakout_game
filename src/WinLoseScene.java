import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class WinLoseScene extends Scene {
    private final String WIN = "Congratulations, you win!";
    private final String LOSE = "Game Over";

    private final Paint TEXT_COLOR = Color.WHITE;

    private String myString;
    private Logistics myLogistic;

    public Group myRoot;


    WinLoseScene(Group root, String string, Logistics logistic){
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        myRoot = root;
        myLogistic = logistic;
        myString = string;
        if(myString.equals("win")){
            createWinLoseHeader(WIN);
            createSpaceBarText("Next Level");
        }
        else{
            createWinLoseHeader(LOSE);
            createSpaceBarText("Menu");
        }
        createScoreText();
    }
//TODO: delete this?
//    public boolean update(){
//        createScoreText();
//        return readyForNextLevel;
//    }
    private void createWinLoseHeader(String header){
        BetterText congratsLine = new BetterText(header);
        congratsLine.setFont(new Font(20));
        congratsLine.setFill(TEXT_COLOR);

        congratsLine.setCenter(200, 170);
        myRoot.getChildren().add(congratsLine);
    }


    private void createScoreText(){
        BetterText scoreLine = new BetterText("Final Score: " + myLogistic.getScore());
        scoreLine.setFont(new Font(15));
        scoreLine.setFill(TEXT_COLOR);

        scoreLine.setCenter(200, 200);
        myRoot.getChildren().add(scoreLine);
    }

    private void createSpaceBarText(String text){
        BetterText spaceBarLine = new BetterText("Press Space Bar To Go To " + text);
        spaceBarLine.setFont(new Font(10));
        spaceBarLine.setFill(TEXT_COLOR);

        spaceBarLine.setCenter(200, 240);
        myRoot.getChildren().add(spaceBarLine);
    }
    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.SPACE) {
            myLogistic.readyForSceneSwitch();
        }
    }


}
