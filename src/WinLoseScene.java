/**
 * @author Carrie Hunner
 * This class generates and updates the Win/Lose/BeatGame scene
 * It is an extension of the scene class
 */

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;


public class WinLoseScene extends Scene {
    private final String WIN = "Congratulations, you win!";
    private final String LOSE = "Game Over";
    private final String BEAT_THE_GAME = "Congratulations, you beat the game!";
    private final Paint TEXT_COLOR = Color.WHITE;

    private String myWinLoseString;
    private Logistics myLogistic;
    private BetterText myScoreText;

    private Group myRoot;


    /**
     * Creates and initializes the scene
     * @param root: root of the program
     * @param string: determines if the scene is win, lose, or beat game
     *              "win" - generates a win scene
     *              "lose" - generates a lose scene
     *              "" (any other string) - generates a beat the game scene
     * @param logistic: instance of the Logistics class used by other classes
     */
    WinLoseScene(Group root, String string, Logistics logistic){
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        myRoot = root;
        myLogistic = logistic;
        myWinLoseString = string;
        addAllChildren();
    }

    private void addAllChildren() {
        if(myWinLoseString.equals("win")){
            createWinLoseHeader(WIN);
            createSpaceBarText("Next Level");
        }
        else if(myWinLoseString.equals("lose")){
            createWinLoseHeader(LOSE);
            createSpaceBarText("Menu");
        }
        else{
            createWinLoseHeader(BEAT_THE_GAME);
            createSpaceBarText("Menu");
        }
        createScoreText();
    }

    /**
     * Updates the Text containing the player's score
     */
    public void updateScoreText(){
        myScoreText.setText("Final Score: " + myLogistic.getScore());
    }

    //creates a header of the string input
    private void createWinLoseHeader(String header){
        BetterText congratsLine = new BetterText(header);
        congratsLine.setFont(new Font(20));
        congratsLine.setFill(TEXT_COLOR);

        congratsLine.setCenter(200, 170);
        myRoot.getChildren().add(congratsLine);
    }

    //displays final score
    private void createScoreText(){
        myScoreText = new BetterText("Final Score: " + myLogistic.getScore());
        myScoreText.setFont(new Font(15));
        myScoreText.setFill(TEXT_COLOR);

        myScoreText.setCenter(200, 200);
        myRoot.getChildren().add(myScoreText);
    }

    //creates the text instructing the player on how to continue
    private void createSpaceBarText(String text){
        BetterText spaceBarLine = new BetterText("Press Space Bar To Go To " + text);
        spaceBarLine.setFont(new Font(10));
        spaceBarLine.setFill(TEXT_COLOR);

        spaceBarLine.setCenter(200, 240);
        myRoot.getChildren().add(spaceBarLine);
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.SPACE) {
            myLogistic.setReadyForSceneSwitch();
        }

        //return home
        if(code == KeyCode.H){
            myLogistic.setMenuNext();
            myLogistic.setReadyForSceneSwitch();
        }
    }
}
