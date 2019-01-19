import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HomeScene extends Scene {
    private boolean timeToSwitch;
    private Group myRoot;

    HomeScene(Group root){
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);
        this.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        myRoot = root;
        timeToSwitch = false;
        createAndAddTitle();
    }

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.SPACE){
            timeToSwitch = true;
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
