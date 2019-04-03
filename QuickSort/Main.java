import java.util.*;

public class Main{

    public static void main(String[] args){
        new Main(10);
    }

    private Integer[] array;
    private int size;

    public Main(int size){
        this.size = size;
        array = new Integer[size];
        fillList();

        printList();

        quickSort(array, 0, array.length-1);

        printList();
    }

    private void printList(){
        for(int a: array){
            System.out.print(a + "  ");
        }
        System.out.println("");
    }

    private void fillList(){
        Random rand = new Random();
        for(int i=0; i<array.length; i++){
            array[i] = rand.nextInt(50);
        }

    }

    //INCREASING ORDER
    private void quickSort(Integer[] arr, int low, int high){
        if (low < high){
            /* pi is partitioning index, arr[pi] is now
            at right place */
            //pi = partition(arr, low, high);
            int pi = partition(low, high);

            quickSort(arr, low, pi - 1);  // Before pi
            quickSort(arr, pi + 1, high); // After pi
        }
    }   

    private int partition(int low, int high){
        
        int index = (low+high)/2;
        swap(index, high);

        int i = low;

        for(int j=low; j<high; j++){
            if(array[j] <= array[high]){
                swap(i,j);
                i++;
            }
        }

        swap(i, high);

        return i;


    }

    private void swap(int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}