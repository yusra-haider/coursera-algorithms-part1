
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;      // the randomized queue
    private int N;              // represents the number of elements in the randomized queue. Also used as the pointer to enque

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        N = 0;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (N == items.length) {
            resize(items.length * 2);
        }
        items[N] = item;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(0, N);
        Item item = items[i];
        items[i] = items[N - 1];    //swapping i, and N-1
        items[N - 1] = null;        // making the last element, N-1, null
        N--;                      // decreasing N to N-1, so that it points to closest null
        if (N > 0 && N == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(0, N)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int currentIndex;
        private Item[] temp;

        public RandomizedQueueIterator() {
            if (!isEmpty()) {
                temp = (Item[]) new Object[N];
                for (int i = 0; i < N; i++) {
                    temp[i] = items[i];
                }
                StdRandom.shuffle(temp, 0, N - 1);
                currentIndex = 0;
            }
        }

        public boolean hasNext() {
            return currentIndex < N;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = temp[currentIndex];
            currentIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
