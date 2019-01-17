import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Ball extends Circle {
    private Point myCenter;

    //TODO: figure out radius
    public static final double RADIUS = 7;
    private int myXSpeed = -50;
    private int myYSpeed = -50;

    private final Paint ballColor = Color.AQUAMARINE;
    private final Paint ballOutlineColor = Color.BLACK;

    Ball(){
        this.setFill(ballColor);
        this.setStroke(ballOutlineColor);
        this.setRadius(RADIUS);

        calcStartLocation();
        setLocation(myCenter);
    }

    private void calcStartLocation(){
        double x = Breakout.SIZE/2;
        double y = Breakout.SIZE - Paddle.HEIGHT - RADIUS -2;       //subtract 2 so the ball doesn't intersect the paddle at the beginning
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

        if(y + RADIUS < 0){
            myYSpeed *= -1;
        }
    }

    public void move(double elapsedTime){
        double newX = myCenter.getX() + myXSpeed * elapsedTime;
        double newY = myCenter.getY() + myYSpeed * elapsedTime;
        Point newPosition = new Point(newX, newY);
        this.setLocation(newPosition);
    }

}
