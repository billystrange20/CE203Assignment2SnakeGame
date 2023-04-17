package essex.ac.uk;

import java.awt.*;

//Class for the snake body to be defined
public class SnakeBody extends SnakeHead {
    public SnakeBody(int X, int Y, int tileSize) {
        super(X, Y, tileSize);
    }

    //Defines the look of the body
    public void drawBody(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(X * Width , Y * Height , Width  , Height );
    }

    public int getX() { return X; }

    public void setX(int x) { X = x; }

    public int getY() { return Y; }

    public void setY(int y) { Y = y; }
}