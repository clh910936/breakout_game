/**
 * @author Carrie Hunner
 * This class creates a rectangular block
 * It keeps track of the "health" of the block, determines the corresponding color,
 * and if the block is hit it increases the score in the Logistics class
 *
 * Dependent on Logistics class for logging the score increase that accompanies hitting
 * a block
 *
 * @example Block testBlock = new Block(4, point, logistic);
 */

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
    public static final double myHeight = 20;
    public static final double myWidth = 40;

    private double myX;
    private double myY;
    private int myHealth;
    private Logistics myLogistic;

    private final Paint ONE_HEALTH = Color.rgb(0, 188, 255);
    private final Paint TWO_HEALTH = Color.rgb(0, 255, 17);
    private final Paint THREE_HEALTH = Color.rgb(250, 255, 0);
    private final Paint FOUR_HEALTH = Color.rgb(240, 150, 66);
    private final Paint FIVE_HEALTH = Color.rgb(200, 0, 0);
    private final Paint OUTLINE = Color.BLACK;

    /**
     * Initializes the instance variables and sets up the graphics
     * @param health: int between 1 and 5 for the number of hits to break the block
     * @param point: Point representing where the upper left corner of the block should be
     * @param logistic: Logistic to add score to, keep track of levels, and lives
     */
    Block(int health, Point point, Logistics logistic){
        myHealth = health;
        myX = point.getX();
        myY = point.getY();
        myLogistic = logistic;

        setBlockProperties();
    }

    /*
    Public Methods
     */

    //Updates health and logistics score

    /**
     * Called when a block is hit.
     * It decreases the health of the block and increases the score for the player.
     */
    public void blockHit(){
        myHealth -= 1;
        myLogistic.increaseScore(10);
    }

    //Returns true of block is destroyed, false otherwise

    /**
     * Checks if the block is out of health. If not,
     * it updates the color of block
     * @return boolean: true if destroyed, false if not
     */
    public boolean isBlockDestroyed(){
        if(myHealth != 0){
            this.setFill(determineColor());
            return false;
        }
        else{
            return true;
        }
    }

    /*
    Private Helper Methods
     */

    //Sets graphics of Block
    private void setBlockProperties() {
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
            return ONE_HEALTH;
        }
        else if(myHealth == 2){
            return TWO_HEALTH;
        }
        else if(myHealth == 3){
            return THREE_HEALTH;
        }
        else if(myHealth == 4){
            return FOUR_HEALTH;
        }
        else{
            return FIVE_HEALTH;
        }
    }
}
