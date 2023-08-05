package brickbreakerpc;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//KeyListener interface is used to detect any keyboard key pressing
//ActionListener interface is used to give an output action for a key event
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    //boolean play is false so that game does not start by itself
    private  boolean play=false;
    private int lives=5;
    private int score=0;
    // Using Random class to generate random integers
    Random random=new Random();
    int nr=random.nextInt(2,6);
    int nc=random.nextInt(10,25);
    private int totalBricks=nr*nc;
    //using Timer class to determine the time when a process has to be executed
    private Timer timer;
    private int delay=5;
    private int playerBarX=714;
    // Using Random class to generate random integers
    int n1=random.nextInt(250,1200);
    int n2=random.nextInt(300,750);
    int n3=random.nextInt(-5,5);
    int n4=random.nextInt(-6,-3);
    private int ballXPos=n1;
    private int ballYPos=n2;
    private int ballXDir=n3;
    private int ballYDir=n4;
    private  MapGen mapgen;

    //constructor for Gameplay class
    public Gameplay(){
        mapgen=new MapGen(nr,nc);
        //addKeyListener is used to add the key pressed as an event
        addKeyListener(this);
        //setFocusable is true to set focus of the panel towards this key event
        setFocusable(true);
        //setFocusTraversalKeysEnabled is false so that no traversal keys(TAB.SHIFT+TAB) can be used
        setFocusTraversalKeysEnabled(false);
        timer= new Timer(delay,this);
        timer.start();
    }
    //Graphics class to draw any components
    public void paint(Graphics g){

        //background

        g.setColor(Color.BLACK);
        g.fillRect(0,0,1650,1080);

        //map array or bricks
        mapgen.draw((Graphics2D)g);

        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,1080);
        g.fillRect(0,0,1650,3);
        g.fillRect(1527,0,3,1080);

        //playerBar
        g.setColor(Color.CYAN);
        g.fillRect(playerBarX,825,100,8);

        //ball
        g.setColor(Color.BLUE);
        g.fillOval(ballXPos,ballYPos,20,20);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("times new roman",Font.BOLD,25));
        g.drawString("SCORE-"+score,1380,30);

        //lives
        g.setColor(Color.white);
        g.setFont(new Font("Times new roman",Font.BOLD,25));
        g.drawString("LIVES-"+lives,20,30);

        //Special effect when ball collides with a brick
        A: for(int i=0;i<mapgen.map.length;i++){
            for(int j=0;j<mapgen.map[0].length;j++){
                if(mapgen.map[i][j]>0){
                    int brickX=j* mapgen.brickWidth+150;
                    int brickY=i* mapgen.brickHeight+50;
                    int brickWidth=mapgen.brickWidth;
                    int brickHeight= mapgen.brickHeight;

                    //rect is a rectangle with same coordinates and size
                    Rectangle brickRect= new Rectangle(brickX,brickY,brickWidth,brickHeight);
                    //ballRect is a rectangle around the ball to detect intersection between ball and brick rectangles
                    Rectangle ballRect= new Rectangle(ballXPos,ballYPos,20,20);

                    //To check intersection and delete the brick
                    if(ballRect.intersects (brickRect)){
                        g.setColor(Color.white);
                        g.fillRect(0,0,1650,1080);
                        g.setColor(Color.black);
                        break A;
                    }
                }
            }
        }
        //Condition of WINNING
        if(totalBricks<=0){
            play=false;
            ballXDir=0;
            ballYDir=0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("Times new roman",Font.BOLD,25));
            g.drawString("YOU WON       SCORE - "+score,610,400);
        }
        //Condition of GAME OVER and ball loss
        if(ballYPos>1100){
            play=false;
            if(lives>0) {
                lives--;
            }
            ballXDir=0;
            ballYDir=0;
            if(lives==0){
                g.setColor(Color.red);
                g.setFont(new Font("Times new roman",Font.BOLD,20));
                g.drawString("GAME OVER       SCORE - "+score,610,400);
                g.setColor(Color.yellow);
                g.drawString("Press 'ENTER' to restart",635,425);
            }
            if(lives>0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                liveRestart();
            }
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        //for the intersection of the bar and the ball
        if(play){
            if(new Rectangle(ballXPos,ballYPos,20,20).intersects(new Rectangle(playerBarX,825,100,8))){
                ballYDir= -ballYDir;
                if(ballXPos+10>=playerBarX+50)
                    ballXDir+=2;
                if(ballXPos+10<playerBarX+50)
                    ballXDir-=2;
            }
            //To detect the intersection of ball and the brick map
            A: for(int i=0;i<mapgen.map.length;i++){
                for(int j=0;j<mapgen.map[0].length;j++){
                    if(mapgen.map[i][j]>0){
                        int brickX=j* mapgen.brickWidth+150;
                        int brickY=i* mapgen.brickHeight+50;
                        int brickWidth=mapgen.brickWidth;
                        int brickHeight= mapgen.brickHeight;

                        //rect is a rectangle with same coordinates and size
                        Rectangle brickRect= new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        //ballRect is a rectangle around the ball to detect intersection between ball and brick rectangles
                        Rectangle ballRect= new Rectangle(ballXPos,ballYPos,20,20);


                        //To check intersection and delete the brick
                        if(ballRect.intersects (brickRect)){
                            String path = "C:\\Users\\HP\\IdeaProjects\\brickbreakerpc\\out\\production\\brickbreakerpc\\brickbreakerpc\\blaster-2-81267.wav" ;
                            playSound(path);
                            mapgen.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            //for left and right margins
                            if(ballXPos+19<=brickRect.x || ballXPos+1>=brickRect.x+brickRect.width){
                                ballXDir=-ballXDir;
                            }
                            else{
                                ballYDir=-ballYDir;
                            }
                        }
                    }
                }
            }

            //for the movement of the ball
            ballXPos+=(0.75)*ballXDir;
            ballYPos+=(0.75)*ballYDir;

            //when the ball hits the left border
            if(ballXPos<0){
                ballXDir=-ballXDir;
            }

            //when the ball hits the top border
            if(ballYPos<0){
                ballYDir=-ballYDir;
            }

            //when the ball hits the right border
            if(ballXPos>1510){
                ballXDir=-ballXDir;
            }
        }
        //when any change happens,repaint makes the components to repaint themselves
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    //to detect the pressing of the keys
    @Override
    public void keyPressed(KeyEvent e) {
        //for right arrow key to move the player bar
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerBarX>=1680){
                playerBarX=1680;
            }
            else{
                play=true;
                playerBarX+=30;
            }
        }

        //for the left arrow key to move the player bar
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerBarX<=10){
                playerBarX=10;
            }
            else{
                play=true;
                playerBarX-=30;
            }
        }

        //To detect ENTER key and restart game
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            if(lives==0){
                String path = "C:\\Users\\HP\\IdeaProjects\\brickbreakerpc\\out\\production\\brickbreakerpc\\brickbreakerpc\\Game Over Voice Sound Effect.wav";
                playSound(path);
            }
            if(totalBricks<=0){
                String path = " C:\\Users\\HP\\IdeaProjects\\brickbreakerpc\\out\\production\\brickbreakerpc\\brickbreakerpc\\You Win Voice Sound Effect.wav";
                playSound(path);
            }
        }
    }
    //Method to restart the game
    void restart(){
        if(!play){
            play=true;
            lives=5;
            nr=random.nextInt(2,6);
            nc=random.nextInt(10,25);
            totalBricks=nr*nc;
            n1=random.nextInt(250,1200);
            n2=random.nextInt(300,750);
            n3=random.nextInt(-5,5);
            n4=random.nextInt(-6,-3);
            ballXPos=n1;
            ballYPos=n2;
            ballXDir=n3;
            ballYDir=n4;
            playerBarX=714;
            score=0;
            //New Map generation
            mapgen=new MapGen(nr,nc);
            repaint();
        }
    }
    //Method to continue the game
    void liveRestart(){
        if(!play){
            play=true;
            n1=random.nextInt(250,1200);
            n2=random.nextInt(300,750);
            ballXPos=n1 ;
            ballYPos=n2;
            ballXDir=n3;
            ballYDir=n4;
            playerBarX=714;
        }
    }

    //Method to play any sound
    void playSound(String fileName){
        File file = new File(fileName);
        Clip clip;
        try {
            AudioInputStream as = AudioSystem.getAudioInputStream(file);
            clip= AudioSystem.getClip();
            clip.open(as);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }
}
