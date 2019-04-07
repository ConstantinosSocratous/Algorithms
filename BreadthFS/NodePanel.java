import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class NodePanel extends JPanel{

    private Node[][] nodes;
    public NodePanel(Node[][] nodes){
        this.nodes = nodes;

        addMouseListener(new MouseAdapter() { 
            public void mousePressed(MouseEvent me) { 
                int x=me.getX();
                int y=me.getY();
                changeStatus(getNodeFromPosition(x,y));
            } 
          }); 

    }

    @Override public void paintComponent(Graphics g) {
        int size = 35;
        for(int i=0; i< nodes.length; i++){
            for(int j=0; j<nodes.length; j++){
               g.setColor(getColor(nodes[i][j].getStatus()));
               g.fillRect(size*j, size*i, size, size);
               g.setColor(Color.BLACK);
               g.drawRect(size*j, size*i, size, size);
            //    r+=30;
            //    c+=30;
            }
        }

    }

    private Color getColor(int status){
        if(status == -1)   return Color.BLACK;  //OBJECT
        if(status == 0)    return Color.WHITE;  //NOTHING
        if(status == 1)    return Color.GREEN;  //SUCC
        if(status == 2)    return Color.BLUE;  //START POSITION
        if(status == 3)    return Color.RED;  //END POSITION
        if(status == 4)    return Color.LIGHT_GRAY;  //SEEN
        if(status == 5)    return Color.GRAY;  //EXPLORED
        if(status == 6)    return Color.ORANGE;  //Current
        
        else return Color.WHITE;
    }

    public void paintAgain(){
        this.repaint();
    }

    private Node getNodeFromPosition(int c, int r){
        Node found = null;
        for(int row=0; row< nodes.length; row++){
            for(int col=0; col<nodes.length; col++){
                Node curr = nodes[row][col];
                if((c <= curr.xPos + 35) && (c >= curr.xPos) && (r <= curr.yPos+35 )&& (r >= curr.yPos ) ){
                    found = curr;
                    break;
                }
            }
        }
        return found;
    }

    private void changeStatus(Node node){
        if(node == null) return;
        if(node.getStatus() == 0){
            node.setStatus(-1);
        }else if(node.getStatus() == -1){
            node.setStatus(0);
        }

        repaint();
    }
}