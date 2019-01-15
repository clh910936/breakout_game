import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    private int mySpeed = 20;
    private Point myLocation;

    public static final double WIDTH = 50.0;
    public static final double HEIGHT = 10.0;

    private static final Paint paddleFill = Color.CORAL;
    private static final Paint paddleStroke = Color.BLACK;

    Paddle(){
        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);
        this.setFill(paddleFill);
        this.setStroke(paddleStroke);
        //TODO: not sure this setup feels ok
        calcStartLocation();
        this.setLocation(myLocation);
    }
    private void calcStartLocation(){
        double x = Breakout.SIZE/2 - WIDTH/2;
        double y = Breakout.SIZE - HEIGHT;
        myLocation = new Point(x, y);
    }
    public void setSpeed(int speed){
        mySpeed = speed;
    }

    public void setLocation(Point point){
        myLocation = point;
        this.setX(myLocation.getX());
        this.setY(myLocation.getY());
    }
}
