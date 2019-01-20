import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    private int mySpeed = 350;
    private int myDirection;
    private Point myLocation;
    private boolean isWallCollideOn = true;
    private boolean isInvertPaddle = false;
    private boolean isConstantMovePaddle = false;
    private final double myY = Breakout.SIZE - HEIGHT;

    public static final double WIDTH = 50.0;
    public static final double HEIGHT = 10.0;

    private static final Paint paddleFill = Color.CORAL;
    private static final Paint paddleStroke = Color.BLACK;

    Paddle(){
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.setFill(paddleFill);
        this.setStroke(paddleStroke);

        setDirection(0);
        calcAndSetStartLocation();
    }

    private void calcAndSetStartLocation(){
        double x = Breakout.SIZE/2 - WIDTH/2;
        this.setLocation(x);
    }

    public void setSpeed(int speed){
        mySpeed = speed;
    }

    /**
     *
     * @param direction 1 for right, -1 for left, 0 for not moving
     */
    public void setDirection(int direction){
        myDirection = direction;
    }

    //Y never changes so don't need to change that
    public void setLocation(double newX){
        myLocation = new Point(newX, myY);
        this.setX(newX);
        this.setY(myY);
    }

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

    //used for a constant move paddle
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

    public void isWallCollide(boolean arg){
        isWallCollideOn = arg;
    }


    //used by ball to determine where it should start/move
    public Point getLocation(){
        return myLocation;
    }

    //used by level2 scene
    public void flipSpeed(){
        mySpeed *= -1;
    }

    public void invertPaddle(){
        isInvertPaddle = true;
    }

    //used by level 3
    public void setConstantMovePaddle(){
        isConstantMovePaddle = true;
    }

    //used in LevelScene
    public boolean isConstantMove(){
        return isConstantMovePaddle;
    }
}
