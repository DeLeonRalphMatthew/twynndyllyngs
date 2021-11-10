public class Queueueue {
    private Node head;
    private Node tail;
    private int size = 0;

    private static class Node{
        String id;
        Node next;

        Node(String id){
            this.id = id;
        }
    }

    // only handful of applications
    public void enqueue(String id){
        if(head == null){
            head = new Node(id);
            tail = head;
        }else{
            tail.next = new Node(id);
            tail = tail.next;
        }

        size++;
    }

    public String dequeue(){
        if(size == 0) // will unlikely to run
            throw new NullPointerException("Queue is empty");

        String id = head.id;

        if(size == 1)
            head = null;
        else
            head = head.next;

        size--;
        return id;
    }

    public boolean isEmpty(){
        return size == 0;
    }
}


