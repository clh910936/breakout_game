/**
 * @author Carrie Hunner
 * This Class creates the Menu scene
 * It sets the title of the game and sets the rules text by reading
 * from a *.txt file
 */

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileReader;

public class MenuScene extends Scene {
    private Group myRoot;
    private Logistics myLogistic;

    MenuScene(Group root, Logistics logistic) throws Exception {
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        myRoot = root;
        myLogistic = logistic;
        addAllChildren();
    }

    //adds all the text
    private void addAllChildren() throws Exception {
        createAndAddTitle();
        createAndAddRules();
        createAndAddStart();
        createAndAddBonusText();
    }

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.SPACE){
            myLogistic.addFutureScene("LevelOne");
            myLogistic.setReadyForSceneSwitch();
        }
        else if(code == KeyCode.B){
            myLogistic.addFutureScene("BonusLevel");
            myLogistic.setReadyForSceneSwitch();
        }
    }

    //Creates the text for the title "Breakout"
    private void createAndAddTitle(){
        BetterText title = new BetterText("Breakout");
        title.setFont(new Font(30));
        title.setFill(Color.rgb(0, 188, 255));
        title.isUnderline();
        title.setCenter(Breakout.SIZE/2, 30);

        myRoot.getChildren().add(title);
    }

    //reads the text from Rules.txt and creates Text objects
    private void createAndAddRules() throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("Rules.txt"));
        int yValue = 50;
        while(in.ready()) {
            yValue += 15;
            String line = in.readLine();
            Text rules = new Text(line);
            rules.setFont(new Font(12));
            rules.setFill(Color.WHITE);
            rules.setX(5);
            rules.setY(yValue);
            myRoot.getChildren().add(rules);
        }
    }

    //Creates the text telling the player how to start
    private void createAndAddStart(){
        BetterText spacebarText = new BetterText("Press SpaceBar to Begin");
        spacebarText.setFont(new Font(15));
        spacebarText.setFill(Color.WHITE);
        spacebarText.isUnderline();
        spacebarText.setCenter(Breakout.SIZE/2, 370);

        myRoot.getChildren().add(spacebarText);
    }

    //creates the text telling the player how to access the bonus level
    private void createAndAddBonusText(){
        BetterText bonusLevelText = new BetterText("Press [B] to start Bonus level");
        bonusLevelText.setFont(new Font(15));
        bonusLevelText.setFill(Color.WHITE);
        bonusLevelText.isUnderline();
        bonusLevelText.setCenter(Breakout.SIZE/2, 390);

        myRoot.getChildren().add(bonusLevelText);
    }
}
