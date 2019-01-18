import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    private int mySpeed = 250;
    private Point myLocation;
    private boolean myWallCollideOn = true;
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

        calcAndSetStartLocation();
    }

    private void calcAndSetStartLocation(){
        double x = Breakout.SIZE/2 - WIDTH/2;
        this.setLocation(x);
    }

    public void setSpeed(int speed){
        mySpeed = speed;
    }

    //Y never changes so don't need to change that
    public void setLocation(double newX){
        myLocation = new Point(newX, myY);
        this.setX(newX);
        this.setY(myY);
    }

    public void move(double elapsedTime, int direction){
        if(myWallCollideOn){
            if(checkLeftWallCollision() && direction < 0){
                return;
            }
            else if(checkRightWallCollision() && direction > 0){
                return;
            }
        }
        else {
            dealWithWallCollision();
        }
        double newX = myLocation.getX() + mySpeed * elapsedTime * direction;
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

    private void dealWithWallCollision(){
        double xLeft = myLocation.getX();
        double xRight = xLeft + WIDTH;

        if (xLeft < 0) {
            double newX = Breakout.SIZE - WIDTH;
            this.setLocation(newX);
        } else if (xRight > Breakout.SIZE) {
            this.setLocation(0);
        }
    }

    public void setWallCollideOn(boolean arg){
        myWallCollideOn = arg;
    }

    //used by ball to determine where it should start/move
    public Point getLocation(){
        return myLocation;
    }
}
