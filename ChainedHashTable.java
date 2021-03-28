/*
 * ChainedHashTable.java
 *
 * Computer Science 112, Boston University
 * 
 * Modifications and additions by:
 *     name:
 *     email:
 */

import java.util.*;     // to allow for the use of Arrays.toString() in testing

/*
 * A class that implements a hash table using separate chaining.
 */
public class ChainedHashTable implements HashTable {
    /* 
     * Private inner class for a node in a linked list
     * for a given position of the hash table
     */
    private class Node {
        private Object key;
        private LLQueue<Object> values;
        private Node next;
        
        private Node(Object key, Object value) {
            this.key = key;
            values = new LLQueue<Object>();
            values.insert(value);
            next = null;
        }
    }
    
    private Node[] table;      // the hash table itself
    private int numKeys;       // the total number of keys in the table
        
    /* hash function */
    public int h1(Object key) {
        int h1 = key.hashCode() % table.length;
        if (h1 < 0) {
            h1 += table.length;
        }
        return h1;
    }
    
    /*** Add your constructor here ***/
    public ChainedHashTable(int size) {
    	if (size <= 0) {
    		throw new IllegalArgumentException();
    	} else {
    		
    		table = new Node[size];
    		numKeys = 0;
    		
    		
    	}
    }
    
    /*
     * insert - insert the specified (key, value) pair in the hash table.
     * Returns true if the pair can be added and false if there is overflow.
     */
    public boolean insert(Object key, Object value) {
        /** Replace the following line with your implementation. **/
    	if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        } 
    	
    	int code = h1(key);
    	Node newKey = new Node(key,value);
    	
    	Node toCheck = table[code];
    	while (toCheck != null) {
    			
    		if (toCheck.key == key) {
    				toCheck.values.insert(value);
    				return true;
    		}
    			toCheck = toCheck.next;
    	}
    		
    	if (toCheck == null) {
    		newKey.next = table[code];
    		table[code] = newKey;
    	}
    		
    		numKeys++;
    	
    	
    	
