package essex.ac.uk;

import java.awt.*;

//Class for the apple to be defined
public class Apple extends Sprites{
    public Apple(int X, int Y, int tileSize) {
        this.X = X;
        this.Y = Y;
        Width = tileSize;
        Height = tileSize;
    }

    //Defines the look of the apple
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(X * Width, Y * Height, Width, Height);
    }

    public int getX() { return X; }

    public void setX(int x) { X = x; }

    public int getY() { return Y; }

    public void setY(int y) { Y = y; }
}
