import javax.swing.*;

import java.awt.*;
import java.util.*;
import java.util.Queue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Frame extends JFrame{

    private final int GRID_SIZE = 25;
    private JPanel grid;
    private Node[][] nodes;
    private NodePanel nodePanel;

    private int startRow=3, startCol=3 , endRow=21, endCol=20;

    public static void main(String[] args){
        new Frame();
    }

    public Frame(){
        init();
    }

    private void init(){
        setResizable(false);
        this.setTitle("PATH FINDER USING BREADTH FIRST SEARCH");
        this.getContentPane().setPreferredSize(new Dimension(1015,880));

        JPanel main = new JPanel(new BorderLayout());

        nodes = new Node[GRID_SIZE][GRID_SIZE];

        createGrid();
        
        nodePanel = new NodePanel(nodes);
        main.add(nodePanel, BorderLayout.CENTER);

    
        JButton btn = new JButton("Search");
        btn.addActionListener(e -> {
            search(nodes[startRow][startCol], nodes[endRow][endCol]);
            btn.setEnabled(false);
        });

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            reset();
            btn.setEnabled(true);
        });
        westPanel.add(resetBtn);

        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            clear();
            btn.setEnabled(true);
        });
        westPanel.add(clear);

        main.add(westPanel, BorderLayout.WEST);
        main.add(btn, BorderLayout.EAST);
        this.getContentPane().add(main);
    
        pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        nodes[0][10].setStatus(-1);
        nodes[1][10].setStatus(-1);
        nodes[2][10].setStatus(-1);
        nodes[3][10].setStatus(-1);
        nodes[4][10].setStatus(-1);
        nodes[5][10].setStatus(-1);
        nodes[7][10].setStatus(-1);
        nodes[8][10].setStatus(-1);
        nodes[9][10].setStatus(-1);
        nodes[10][9].setStatus(-1);

        nodes[10][7].setStatus(-1);
        nodes[10][6].setStatus(-1);
        nodes[10][5].setStatus(-1);
        nodes[10][4].setStatus(-1);
        nodes[10][3].setStatus(-1);

        nodes[10][2].setStatus(-1);
        nodes[10][2].setStatus(-1);
        nodes[9][15].setStatus(-1);
        nodes[10][15].setStatus(-1);


        search(nodes[startRow][startCol], nodes[endRow][endCol]);
    }

    private void createGrid(){
        int size = 35;
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                Node node = new Node(0,i,j, j*size, i*size);
                nodes[i][j] = node;
            }
        }
        nodes[startRow][startCol].setStatus(2); 
        nodes[endRow][endCol].setStatus(3);
    }   

    private void clear(){
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                nodes[i][j].setStatus(0);
            }
        }
        nodes[startRow][startCol].setStatus(2); 
        nodes[endRow][endCol].setStatus(3);
        nodePanel.paintAgain();
    }

    private void reset(){
        int size = 35;
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                if(nodes[i][j].getStatus() == 1 || nodes[i][j].getStatus() == 4 || nodes[i][j].getStatus() == 5)
                    nodes[i][j].setStatus(0);
            }
        }
        nodes[startRow][startCol].setStatus(2); 
        nodes[endRow][endCol].setStatus(3);
        nodePanel.paintAgain();
    }   

    private ArrayList<Node> getAdjacents(Node node){
        ArrayList<Node> adjacents = new ArrayList<Node>();

        // if(isPositionValid(node.getRow()-1, node.getCol()-1)){
        //     adjacents.add(nodes[node.getRow()-1][node.getCol()-1]);
        // }
        // if(isPositionValid(node.getRow()-1, node.getCol()+1)){
        //     adjacents.add(nodes[node.getRow()-1][node.getCol()+1]);
        // }
        // if(isPositionValid(node.getRow()+1, node.getCol()-1)){
        //     adjacents.add(nodes[node.getRow()+1][node.getCol()-1]);
        // }
        // if(isPositionValid(node.getRow()+1, node.getCol()+1)){
        //     adjacents.add(nodes[node.getRow()+1][node.getCol()+1]);
        // }
        if(isPositionValid(node.getRow()-1, node.getCol())){
            adjacents.add(nodes[node.getRow()-1][node.getCol()]);
        }
        if(isPositionValid(node.getRow(), node.getCol()+1)){
            adjacents.add(nodes[node.getRow()][node.getCol()+1]);
        }
        if(isPositionValid(node.getRow()+1, node.getCol())){
            adjacents.add(nodes[node.getRow()+1][node.getCol()]);
        }
        if(isPositionValid(node.getRow(), node.getCol()-1)){
            adjacents.add(nodes[node.getRow()][node.getCol()-1]);
        }

        return adjacents;
    }

    private boolean isPositionValid(int r, int c){
        if(r < GRID_SIZE && c < GRID_SIZE && r>=0 && c>=0){
            if(nodes[r][c].getStatus() != -1){
                return true;
            }
            return false;
        }
        else return false;
    }

    private void search(Node start, Node goal){
        

        HashSet<Node> explored = new HashSet<Node>();
        
        MyQueue queue = new MyQueue(GRID_SIZE*GRID_SIZE*GRID_SIZE);

        queue.add(start);
        boolean found = false;
        int counter=0;

        while((!queue.isEmpty())&&(!found)){
            
            Node current = queue.get();
            while(explored.contains(current)){
                current = queue.get();
            }

            counter++;
            if(!current.equals(start)){
                current.setStatus(6);
            }

            explored.add(current);
            
            try{
               Thread.sleep(50);
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
            //goal found
            if(current.equals(goal)){
                System.out.println("Found");
                found = true;
                break;
            }

            //check every child of current node
            for(Node n : getAdjacents(current)){
            
                if(explored.contains(n))
                    continue;

                queue.add(n);
                n.setParent(current);
            
                
                n.setStatus(4);
                goal.setStatus(3);
                start.setStatus(2);
                
            }

            if(queue.isEmpty()){
                System.out.println("EMPTY");
            }
            
            current.setStatus(5);

            nodePanel.repaint();
        }
        
        goal.setStatus(3);
        start.setStatus(2);
        System.out.println("Counter " + counter);

        showPath(start,goal);
        //printPath(start, goal);

    }

    private void printPath(Node start, Node goal){
        Node curr = goal;
        //Node curr2 = curr.getParent();

        for(int i=0; i<5; i++){
            System.out.println(curr.getParent().getRow()+","+curr.getParent().getCol());
            curr = curr.getParent();
        }
    }

    private void showPath(Node start, Node goal){
        ArrayList<Node> ans = new ArrayList<>();

        Node curr = goal;
        
        //System.out.println(curr.getRow()+","+curr.getCol());
        while(curr != null){
            if(curr.getParent() == null){
                break;
            }else{
                ans.add(curr);
                //System.out.println(curr.getParent().getRow()+","+curr.getParent().getCol());
                curr = curr.getParent();
            }  
        }
        ans.add(start);

        Collections.reverse(ans);
        for(Node node: ans){
            node.setStatus(1);
            System.out.println(node.getRow()+","+node.getCol());
        }

        goal.setStatus(3);
        start.setStatus(2);
        nodePanel.paintAgain();
    }

    private void calculateGScores(Node start, Node end){
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                nodes[i][j].gScore = Node.getDistance(nodes[i][j], start);
            }
        }
    }

}