package essex.ac.uk;

import java.awt.*;

//Class for the snake head to be defined
public class SnakeHead extends Sprites{
    public SnakeHead(int X, int Y, int tileSize) {
        this.X = X;
        this.Y = Y;
        Width = tileSize;
        Height = tileSize;
    }

    //Defines the look of the head
    public void drawHead(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(X * Width, Y * Height, Width, Height);
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
