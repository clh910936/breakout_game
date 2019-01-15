import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    private int mySpeed = 20;

    private Point myLocation = new Point(180, 390);

    public static final double WIDTH = 40.0;
    public static final double HEIGHT = 10.0;

    private static final Paint paddleFill = Color.CORAL;
    private static final Paint paddleStroke = Color.BLACK;

    Paddle(){
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.setFill(paddleFill);
        this.setStroke(paddleStroke);
    }

    public void setSpeed(int speed){
        mySpeed = speed;
    }

    public void setLocation(Point point){
        myLocation = point;
    }
}
