import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball extends Circle {
    public Point myCenter;

    //TODO: figure out radius
    public static final double RADIUS = 7;
    private int myXSpeed = 50;
    private int myYSpeed = 50;

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
        double y = Breakout.SIZE - Paddle.HEIGHT - RADIUS;
        myCenter = new Point(x, y);

    }
    public void setSpeed(int xSpeed, int ySpeed){
        myXSpeed = xSpeed;
        myYSpeed = ySpeed;
    }

    public void setLocation(Point point){
        myCenter = point;
        this.setCenterX(myCenter.getX());
        this.setCenterY(myCenter.getY());
    }
}
