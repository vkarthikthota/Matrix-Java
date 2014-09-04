public class List {
	
	private class Node {
		// Fields
		Object data;
		Node next;
		Node prev;

		// Constructor
		Node(Object newData) {
			this.data = newData;
			next = null;
			prev = null;
		}

	}

	// Fields
	private Node front;
	private Node back;
	private Node current;
	private int length;
	private int index;

	// Constructor - a new empty List
	List() {
		front = null;
		back = null;
		current = null;
		length = 0;
		index = -1;
	}

	int length() {
		return length;
	}

	int getIndex() {
		return index;
	}

	boolean isEmpty() {
		if(length == 0){
			return true;
		}
		else {
			return false;
		}
	}

	boolean offEnd() {
		if(index == -1) {
			return true;
		}
		else {
			return false;
		}
	}

	Object front() {
		if(isEmpty()) {
			throw new RuntimeException("List Error: front() called on empty List.");
		}
		return front.data;
	}

	Object back() {
		if(isEmpty()) {
			throw new RuntimeException("List Error: back() called on empty List.");
		}
		return back.data;
	}

	Object getElement() {
		if(isEmpty()) {
			throw new RuntimeException("List Error: getElement() called on empty List.");
		}
		else if(offEnd()) {
			throw new RuntimeException("List Error: getElement() called on null pointer.");
		}
		return current.data;
	}

	boolean equals (List L) {
		if(this.length() == L.length()) {
			boolean flag = true;
			Node curr = this.front;
			Node tempCurr = L.front;

			while((curr.next != null) && flag) {
				flag = (curr.data == tempCurr.data);
				curr = curr.next;
				tempCurr = tempCurr.next;
			}
			return flag;
		else {
			return false;
		}

		}
	}

	void clear() {
		length = 0;
		index = -1
		front = back = current = null;
	}

	void moveTo(int i) {
		if(isEmpty()) {
			throw new RuntimeException("List Error: moveTo() called on empty List.");
		}
		else {
			current = front;
			for(int j = 0; j < i; j++) {
				moveNext();
			}
		}
	}

	void movePrev() {
		if(isEmpty()) {
			throw new RuntimeException("List Error: movePrev() called on empty List.");
		}
		else if(offEnd()) {
			throw nee RuntimeException("List Error: movePrev() called on null pointer.");
		}
		else {
			current = current.prev;
		}

	}






}
