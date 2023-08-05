package brickbreakerpc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {
    public static void main(String[] args) {
        //BEGIN screen
        JFrame frame=new JFrame("Breakout Ball Game");
        frame.setBounds(0,0,1650,1080);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Button to begin the game
        Button b =new Button("BEGIN");
        b.setFont(new Font("Times new roman", Font.BOLD,25));
        b.setBounds(825,540,25,25);
        frame.add(b);

        //Mouse listener to detect the mouse click event
        b.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //to detect left mouse click
                if(e.getButton()==MouseEvent.BUTTON1){
                    // New Gameplay frame
                    JFrame frame=new JFrame("Breakout ball game");
                    Gameplay gameplay=new Gameplay();
                    frame.setBounds(0,0,1650,1080);
                    frame.setTitle("Breakout Ball Game");
                    frame.setResizable(false);
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(gameplay);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }
}


