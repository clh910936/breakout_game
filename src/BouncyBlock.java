import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class BouncyBlock extends Circle {
    private Point CENTER = new Point(200, 180);
    private static final int RADIUS = 20;
    private final Paint MY_FILL = Color.WHITE;
    private final Paint MY_STROKE = Color.WHITE;

    BouncyBlock(){
        initializeGraphics();
    }

    private void initializeGraphics(){
        double x = CENTER.getX();
        double y = CENTER.getY();

        this.setRadius(RADIUS);
        this.setFill(MY_FILL);
        this.setStroke(MY_STROKE);
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void ballHit(Ball ball){
        ball.flipXSpeedDirection();
        ball.flipYSpeedDirection();
    }
}
