import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque <Item> implements Iterable <Item> {

    private Node first, last;
    private int size;
    
    private class Node{
        
        private Item item;
        private Node previous;
        private Node next;
        
    }
    
    public Deque(){
    
        first= null;
        last= null;
        size= 0;
}
    
    public boolean isEmpty(){
        
        return first == null || last == null ;
        
    }
    
    public void addFirst(Item item){
        
        if(item == null)
            throw new NullPointerException();
        
        Node oldFirst= first;
        first= new Node();
        first.item= item;
        first.next= oldFirst;
        first.previous= null;
        
        if(isEmpty())
            last= first;
        else
            oldFirst.previous= first;
        
        size++;
    }
    public void addLast(Item item){
        
        if(item == null)
            throw new NullPointerException();
        
        Node oldLast= last;
        last= new Node();
        last.item= item;
        last.next= null;
        last.previous= oldLast;
        
        if(isEmpty())
            first= last;
        else
            oldLast.next= last;
        
        size++;
    }
    
    public Item removeFirst(){
        
        if(isEmpty())
            throw new NoSuchElementException();
        
        Item item= first.item;
        first= first.next;
        
        
        if(isEmpty())
            last= null;
        
        else
            first.previous= null;
 
        size--;
        return item;
        
    }
    
    public Item removeLast(){
        
        if(isEmpty())
            throw new NoSuchElementException();
        
        Item item= last.item;
        last= last.previous;
        
        if(isEmpty())
            first= null;
        else
            last.next= null;
        
        size--;
        return item;
    }
    
    public int size(){
        return size;
    }
    
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator <Item>{
        
        private Node current= first;
        
        public boolean hasNext(){
            return current!=null;
        }
        
        public Item next(){
            
          if(!hasNext())
              throw new NoSuchElementException();
                  
            Item item= current.item;
            current= current.next;
            return item;
        }
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    
}
