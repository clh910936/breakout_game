import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Ball extends Circle {
    private Point myCenter;
    private boolean mySticky;
    private Paddle myPaddle;        //the paddle the ball starts on

    //TODO: figure out radius
    public static final double RADIUS = 7;
    private int myXSpeed = -50;
    private int myYSpeed = -50;

    private final Paint ballColor = Color.AQUAMARINE;
    private final Paint ballOutlineColor = Color.BLACK;

    Ball(Paddle paddle){
        this.setFill(ballColor);
        this.setStroke(ballOutlineColor);
        this.setRadius(RADIUS);

        mySticky = true;
        myPaddle = paddle;

        calcStartLocation();
        setLocation(myCenter);
    }

    private void calcStartLocation(){
        Point paddlePoint = myPaddle.getLocation();
        double x = paddlePoint.getX() + myPaddle.getWidth()/2;
        double y = paddlePoint.getY() - RADIUS;
        myCenter = new Point(x, y);

    }

    public void setXSpeed(int xSpeed){
        myXSpeed = xSpeed;
    }
    public void setYSpeed(int ySpeed){
        myYSpeed = ySpeed;
    }

    public void flipXSpeed(){
        myXSpeed *= -1;
    }

    public void flipYSpeed(){
        myYSpeed *= -1;
    }


    public void setLocation(Point point){
        myCenter = point;
        this.setCenterX(myCenter.getX());
        this.setCenterY(myCenter.getY());
    }

    public int getXSpeed(){
        return myXSpeed;
    }

    public int getYSpeed(){
        return myYSpeed;
    }

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

    public boolean checkLostBall(){
        double y = myCenter.getY() + RADIUS;
        return y > Breakout.SIZE;
    }

    public void move(double elapsedTime){
        //ball moves freely
        if(!mySticky) {
            double newX = myCenter.getX() + myXSpeed * elapsedTime;
            double newY = myCenter.getY() + myYSpeed * elapsedTime;
            Point newPosition = new Point(newX, newY);
            this.setLocation(newPosition);
        }
        //Ball aligns with paddle
        else{
            calcStartLocation();
            setLocation(myCenter);
        }
    }

    public void flipSticky(){
        mySticky = false;
    }

    //used in LevelScene controls so the first ball found to be sticky is flipped
    public boolean isSticky(){
        return mySticky;
    }

    public boolean checkShapeCollisionAndFlipSpeed(Shape shape){
        Shape tempShape = intersect(this, shape);
        double shapeHeight = tempShape.getBoundsInLocal().getHeight();
        double shapeWidth = tempShape.getBoundsInLocal().getWidth();

        if(shapeHeight != -1 || shapeWidth != -1){
            if(shapeHeight > shapeWidth){
                myXSpeed *= -1;
            }
            if(shapeHeight == shapeWidth){
                myXSpeed *= -1;
                myYSpeed *= -1;
                movementJump();
            }
            else{
                myYSpeed *= -1;
            }
            return true;
        }
        return false;
    }

    //skips the ball slightly in one direction to try to reduce glitches/getting stuck
    private void movementJump(){
        double newX = myCenter.getX() + Integer.signum(myXSpeed) * 3;
        double newY = myCenter.getY() + Integer.signum(myYSpeed) * 3;
        Point newPosition = new Point(newX, newY);
        this.setLocation(newPosition);
    }
}
