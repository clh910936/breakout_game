/**
 * @author Carrie Hunner
 * This class represents a Point consisting of an X and Y double
 * The coordinates are set when the Point is created
 * There are methods to return either the X or the Y value
 */

public class Point {
    private double myX;
    private double myY;

    Point(double x, double y){
        myX = x;
        myY = y;
    }

    /**
     * @return double of the X coordinate
     */
    public double getX(){
        return myX;
    }

    /**
     * @return double of the Y coordinate
     */
    public double getY(){
        return myY;
    }
}
