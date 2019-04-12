import javax.swing.*;

import java.awt.*;
import java.util.*;
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

    private int startRow=20, startCol=3 , endRow=2, endCol=23;

    public static void main(String[] args){
        new Frame();
    }

    public Frame(){
        init();
    }

    private void init(){
        setResizable(false);
        this.setTitle("PATH FINDER USING A*");
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



        // nodes[0][10].setStatus(-1);
        // nodes[1][10].setStatus(-1);
        // nodes[2][10].setStatus(-1);
        // nodes[3][10].setStatus(-1);
        // nodes[4][10].setStatus(-1);
        // nodes[5][10].setStatus(-1);
        // nodes[7][10].setStatus(-1);
        // nodes[8][10].setStatus(-1);
        // nodes[9][10].setStatus(-1);
        // nodes[10][9].setStatus(-1);

        // nodes[10][7].setStatus(-1);
        // nodes[10][6].setStatus(-1);
        // nodes[10][5].setStatus(-1);
        // nodes[10][4].setStatus(-1);
        // nodes[10][3].setStatus(-1);

        // node s[10][2].setStatus(-1);
        // nodes[10][2].setStatus(-1);
        // nodes[9][15].setStatus(-1);
        // nodes[10][15].setStatus(-1);


        // search(nodes[startRow][startCol], nodes[endRow][endCol]);
        createRandom();
    }

    private void createRandom(){
        Random rand = new Random();

        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                int temp = rand.nextInt(5)+1;
                if(temp == 3){
                    nodes[i][j].setStatus(-1);
                }
            }
        }

        nodes[startRow][startCol].setStatus(2);
        nodes[endRow][endCol].setStatus(3);

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
        
        calculateGScores(start,goal);

        //start.setStatus(2); 
        //goal.setStatus(3);
        //nodePanel.paintAgain();
        
        //CLOSE LIST
        HashSet<Node> explored = new HashSet<Node>();
        
        //OPEN LIST
        PriorityQueue<Node> queue = new PriorityQueue<Node>(GRID_SIZE*GRID_SIZE,new Comparator<Node>(){
            public int compare(Node a, Node b){
                if(a.getFScore() > b.getFScore()){
                    return 1;
                }else {
                    return -1;
                }
            }
        });

        queue.add(start);
        boolean found = false;
        int counter=0;

        while((!queue.isEmpty())&&(!found)){
            
            Node current = queue.poll();
            while(explored.contains(current)){
                current = queue.poll();
            }

            counter++;
            if(!current.equals(start)){
                current.setStatus(6);
            }
            nodePanel.repaint();

            explored.add(current);
            
            try{
                nodePanel.repaint();
                Thread.sleep(60);
                
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
            
                //int gScore = Node.getDistance(start, current);
                int hScore = Node.getDistance(current, n);
                int cost = current.gScore + hScore;
                
                
                if(explored.contains(n))
                continue;


                if( (n.gScore != 0 ) || cost < n.gScore ){
                    n.gScore = cost;
                    n.setFScore(cost + Node.getDistance(goal, n));
                    if(!explored.contains(n))
                        n.setParent(current);
                    queue.add(n);
                }else{
                    
                }

                n.setStatus(4);


                nodePanel.repaint();

            
                goal.setStatus(3);
                start.setStatus(2);
            }
            
            current.setStatus(5);
        }
        
        goal.setStatus(3);
        start.setStatus(2);
        System.out.println("Counter " + counter);

        showPath(start,goal);

        if(!found)  System.out.println("No solution found");
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
            //System.out.println(node.getRow()+","+node.getCol());
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