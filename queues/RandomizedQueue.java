import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] items;
	private int size;

	// construct an empty randomized queue
	public RandomizedQueue() {
		items = (Item[]) new Object[1];
		size = 0;
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return size;
	}

	// resize the underlying array holding the elements
	private void resize(int capacity) {
		assert capacity >= size;
		Item[] temp = (Item[]) new Object[capacity];
		if (size >= 0) System.arraycopy(items, 0, temp, 0, size);
		items = temp;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		if (size == items.length) {
			resize(2 * items.length);
		}
		items[size++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("RandomizedQueue is empty");
		}
		int index = StdRandom.uniform(size);
		Item item = items[index];
		items[index] = items[--size];
		items[size] = null;
		if (size > 0 && size == items.length / 4) {
			resize(items.length / 2);
		}
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("RandomizedQueue is empty");
		}
		int index = StdRandom.uniform(size);
		return items[index];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		private final Item[] shuffledItems;
		private int index;

		public RandomizedQueueIterator() {
			shuffledItems = (Item[]) new Object[size];
			System.arraycopy(items, 0, shuffledItems, 0, size);
			StdRandom.shuffle(shuffledItems);
			index = 0;
		}

		public boolean hasNext() {
			return index < size;
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more items to return");
			}
			return shuffledItems[index++];
		}

		public void remove() {
			throw new UnsupportedOperationException("Remove operation is not supported");
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		// Test constructor
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		assert rq.isEmpty();
		assert rq.size() == 0;

		// Test enqueue
		rq.enqueue(1);
		rq.enqueue(2);
		rq.enqueue(3);
		assert !rq.isEmpty();
		assert rq.size() == 3;

		// Test dequeue
		int first = rq.dequeue();
		int second = rq.dequeue();
		int third = rq.dequeue();
		assert (first == 1 && second == 2 && third == 3);
		assert rq.isEmpty();
		assert rq.size() == 0;

		// Test sample
		rq.enqueue(4);
		rq.enqueue(5);
		rq.enqueue(6);
		int sample1 = rq.sample();
		int sample2 = rq.sample();
		int sample3 = rq.sample();
		assert !rq.isEmpty();
		assert rq.size() == 3;
		assert (sample1 == 4 || sample1 == 5 || sample1 == 6);
		assert (sample2 == 4 || sample2 == 5 || sample2 == 6);
		assert (sample3 == 4 || sample3 == 5 || sample3 == 6);

		// Test iterator
		RandomizedQueue<Integer> rq2 = new RandomizedQueue<Integer>();
		for (int i = 0; i < 10; i++) {
			rq2.enqueue(i);
		}
		Iterator<Integer> it1 = rq2.iterator();
		Iterator<Integer> it2 = rq2.iterator();
		while (it1.hasNext()) {
			int item1 = it1.next();
			int item2 = it2.next();
			assert item1 == item2;
		}
	}
}
