import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class BouncyBlock extends Circle {
    private Point myPoint;
    private static final int RADIUS = 10;
    private final Paint MY_FILL = Color.BLACK;
    private final Paint MY_STROKE = Color.BLACK;

    BouncyBlock(Point point){
        myPoint = point;
    }

    private void initializeGraphics(){
        double x = myPoint.getX();
        double y = myPoint.getY();

        this.setRadius(RADIUS);
        this.setFill(MY_FILL);
        this.setStroke(MY_FILL);
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void ballHit(Ball ball){
        ball.flipXSpeed();
        ball.flipYSpeed();
    }
}