        return true;
    }
    
    /*
     * search - search for the specified key and return the
     * associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> search(Object key) {
        /** Replace the following line with your implementation. **/
    	if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
    	
    	
    	
    	int code = h1(key);
    	Node nodeToCheck = table[code];
    	
    	while (nodeToCheck != null) {
    		
    		if (nodeToCheck.key == key) {
    			return nodeToCheck.values;
    		}
    		nodeToCheck = nodeToCheck.next;
    		
    	}
    	
        return null;
    }
    
    /* 
     * remove - remove from the table the entry for the specified key
     * and return the associated collection of values, or null if the key 
     * is not in the table
     */
    public Queue<Object> remove(Object key) {
        /** Replace the following line with your implementation. **/
    	if (key == null) {
            throw new IllegalArgumentException("key must be non-null");
        }
    	
    	
    	
    	
    	int code = h1(key);
    	Node nodeToCheck = table[code];
    	Node nodeBefore = null;
    	
    	while (nodeToCheck != null) {
    		
    		
    		if (nodeToCheck.key == key) {
    			if (nodeBefore == null) { // the key we're removing is the first node of the cell in the table 
    				table[code] = null;
    				numKeys--;
    				return nodeToCheck.values;
    			} else {
    			 nodeBefore.next = nodeToCheck.next;
    			 numKeys--;
    			 return nodeToCheck.values;
    			}
    		}
    		nodeBefore = nodeToCheck;
    		nodeToCheck = nodeToCheck.next;
    		
    	}
        return null;
    }
    
    
    /*** Add the other required methods here ***/
    public int getNumKeys() {
    	return numKeys;
    }
    
    public double load() {
    	 
    	 return (double) numKeys/(double) table.length;
    	
    }
    public Object[] getAllKeys() {
    	
    	Object[] array = new Object[numKeys];
    	int index = 0;
    	
    	for (int i = 0; i < table.length; i++) {
    		
    		if (table[i] != null) {
    			Node node = table[i];
    			
    			while (node != null) {
    				array[index] = node.key;
    				index++;
    				
    				node = node.next;
    			}
    			
    		}
    		
    	}
    	
    	return array;
    	
    }
    
    public void resize(int size) {
    	if (size < table.length) {
    		throw new IllegalArgumentException();
    	} else if (size == table.length) {
    		return;
    	} else {
    		
    		ChainedHashTable newHash = new ChainedHashTable(size);
    		Node[] newTable = new Node[size];
    		Object[] keys = this.getAllKeys();
    		
    		for (int i = 0; i < keys.length; i++) {
    			
    			Object value = this.search(keys[i]);
    			int code = newHash.h1(keys[i]);
    			Node newNode = new Node(keys[i], value);
    			
    			if (newTable[code] != null) {
    				newNode.next = newTable[code];
    				newTable[code] = newNode;
    			} else {
    				newTable[code] = newNode;
    			}
    			
    			
    		}
    		
    		table = newTable;
    		
    		
    		
    	}
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    /*
     * toString - returns a string representation of this ChainedHashTable
     * object. *** You should NOT change this method. ***
     */
    public String toString() {
        String s = "[";
        
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                s += "null";
            } else {
                String keys = "{";
                Node trav = table[i];
                while (trav != null) {
                    keys += trav.key;
                    if (trav.next != null) {
                        keys += "; ";
                    }
                    trav = trav.next;
                }
                keys += "}";
                s += keys;
            }
        
            if (i < table.length - 1) {
                s += ", ";
            }
        }       
        
        s += "]";
        return s;
    }

    public static void main(String[] args) {
        /** Add your unit tests here **/
    	System.out.println("Testing insert");
    	System.out.println("--------------");
    	
    	System.out.println("Test 1 - adding a value to a key that already exists");
    	
    	ChainedHashTable table = new ChainedHashTable(5);
		table.insert("howdy", 15);
		table.insert("goodbye", 10);
    	
    	try {
    		System.out.println(table);
    		table.insert("apple", 5);
    		String result = table.toString(); 
    		String expected = "[{apple; howdy}, null, null, {goodbye}, null]";
    		System.out.println("Expected result?");
    		System.out.println(result.equals(expected));
    		System.out.println();
    		
    	} catch (Exception e) {
    		System.out.println("Incorrectly threw exception!");
    	}
    	System.out.println();
    	
    	
    	System.out.println("Test 2 - adding a null key");
    	  
    	ChainedHashTable hash = new ChainedHashTable(4);
    	
    	try {
    		
    		hash.insert(1, "FARID");
    		hash.insert(1, "TEYMUR");
    		hash.insert(2, "EMIN");
    		hash.insert(null,"JAVID");
    	
    		System.out.println(hash);
    		System.out.println();
    	} catch (Exception e){
    		System.out.println("Correctly threw exception");
    	}
    	
    	System.out.println();
    	
    	
    	System.out.println("Testing search");
    	System.out.println("--------------");
    	
    	System.out.println("Test 1");
    	System.out.println("Testing on key \"apple\" from TABLE");
    	
    		try {
    	
    		String expected = "{5}";
    		String result = table.search("apple").toString();
    		System.out.println("Expected result?");
    		System.out.println(expected.equals(result));
    		
    		} catch (Exception e) {
    			System.out.println("Incorrectly threw exception");
    		}
    		
    		System.out.println();
    	
    	System.out.println("Test 2");
    	System.out.println("Testing on key 1 from HASH");
    	
    	try {
    	
    		System.out.println(hash); 
    		System.out.println(hash.search(1));
    		Object result = hash.search(1);
    		String expected = "{FARID, TEYMUR}";
    		System.out.println("Expected result?");  
    		System.out.println(result.equals(expected));  
    		
    		System.out.println();
    	
    	} catch (Exception e) {
    		System.out.println("Incorrectly threw exception");  
    	}
    	
    	
    	
    	
    	
    	System.out.println("Testing remove");
    	System.out.println("--------------");
    	
    	System.out.println("Test 1");
    	System.out.println("Removing key \"howdy\", then \"apple\" from TABLE");
    	System.out.println();
    	
    	try {
    	
    		
    		
    		System.out.println("Removing \"howdy\"...");
    		String result = table.remove("howdy").toString();
    		String expected = "{15}";
    		System.out.println("Expected result No.1?");  
    		System.out.println(result.equals(expected));  
    		System.out.println(table);    	
    	
    		System.out.println();  
    	
    		System.out.println("Removing \"apple\"...");
    		String result2 = table.remove("apple").toString();
    		String expected2 = "{5}";
    		System.out.println("Expected result No.2?");  
    		System.out.println(result2.equals(expected2));  
    		System.out.println(table);    	
    		System.out.println();
    	
    	} catch (Exception e) {
    		System.out.println("Incorrectly threw exception");
    	}
    		
    	System.out.println();
    	System.out.println("Test 2");
    	System.out.println("Removing  key 1 from HASH");
    	
    	try {
    	
    		System.out.println("Removing key 1...");
    		String result =  hash.remove(1).toString();
    		String expected = "{FARID, TEYMUR}";    		
    		System.out.println("Expected result?");  
    		System.out.println(result.equals(expected));  
    		System.out.println(hash);    	
    		System.out.println();
    		
    	} catch (Exception e) {
    		System.out.println("Incorrectly threw an exception");  
    	}
    	
    	System.out.println("Testing getNumKeys");
    	System.out.println("--------------");
    	System.out.println("Test 1");
    	
    	ChainedHashTable table2 = new ChainedHashTable(5);
    	
    	try {
    		table2.insert("howdy", 15);
    		table2.insert("goodbye", 10);
    		table2.insert("apple", 5);
    		
    		table2.insert("howdy", 25);
    		int result = table2.getNumKeys();    		
    		System.out.println("Result should be 3");
    		System.out.println("Expected result?");
    		System.out.println(result  == 3);
    		System.out.println(table2);
    	
    	} catch (Exception e){
    		System.out.println("Incorrectly threw exception!");
    	}
    	System.out.println();
    	
    	
    	System.out.println("Test 2");
    	
    	ChainedHashTable table3 = new ChainedHashTable(5);
    	
    	try {
    		table3.insert("howdy", 15);
    		table3.insert("goodbye", 10);
    		table3.insert("apple", 5);
    		   		
    		table3.remove("howdy");
    		int result = table3.getNumKeys();    
    		System.out.println("Result should be 2");
    		System.out.println("Expected result?");
    		System.out.println(result == 2);
    		System.out.println(table3);
    	
    	} catch (Exception e){
    		System.out.println("Incorrectly threw exception!");
    	}
    	System.out.println();
    	
    	
    	
    	
    	
    	System.out.println("Testing load");
    	System.out.println("--------------");
    	System.out.println("Test 1");
    	
    	ChainedHashTable table4 = new ChainedHashTable(5);
    	
    	try {
    		table4.insert("howdy", 15);
    		table4.insert("goodbye", 10);
    		table4.insert("apple", 5);
    		System.out.println(table4);
    		
    		double result = table4.load();
    		System.out.println("Inserting \"peak\" ");
    		table4.insert("pear", 6);
    		double result2 = table4.load();
    		System.out.println("Results should be 0.6 and 0.8");
    		System.out.println("Expected result? ");
    		System.out.println(result == 0.6 && result2 == 0.8);
    	
    	} catch (Exception e){
    		System.out.println("Incorrectly threw exception!");
    	}
    	System.out.println();
    	
    	
    	System.out.println("Test 2 - full table");
    	
    	ChainedHashTable table5 = new ChainedHashTable(3);
    	
    	try {
    		table5.insert("howdy", 15);
    		table5.insert("goodbye", 10);
    		table5.insert("apple", 5);
    		System.out.println(table3);	
    		
    		double result = table5.load();  
    		
    		System.out.println("Result should be 1.0");
    		System.out.println("Expected result?");
    		System.out.println(result == 1.0);
    		
    	
    	} catch (Exception e){
    		System.out.println("Incorrectly threw exception!");
    	}
    	System.out.println();
    	
    	
    	
    	System.out.println("Testing getAllKeys");
    	System.out.println("--------------");
    	System.out.println("Test 1 - testing on a table with 3 keys");
    	
    	ChainedHashTable table6 = new ChainedHashTable(5);
    	
    	try { 
    		table6.insert("howdy", 15);
    		table6.insert("goodbye", 10);
    		table6.insert("apple", 5);
    		table6.insert("howdy", 25);    
    		Object[] keys = table6.getAllKeys();
    		String expected = "[apple, howdy, goodbye]";
    		System.out.println("Result should be: " + expected);
    		System.out.println("Expected result?");
    		System.out.println(Arrays.toString(keys).equals(expected));
    	} catch (Exception e){
    		System.out.println("Incorrectly threw exception!");
    	}
    	
    	System.out.println();
    	
    	System.out.println("Test 2 - empty table");
    	
    	ChainedHashTable table7 = new ChainedHashTable(5);
    	
    	try { 
    		
    		Object[] keys = table7.getAllKeys();
    		
    		String expected = "[]";
    		System.out.println("Result should be: " + expected);
    		System.out.println("Expected result?");
    		System.out.println(Arrays.toString(keys).equals(expected));
    	} catch (Exception e){
    		System.out.println("Incorrectly threw exception!");
    	}  	
    	
    	System.out.println();

    	System.out.println("Testing resize");
    	System.out.println("--------------");
    	System.out.println("Test 1 - testing on a table with 5 keys, resizing to 7 keys");
    	
    	
    	try {
    	
    		ChainedHashTable tableToResize = new ChainedHashTable(5);
    		tableToResize.insert("howdy", 15);
    		tableToResize.insert("goodbye", 10);
    		tableToResize.insert("apple", 5);
    		System.out.println("Current table: " + tableToResize);
        	System.out.println("Resizing...");

    		tableToResize.resize(7);
    		String expected = "[null, {apple}, null, null, null, {howdy}, {goodbye}]";
    		String result = tableToResize.toString();
    		
    		System.out.println("Expected result?");
    		System.out.println(result.equals(expected));
    		
    		
    	
    	} catch (Exception e){ 
    		System.out.println("Incorrectly threw exception");
    	}
    	
    	System.out.println();
    	
    	
    	System.out.println("Test 2 - attempt at resizing to a smaller size");
    	

    	try {
    	
    		ChainedHashTable tableToResize = new ChainedHashTable(5);
    		tableToResize.insert("howdy", 15);
    		tableToResize.insert("goodbye", 10);
    		tableToResize.insert("apple", 5);
    		System.out.println("Current table: " + tableToResize);
        	System.out.println("Resizing...");

    		tableToResize.resize(3);
    		String expected = "[null, {apple}, null, null, null, {howdy}, {goodbye}]";
    		String result = tableToResize.toString();
    		
    		System.out.println("Expected result?");
    		System.out.println(result.equals(expected));
    		
    		
    	
    	} catch (Exception e){ 
    		System.out.println("Correctly threw exception!");
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
