package essex.ac.uk;

import javax.swing.*;

//Class creates basic JFrame and starts the whole game
public class Main extends JFrame {
    //Creates the basics for the JFrame and starts on the Board class
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("1803524");
        setResizable(false);

        add(new Board());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Creates a new Main
    public static void main(String[] args) {
        new Main();
    }
}