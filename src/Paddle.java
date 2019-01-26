/**
 * @author Carrie Hunner
 * Paddle Class creates a paddle object and sets its properties.
 * It has three abilities that can be turned on and off through boolean
 * variables: inverse Paddle, wall collision, and constant moving.
 */

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    private final double myY = Breakout.SIZE - HEIGHT;
    private static final double WIDTH = 50.0;
    private static final double HEIGHT = 10.0;
    private static final Paint paddleFill = Color.CORAL;
    private static final Paint paddleStroke = Color.BLACK;

    private int mySpeed = 350;
    private int myDirection;
    private Point myLocation;
    private boolean isWallCollideOn;
    private boolean isInvertPaddle;
    private boolean isConstantMovePaddle;


    /**
     * Sets the graphics of the paddle and initializes all the abilities
     * to be turned off.
     */
    Paddle(){
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.setFill(paddleFill);
        this.setStroke(paddleStroke);

        isWallCollideOn = true;
        isInvertPaddle = false;
        isConstantMovePaddle = false;

        setDirection(0);
        calcAndSetStartLocation();
    }

    private void calcAndSetStartLocation(){
        double x = Breakout.SIZE/2 - WIDTH/2;
        this.setLocation(x);
    }

    /**
     * Sets the speed value of the paddle
     * @param speed int value of the desired speed
     *              Assumes @param speed has signs indicating direction
     */
    public void setSpeed(int speed){
        mySpeed = speed;
    }

    /**
     *sets the direction of the paddle
     * @param direction 1 for right, -1 for left, 0 for not moving
     */
    public void setDirection(int direction){
        myDirection = direction;
    }


    /**
     * Sets the location of the X value of the paddle
     * Assumes paddle doesn't move vertically so Y value never changes
     * @param newX double value of the desired X location for upper left corner
     *             of the paddle
     */
    public void setLocation(double newX){
        myLocation = new Point(newX, myY);
        this.setX(newX);
        this.setY(myY);
    }

    /**
     * Moves the paddle after checking and accounting for all the special abilities
     * This method is used when the paddle is not constantly moving
     * @param elapsedTime double indicating the amount of time that's passed
     * @param direction int indicating direction of paddle movement, 1 is right and -1 is left
     */
    public void move(double elapsedTime, int direction){

        //inverted paddle needs to be 100% flipped in speed and key direction- problems were solved doing this
        if(isInvertPaddle){
            direction *= -1;
        }
        if(isWallCollideOn){
            if(checkLeftWallCollision() && direction < 0){
                return;
            }
            else if(checkRightWallCollision() && direction > 0){
                return;
            }
        }
        else {
            jumpToOtherWall();
        }
        double newX = myLocation.getX() + mySpeed * elapsedTime * direction;
        setLocation(newX);
    }

    /**
     * Moves the paddle and flips directions if it collides with the edge of the screen
     * This method is used when the paddle is constantly moving
     * @param elapsedTime double indicating the amount of time passed
     */
    public void move(double elapsedTime){
        if(checkLeftWallCollision()){
            setDirection(1);
        }
        else if(checkRightWallCollision()){
            setDirection(-1);
        }
        double newX = myLocation.getX() + mySpeed * elapsedTime * myDirection;
        setLocation(newX);
    }

    private boolean checkLeftWallCollision(){
        double xLeft = myLocation.getX();
        if(xLeft < 0) return true;
        return false;
    }

    private boolean checkRightWallCollision(){
        double xRight = myLocation.getX() + WIDTH;
        if(xRight > Breakout.SIZE) return true;
        return false;
    }

    //used for the teleporting paddle ability
    private void jumpToOtherWall(){
        double xLeft = myLocation.getX();
        double xRight = xLeft + WIDTH;

        if (xLeft < 0) {
            double newX = Breakout.SIZE - WIDTH;
            this.setLocation(newX);
        } else if (xRight > Breakout.SIZE) {
            this.setLocation(0);
        }
    }

    /**
     *
     * @param arg boolean determining if the paddle will collide with walls
     *            True: paddle will collide with walls
     *            False: paddle will teleport to opposite wall if it makes contact
     */
    public void isWallCollide(boolean arg){
        isWallCollideOn = arg;
    }


    //used by ball to determine where it should start/move

    /**
     * @return Point location of the upper left corner of the paddle
     */
    public Point getLocation(){
        return myLocation;
    }

    /**
     * Turns on invert paddle effect
     * The paddle will now respond inversely to user input
     */
    public void invertPaddle(){
        isInvertPaddle = true;
    }

    //used by level 3

    /**
     * Turns on the constant moving effect
     * The paddle will now be constantly in motion and
     * will only use player input to change directions
     */
    public void setConstantMovePaddle(){
        isConstantMovePaddle = true;
    }

    //used in LevelScene

    /**
     * @return
     *      True: it's in constant motion
     *      False: it's not in constant motion
     */
    public boolean isConstantMove(){
        return isConstantMovePaddle;
    }
}
