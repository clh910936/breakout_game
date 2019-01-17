import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class WinLoseScene extends Scene {
    private final String WIN = "Congratulations, you win!";
    private final String LOSE = "Game Over";

    private int myScore;
    private String myString;

    public Group myRoot;

    WinLoseScene(Group root, int score, String string){
        super(root, Breakout.SIZE, Breakout.SIZE, Breakout.BACKGROUND);

        myRoot = root;
        myScore = score;
        myString = string;
        if(myString.equals("win")){
            createWinScene();
        }
        else{
            //createLoseScene();
        }
    }

    private void createWinScene(){
        Text congratsLine = new Text(WIN);
        congratsLine.setFont(new Font(20));
        congratsLine.setTextAlignment(TextAlignment.JUSTIFY);
        congratsLine.setX(0);
        congratsLine.setY(100);
        double height = congratsLine.getLayoutBounds().getHeight();
        double width = congratsLine.getLayoutBounds().getWidth();
        myRoot.getChildren().add(congratsLine);
    }

}
