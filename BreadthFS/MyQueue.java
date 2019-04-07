import java.util.*;

public class MyQueue{

    private Node[] queue;
    private int front;
    private int rear;

    public MyQueue(int size){
        queue = new Node[size];
        rear = 0;
        front = 0;
    }

    public void add(Node node){
        if(rear == queue.length){
            rear = 0;
        }
        queue[rear] = node;
        rear++;
        //System.out.println("Added " + node.temp);
    }

    public Node get(){
        if(getSize() == 0 ) return null;
        Node temp = queue[front];

        queue[front] = null;
        front++;
        if(front >= queue.length){
            front = 0;
        }
        //System.out.println("Poped " + temp.temp);
        return temp;
    }

    public int getSize(){
        return rear - front;
    }

    public Boolean isEmpty(){
        if(getSize() == 0) return true;
        else return false;
    }


    // public static void main(String[] args){
    //     Queue queue = new Queue(30);

    //     queue.addNode(new Node("One"));
    //     queue.addNode(new Node("Two"));
    //     queue.addNode(new Node("Three"));
    //     queue.addNode(new Node("Four"));
    //     System.out.println("Size: " + queue.getSize());
    //     queue.getNode();
    //     queue.addNode(new Node("Five"));
    //     queue.getNode();
    //     System.out.println("Size: " + queue.getSize());
    //     queue.getNode();
        

    // }
}
