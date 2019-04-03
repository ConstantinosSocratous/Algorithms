import javax.swing.*;
import java.awt.*;

public class Node{

    private int numStatus;
    private int fScore=0;
    public int gScore=0,hScore;
    private int row,col;
    private Node parent = null;
    public int xPos,yPos;
    public Node(int st, int r, int c, int xPos, int yPos){
        row = r;
        col = c;
        this.numStatus = st;

        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getStatus(){
        return numStatus;
    }
    public void setStatus(int num){
        this.numStatus = num;
        //this.status.setText("" + num);
    }

    public int getRow(){return row;}
    public int getCol(){return col;}

    public int getFScore(){return fScore;}

    public void setFScore(int n){
        fScore = n;
    }

    public Node getParent(){return parent;}
    public void setParent(Node n){
        parent = n;
    }

    public static int getDistance(Node a, Node b){
        if(a == null) return 0;
        if(b == null) return 0;
        int rDif = Math.abs(a.getRow()-b.getRow());
        int cDif = Math.abs(a.getCol()-b.getCol());
        return rDif + cDif;
    }
}