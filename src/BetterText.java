import javafx.geometry.VPos;
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

    public void setCenter(double x, double y){
        myWidth = this.getLayoutBounds().getWidth();
        myHeight = this.getLayoutBounds().getHeight();
        double newX = x - myWidth/2;
        double newY = y - myHeight/2;

        this.relocate(newX, newY);
    }

    public double getHeight(){
        return myHeight;
    }


}
