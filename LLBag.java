import java.util.*;

public class LLBag implements Bag {
	
	/*
	 * An implementation of a bag data structure using an array.
	 */
	
	
		private class Node {
			
			// class for a Node object
			
			private Object item;
			private Node next;
        
			private Node(Object i, Node n) {
				item = i;
				next = n;
			}
		}
	
	
	private int numItems;
	private Node head;
	
	
	public LLBag() {
		
		// creates a Bag that is empty
		
		head = new Node(null,null);
		numItems = 0;
		
	}
	
	/*
     * Constructs an LLList object containing the items in the specified array
     */
    public LLBag(Object[] initItems) {
        head = new Node(null, null);
        
        Node prevNode = head;
        for (int i = 0; i < initItems.length; i++) {
            Node nextNode = new Node(initItems[i], null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }
        
        numItems = initItems.length;
    }
		
		
	
	public boolean add(Object item) {
		
		// adds the specified item to the bag
		
		Node newNode = new Node(item,null);
		newNode.next = head.next;
		head.next = newNode;
		numItems++;
		
		return true;
	}

	
	public boolean remove(Object item) {
		
		// removes the specified item from the bag
		
		Node trav = head;
	
		while (trav != null) {
			
			if (trav.next.item == item) {
				
				trav.next = trav.next.next;
				numItems--;
				return true;
				
			}
			
			
			trav = trav.next;
			
		}
		
		
		
		return false;
	}

	
	public boolean contains(Object item) {
		
		// returns true if item is found in the bag, and false otherwise
		
		Node trav = head;
		
		while (trav != null) {	
			
			if (trav.next == null) 
				return false;
			
			if (trav.next.item == item) {
				return true;
			}
			trav = trav.next;
			
		}
		
		return false;
		
	}

	
	public int numItems() {
		return numItems;
	}

	
	public Object grab() {
		
		// grabs a random item from the LLBag
		
		int i = (int)(Math.random() * numItems);
		int counter = 0;
		
		Node trav = head.next;
		
		while (trav != null) {
			
			if (counter == i) {
		
				return trav.item;
				
			}
		
			trav = trav.next;
			counter++;
		}
		
		return null;
	}

	
	public Object[] toArray() {
		
		// returns an array representation of the LLBag
		
		Object[] array = new Object[numItems];
		int i = 0;
		
		Node trav = head.next;
		
		while (trav != null) {
			
			array[i] = trav.item;
		
			trav = trav.next;
			i++;
		}
		
		return array;
		
	}

	
	
	
	
	public String toString() {
		
		// returns the string representation of the LLBag
		
		Node trav = head.next;
		String str = "{";
		
		while (trav != null) {
			
			str += trav.item;
			
			if (trav.next != null) {
				
				str += ", ";
				
			}
			
			trav = trav.next;
			
		}
		
		return str + "}";
		
	}
	
	 public static void main(String[] args) {
	        // Create a Scanner object for user input.
	      
	        Object[] numItems = {1,2,3,4,5};
	        Bag bag1 = new LLBag(numItems);
	        System.out.println(bag1);
	        
	        bag1.add(1);
	        System.out.println(bag1);
	        
	        bag1.remove(5);
	        System.out.println(bag1);	        
	        
	        System.out.println(bag1.contains(7));	   
	        
	        Object item = bag1.grab();
	        System.out.println("grabbed " + item);
	        System.out.println();
	       
	        Object[] items = bag1.toArray();
	        for (int i = 0; i < items.length; i++) {
	            System.out.println(items[i]);
	        }
	        System.out.println();
	        
	
	 }
	
	
}
