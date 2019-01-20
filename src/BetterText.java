/**
 * @author Carrie Hunner
 * This class is an extension of the Text class
 * It consists of one method to help the user center their text at any coordinates
 * It is a helper class used in both the WinLoseScene Class and the MenuScene Class
 */

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BetterText extends Text {
    double myX;
    double myY;

    double myWidth;
    double myHeight;

    BetterText(String text){
        super(text);
        myX = 0;
        myY = 0;

        this.setTextAlignment(TextAlignment.CENTER);

    }

    //Sets the center of a text object 
    public void setCenter(double x, double y){
        myWidth = this.getLayoutBounds().getWidth();
        myHeight = this.getLayoutBounds().getHeight();
        double newX = x - myWidth/2;
        double newY = y - myHeight/2;

        this.relocate(newX, newY);
    }
}
