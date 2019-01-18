import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class LevelThreeScene extends LevelScene {
    private BouncyBlock myBouncyBlock;
    LevelThreeScene(String fileName, Group root) throws Exception {
        super(fileName, root);
        initializeLevelThree();
    }

    private void initializeLevelThree(){
        addBouncyBlock();
    }

    @Override
    protected void checkAllCollisions(Ball ball){
        super.checkAllCollisions(ball);
        checkBouncyBlockCollision(ball);
    }

    private void checkBouncyBlockCollision(Ball ball){
        Shape tempShape = Shape.intersect(ball, myBouncyBlock);
        double shapeHeight = tempShape.getBoundsInLocal().getHeight();
        double shapeWidth = tempShape.getBoundsInLocal().getWidth();

        if(shapeHeight != 0 || shapeWidth != 0){
            ball.flipYSpeed();
            ball.flipXSpeed();
        }
    }

    private void addBouncyBlock(){
        Point center = new Point(100, 100);
        BouncyBlock bouncyBlock = new BouncyBlock(center);

        myBouncyBlock = bouncyBlock;
        myRoot.getChildren().add(myBouncyBlock);
    }



}
