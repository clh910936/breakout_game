/**
 * @author Carrie Hunner
 * This is a subclass of Circle
 * Creates a block that never breaks and flips the x and y direction of any shape
 * that hits it
 *
 * It's dependent on the Ball class for responding to being hit
 * @example BouncyBlock tester = new BouncyBlock();
 * This is currently hardcoded for location because it is only used once in my current game.
 */

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class BouncyBlock extends Circle {
    private static final int RADIUS = 20;
    private final Paint MY_FILL = Color.WHITE;
    private final Paint MY_STROKE = Color.WHITE;

    private Point CENTER = new Point(200, 180);

    /**
     * Sets up the graphics of BouncyBlock as well as the location
     */
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


    /**
     * Deflects the ball by flipping its directions
     * @param ball: that needs to be deflected
     */
    public void ballHit(Ball ball){
        ball.flipXSpeedDirection();
        ball.flipYSpeedDirection();
    }
}
