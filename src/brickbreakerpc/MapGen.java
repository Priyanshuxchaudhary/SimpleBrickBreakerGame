package brickbreakerpc;

import java.awt.*;

public class MapGen {
    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public MapGen(int row,int col){
        map=new int [row][col];
        //map.length to get the size of the array
        for(int i=0;i<map.length;i++){
            //map[0].length to execute for no of columns
            for(int j=0;j<map[0].length;j++){
                //logic 1 to detect that a brick is not hit by the ball
                map[i][j]=1;
            }
        }
        brickWidth=1250/col;
        brickHeight=240/row;
    }
    //Method to display bricks on the panel
    public void draw(Graphics2D g){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]>0){
                    //Random integers assigned for colour
                    int R = (int)(Math.random()*256);
                    int G = (int)(Math.random()*256);
                    int B= (int)(Math.random()*256);
                    Color color = new Color(R, G, B);
                    //bricks size and colour
                    g.setColor(color);
                    g.fillRect(j * brickWidth + 150, i * brickHeight + 50, brickWidth, brickHeight);
                    g.setStroke(new BasicStroke(10));
                    g.setColor((Color.black));
                    g.drawRect(j * brickWidth + 150,i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }
    public void setBrickValue(int value,int row,int col) {
        map[row][col]=value;
    }
}
