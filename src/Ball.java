import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    public Point myCenter;

    //TODO: figure out radius
    public final double myRadius = 10;
    public int myXSpeed = 50;
    public int myYSpeed = 50;

    private final Paint ballColor = Color.AQUAMARINE;
    private final Paint ballOutlineColor = Color.BLACK;

    Ball(Point center){
        myCenter = center;

        this.setFill(ballColor);
        this.setStroke(ballOutlineColor);
        this.setCenterX(myCenter.getX());
        this.setCenterY(myCenter.getY());
        this.setRadius(myRadius);
    }

    public void setSpeed(int xSpeed, int ySpeed){
        myXSpeed = xSpeed;
        myYSpeed = ySpeed;
    }
}
