import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    public int myHealth;
    public static final double myHeight = 20;
    public static final double myWidth = 40;
    public double myX;
    public double myY;

    private final Paint ONEHEALTH = Color.rgb(0, 188, 255);
    private final Paint TWOHEALTH = Color.rgb(0, 255, 17);
    private final Paint THREEHEALTH = Color.rgb(250, 255, 0);
    private final Paint FOURHEALTH = Color.rgb(240, 150, 66);
    private final Paint FIVEHEALTH = Color.rgb(200, 0, 0);
    private final Paint OUTLINE = Color.BLACK;
    private Logistics myLogistic;


    Block(int health, Point point, Logistics logistic){
        myHealth = health;
        myX = point.getX();
        myY = point.getY();
        myLogistic = logistic;


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
        else if(myHealth == 4){
            return FOURHEALTH;
        }
        else{
            return FIVEHEALTH;
        }

    }

    public boolean blockHitAndReturnIfDestroyed(){
        myHealth -= 1;
        //TODO: DEAL WITH THIS - BACK TO 10
        myLogistic.increaseScore(10);
        if(myHealth != 0){
            this.setFill(determineColor());
            return false;
        }
        else{
            return true;
        }


    }


}
