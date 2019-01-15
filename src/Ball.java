import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    public Point myCenter;

    //TODO: figure out radius
    public static final double RADIUS = 10;
    private int myXSpeed = 50;
    private int myYSpeed = 50;

    private final Paint ballColor = Color.AQUAMARINE;
    private final Paint ballOutlineColor = Color.BLACK;

    Ball(Point center){
        myCenter = center;

        this.setFill(ballColor);
        this.setStroke(ballOutlineColor);
        this.setCenterX(myCenter.getX());
        this.setCenterY(myCenter.getY());
        this.setRadius(RADIUS);
    }

    public void setSpeed(int xSpeed, int ySpeed){
        myXSpeed = xSpeed;
        myYSpeed = ySpeed;
    }
}
