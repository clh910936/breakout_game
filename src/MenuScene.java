import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuScene extends Scene {
    private Group myRoot;
    private Logistics myLogistic;

    MenuScene(Group root, Logistics logistic){
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        myRoot = root;
        myLogistic = logistic;
        createAndAddTitle();
    }

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.SPACE){
            myLogistic.addFutureScene("LevelOne");
            myLogistic.readyForSceneSwitch();
        }
    }

    private void createAndAddTitle(){
        BetterText title = new BetterText("Breakout");
        title.setFont(new Font(50));
        title.setFill(Color.WHITE);
        title.isUnderline();
        title.setCenter(Breakout.SIZE/2, 50);

        myRoot.getChildren().add(title);
    }
}
