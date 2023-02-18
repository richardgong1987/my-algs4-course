import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int size;

	private class Node {
		private Item item;
		private Node next;
		private Node prev;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Cannot add null item to deque.");
		}
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		if (isEmpty()) {
			last = first;
		} else {
			oldFirst.prev = first;
		}
		size++;
	}

	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Cannot add null item to deque.");
		}
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.prev = oldLast;
		if (isEmpty()) {
			first = last;
		} else {
			oldLast.next = last;
		}
		size++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException("Deque is empty.");
		}
		Item item = first.item;
		first = first.next;
		size--;
		if (isEmpty()) {
			last = null;
		} else {
			first.prev = null;
		}
		return item;
	}

	// remove and return the item from the back
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException("Deque is empty.");
		}
		Item item = last.item;
		last = last.prev;
		size--;
		if (isEmpty()) {
			first = null;
		} else {
			last.next = null;
		}
		return item;
	}

	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	// iterator class
	private class DequeIterator implements Iterator<Item> {

		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException("Cannot remove items from iterator.");
		}

		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more items in deque.");
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<>();

		System.out.println("Adding elements to front...");
		deque.addFirst(2);
		deque.addFirst(1);
		deque.addFirst(0);

		System.out.println("Deque contains " + deque.size() + " elements.");
		System.out.println("Contents: " + deque);

		System.out.println("Adding elements to back...");
		deque.addLast(3);
		deque.addLast(4);
		deque.addLast(5);

		System.out.println("Deque contains " + deque.size() + " elements.");
		System.out.println("Contents: " + deque);

		System.out.println("Removing elements from front...");
		System.out.println(deque.removeFirst());
		System.out.println(deque.removeFirst());
		System.out.println(deque.removeFirst());

		System.out.println("Deque contains " + deque.size() + " elements.");
		System.out.println("Contents: " + deque);

		System.out.println("Removing elements from back...");
		System.out.println(deque.removeLast());
		System.out.println(deque.removeLast());
		System.out.println(deque.removeLast());

		System.out.println("Deque contains " + deque.size() + " elements.");
		System.out.println("Contents: " + deque);

		System.out.println("Adding null to front...");
		try {
			deque.addFirst(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Caught exception: " + e.getMessage());
		}

		System.out.println("Adding null to back...");
		try {
			deque.addLast(null);
		} catch (IllegalArgumentException e) {
			System.out.println("Caught exception: " + e.getMessage());
		}

		System.out.println("Removing from empty deque...");
		Deque<String> emptyDeque = new Deque<>();
		try {
			emptyDeque.removeFirst();
		} catch (NoSuchElementException e) {
			System.out.println("Caught exception: " + e.getMessage());
		}
		try {
			emptyDeque.removeLast();
		} catch (NoSuchElementException e) {
			System.out.println("Caught exception: " + e.getMessage());
		}

		System.out.println("Iterating over deque...");
		for (int i : deque) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

}