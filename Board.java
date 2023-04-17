package essex.ac.uk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import static java.lang.String.format;

//Class acts as the board of the game
public class Board extends JPanel implements Runnable {
    private static final int Width = 500;
    private static final int Height = 500;
    private Thread thread;
    private boolean GameOn = false;

    private SnakeBody B;
    private ArrayList<SnakeBody> Snake;

    private Apple A;
    private ArrayList<Apple> Apples;
    private Random rand;

    private int X, Y;
    private int size;
    private int score;
    private int hiScore = 0;

    private boolean right;
    private boolean left, up, down;

    private int ticks = 0;

    private key key;

    public Board() {
        init();
    }

    //Constructor that sets up the game
    public void init() {
        X = 10;
        Y = 10;
        size = 5;
        score = 0;
        right = true;
        left = false;
        up = false;
        down = false;

        //Reads from Score to get the previous hi scores
        try {
            File x = new File("src/resources/Scores.txt");
            Scanner sc = new Scanner(x);

            while(sc.hasNext()) {
                hiScore = Integer.parseInt(sc.next());
            }

            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        setFocusable(true);
        addKeyListener(new key());

        setPreferredSize(new Dimension(Width, Height));

        Snake = new ArrayList<>();
        Apples = new ArrayList<>();
        rand = new Random();

        start();
    }

    //Starts the game and thread timer
    public void start() {
        GameOn = true;
        thread = new Thread(this, "");
        thread.start();
    }

    //Creates the snake, apple, and scores, and also the game over message
    public void paint(Graphics g) {
        if (GameOn) {
            g.clearRect(0, 0, Width, Height);

            //Creates scores
            int h = getHeight();
            g.setColor(getForeground());
            String s = format("Hi-Score: %d    Score: %d", hiScore, score);
            g.drawString(s, 30, h - 30);

            //Creates snake
            for (int i = 0; i < Snake.size(); i++) {
                if (i == Snake.size()-1) {
                    Snake.get(i).drawHead(g);
                } else {
                    Snake.get(i).drawBody(g);
                }
            }

            //Creates apple
            for (int i = 0; i < Apples.size(); i++) {
                Apples.get(i).draw(g);
            }

        } else {
            //rCreates the game over message
            String msg = "Game Over";
            String msg2 = "Press Enter to restart";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = getFontMetrics(small);

            g.setColor(Color.BLACK);
            g.setFont(small);
            g.drawString(msg, (Width - metr.stringWidth(msg)) / 2, Height / 2);
            g.drawString(msg2, (Width - metr.stringWidth(msg2)) / 2, Height / 2 + 20);

        }
    }

    //Carrys out every tick of the thread timer
    public void tick() {
        //Adds snake to the board
        if (Snake.size() == 0){
            B = new SnakeBody(X, Y, 10);
            Snake.add(B);
        }

        //Adds apple to the board
        if (Apples.size() == 0) {
            int x = rand.nextInt(49);
            int y = rand.nextInt(49);

            A = new Apple(x, y, 10);
            Apples.add(A);
        }

        //When the apple is eaten, it grows the snake
        for (int i=0; i < Apples.size(); i++) {
            if (X == Apples.get(i).getX() && Y == Apples.get(i).getY()) {
                score++;
                size++;
                Apples.remove(i);
                i--;
            }
        }

        //If the snake hits itself, ends the game
        for (int i=0; i < Snake.size(); i++) {
            if (X == Snake.get(i).getX() && Y == Snake.get(i).getY()) {
                if (i != Snake.size() - 1) {
                    end();
                }
            }
        }

        //If the snake hits the wall, ends the game
        if (X < 0 || X > 49 || Y < 0 || Y > 49) {
            end();
        }

        ticks++;

        //Resets the tick count to control the speed of the snake
        if (ticks > 1000000) {
            if (right) X++;
            if (left) X--;
            if (up) Y--;
            if (down) Y++;

            ticks = 0;

            B = new SnakeBody(X, Y, 10);
            Snake.add(B);

            if (Snake.size() > size) {
                Snake.remove(0);
            }
        }
    }

    //Ends the game
    public void end() {
        GameOn = false;

        Snake.clear();
        Apples.clear();

        //Stores users score if its higher than the current high score
        if (score > hiScore) {
            try {
                FileWriter Wr = new FileWriter("src/resources/Scores.txt");
                PrintWriter Pwr = new PrintWriter(Wr);
                Pwr.print(score);
                Pwr.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        //Ends the thread
        if (thread != null) {
            Thread tmp = thread;
            thread = null;
            tmp.interrupt();
        }
    }

    //Runs the game
    public void run() {
        while(GameOn) {
            tick();
            repaint();
        }
    }

    //Where the key actions are defined
    private class key extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_ENTER) && (!GameOn)) {
                init();
            }
        }
    }
}
