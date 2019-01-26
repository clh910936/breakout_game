/**
 * @author Carrie Hunner
 * This class is used to create a ball and set its properties and physics.
 * It extends the Circle class to use its graphics and location methods.
 *
 */

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball extends Circle {
    private Point myCenter;
    private Paddle myPaddle;    //paddle the ball will start on
    private long myStartTimer;

    //Optional properties
    private boolean isSlow;
    private boolean isSticky;   //is ball attached to paddle - used when a ball is added

    private int myXSpeed = -75;
    private int myYSpeed = -75;
    private int SLOW_SPEED = 50;
    private final long SLOW_TIME_LENGTH = 7000;
    private final Paint ballColor = Color.AQUAMARINE;
    private final Paint ballOutlineColor = Color.BLACK;

    public static final double RADIUS = 7;

    /**
     *Initializes the ball to start on the paddle, sets the ball's graphic properties, and defaults to the
     * ball not being slow. The graphic is set to be blue with a radius of 7.  The start location is then
     * calculated and set using the paddle's coordinates.
     * @param paddle This is the paddle the ball will start out sticky to.
     *               Example: Ball myBall = new Ball(paddle_1);
     *               This will create a ball with its sticky property activated, causing it to be attached
     *               to paddle_1.
     */
    Ball(Paddle paddle){
        myPaddle = paddle;

        isSticky = true;
        isSlow = false;

        createAndSetBallGraphics();
    }

    /*
    Methods that Initialize the Ball and Properties
     */
    private void createAndSetBallGraphics() {
        this.setFill(ballColor);
        this.setStroke(ballOutlineColor);
        this.setRadius(RADIUS);
        calcStartLocation();
        setCenterLocation(myCenter);
    }

    private void calcStartLocation(){
        Point paddlePoint = myPaddle.getLocation();
        double x = paddlePoint.getX() + myPaddle.getWidth()/2;
        double y = paddlePoint.getY() - RADIUS;
        myCenter = new Point(x, y);

    }

    /*
    Methods that are publicly accessible
     */

    /**
     * Sets the ball's X direction speed.
     * Assumes negative number indicates moving left.
     * Assumes positive number indicates moving right.
     * @param xSpeed new x direction speed.
     *
     */
    public void setXSpeed(int xSpeed){
        myXSpeed = xSpeed;
    }

    /**
     * Sets the ball's Y direction speed.
     * Assumes negative number means moving up
     * Assumes positive number means moving down.
     * @param ySpeed new y direction speed.
     */

    public void setYSpeed(int ySpeed){
        myYSpeed = ySpeed;
    }


    /**
     * Inverts the sign of the X direction velocity.
     * Eg. if the ball is moving to the right, calling this method would
     * cause it to move left.
     */
    public void flipXSpeedDirection(){
        myXSpeed *= -1;
    }


    /**
     * Inverts the sign of the Y direction velocity.
     * Eg. if the ball is moving to the up, calling this method would
     * cause it to move down.
     */
    public void flipYSpeedDirection(){
        myYSpeed *= -1;
    }

    public void setCenterLocation(Point point){
        myCenter = point;
        this.setCenterX(myCenter.getX());
        this.setCenterY(myCenter.getY());
    }

    //checks for wall collision and flips the speed
    public void checkWallCollision(){
        double x = myCenter.getX();
        double y = myCenter.getY();

        if(x + RADIUS > Breakout.SIZE || x - RADIUS < 0){
            myXSpeed *= -1;
        }

        if(y - RADIUS < 0){
            myYSpeed *= -1;
        }
    }

    //Determines if the ball has fallen off the bottom of the screen
    public boolean isBallLost(){
        double y = myCenter.getY() + RADIUS;
        return y > Breakout.SIZE;
    }

    //Moves the ball based on the speed instance variables and the time elapsed
    //Sticky property checked to determine if ball should move with paddle or independently
    public void move(double elapsedTime){
        //ball moves independently
        if(!isSticky) {
            double newX = myCenter.getX() + myXSpeed * elapsedTime;
            double newY = myCenter.getY() + myYSpeed * elapsedTime;
            Point newPosition = new Point(newX, newY);
            this.setCenterLocation(newPosition);
        }
        //Ball moves with paddle
        else{
            calcStartLocation();
            setCenterLocation(myCenter);
        }

        //Ball Slow powerup is enabled
        if(isSlow){
            checkSlowTimer();
        }
    }


    //Ball released from paddle and can be used independently
    //Used in LevelScene when the up button is pushed
    public void turnStickyOff(){
        isSticky = false;
    }

    //used in LevelScene controls so the first ball found to be sticky is flipped
    public boolean isSticky(){
        return isSticky;
    }

    //Checks if ball collides with shape
    //Adjusts the speed signs accordingly
    public boolean checkShapeCollisionAndFlipSpeed(Shape shape){
        Shape tempShape = intersect(this, shape);
        double shapeHeight = tempShape.getBoundsInLocal().getHeight();
        double shapeWidth = tempShape.getBoundsInLocal().getWidth();

        //Collides
        if(shapeHeight != -1 || shapeWidth != -1){
            //Hit on left or right side
            if(shapeHeight > shapeWidth){
                myXSpeed *= -1;
            }
            //Hit perfectly
            if(shapeHeight == shapeWidth){
                myXSpeed *= -1;
                myYSpeed *= -1;
                movementJump();
            }
            //Hit on the top or bottom
            else{
                myYSpeed *= -1;
            }
            return true;
        }
        return false;
    }

    //Turns on slow powerup
    //Used in LevelScene PowerUps @1000 pts
    public void setSlowBall(){
        isSlow = true;
        myXSpeed = SLOW_SPEED * (int)Math.signum(myXSpeed);
        myYSpeed = SLOW_SPEED * (int)Math.signum(myYSpeed);
        myStartTimer = System.currentTimeMillis();
    }

    //Increases ball speed by 10 - used in cheat key
    public void increaseSpeed(){
        myXSpeed = (int)Math.signum(myXSpeed) * (Math.abs(myXSpeed) + 10);
        myYSpeed = (int)Math.signum(myYSpeed) * (Math.abs(myYSpeed) + 10);
    }

    /*
    Private Helper Methods
     */

    //checks if slow powerup is over
    private void checkSlowTimer() {
        if(System.currentTimeMillis() - myStartTimer > SLOW_TIME_LENGTH){
            isSlow = false;
            myYSpeed = (int)Math.signum(myYSpeed) * 100;
            myXSpeed = (int)Math.signum(myXSpeed) * 100;
        }
    }


    //skips the ball slightly in one direction to reduce glitches/getting stuck
    private void movementJump(){
        double newX = myCenter.getX() + Integer.signum(myXSpeed) * 5;
        double newY = myCenter.getY() + Integer.signum(myYSpeed) * 5;
        Point newPosition = new Point(newX, newY);
        this.setCenterLocation(newPosition);
    }
}
