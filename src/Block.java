import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    public int myHealth;
    public double myHeight;
    public double myWidth;
    public double myX;
    public double myY;

    private final Paint ONEHEALTH = Color.rgb(0, 50, 250);
    private final Paint TWOHEALTH = Color.rgb(0, 0, 200);
    private final Paint THREEHEALTH = Color.rgb(0, 0, 150);
    private final Paint FOURHEALTH = Color.rgb(0, 0, 100);
    private final Paint OUTLINE = Color.BLACK;


    Block(double height, double width, int health, Point point){
        myHealth = health;
        myHeight = height;
        myWidth = width;
        myX = point.getX();
        myY = point.getY();


        //Setting Properties
        this.setWidth(myWidth);
        this.setHeight(myHeight);
        this.setStroke(OUTLINE);
        this.setFill(determineColor());
        this.setX(myX);
        this.setY(myY);
    }

    //Returns the color corresponding to the Block's health
    private Paint determineColor(){
        if(myHealth == 1){
            return ONEHEALTH;
        }
        else if(myHealth == 2){
            return TWOHEALTH;
        }
        else if(myHealth == 3){
            return THREEHEALTH;
        }
        else{
            return FOURHEALTH;
        }

    }

}
