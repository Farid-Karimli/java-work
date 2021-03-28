import java.lang.Math;
/* 
 * BigInt.java
 *
 * A class for objects that represent non-negative integers of 
 * up to 20 digits.
 */

public class BigInt  {
    // the maximum number of digits in a BigInt -- and thus the length
    // of the digits array
    private static final int SIZE = 20;
    
    // the array of digits for this BigInt object
    private int[] digits;
    
    // the number of significant digits in this BigInt object
    private int numSigDigits = 0;

    /*
     * Default, no-argument constructor -- creates a BigInt that 
     * represents the number 0.
     */
    public BigInt() {
        this.digits = new int[SIZE];
        this.numSigDigits = 1;  // 0 has one sig. digit--the rightmost 0!
    }
    
    public BigInt(int arr[]) {
    	
    	if (arr == null || arr.length > SIZE || arr.length < 0) {
    	
    		throw new IllegalArgumentException();
    		
    	} else {
    		
    		this.digits = new int[SIZE];
    		
    		int firstSigDig = 0;
    		
    		for (int i = 0 ; i <= arr.length-1; i++ ) {
    		
    			if (arr[i] > 10) {       
    				
    				throw new IllegalArgumentException();
    				

    			} else if (arr[i] != 0) {
    				
    				firstSigDig = i;
    				
    				break;
    			} 
    		
    		}
    		
    		for (int i = firstSigDig ; i <= arr.length-1; i++ ) {
    			
    			this.digits[(SIZE - arr.length ) + i] = arr[i];
    			numSigDigits++;
    			
    		}
    		
    	}
    	
    }
    
    public BigInt(int n) {
    	
    	if (n < 0) {
    		
    		throw new IllegalArgumentException();
    		
    	} else {
   
    		this.digits = new int[SIZE];
    		
    		int count = 1;
    		
    		for (int i = n; i > 0; i = i / 10) {
    			
    			int unit = i % 10;
    			
    			digits[SIZE - count] = unit;
    			this.numSigDigits++;
    			
    			count++;
    			
    		}
    		
    	}
    	
    }
    
    public int getNumSigDigits() {
    	
    	// returns the number of significant digits the BigInt object has 
    	
    	return this.numSigDigits;
    	
    }
    
    public String toString() {
    	
    	// This method returns the string representation of the BigInt object it is invoked on
    	
    	String str = "";
    	int index = 0;
    	
    	for (int i = 0; i < this.digits.length; i++) { 
    		
    		if (this.digits[i] != 0) {
    		
    			index = i;
    			break;
    		
    		} else if (this.digits[i] == 0 && i == this.digits.length - 1) {
    			
    			
    			str += 0;
    			
    			return str;
    			
    		}
    		
    	}
    	
    	for (int i = index; i < this.digits.length; i++) { 
    		
    		str += this.digits[i];
    		
    	}
    	
    	if (str.equals("")) {
    		
    		str = "0";
    		
    	}
    	
    	
    	 return str;
    }
    
    public int compareTo(BigInt other) {
    	
    	/* 
    	 * This method takes another BigInt object and 
    	 * returns 1 if the object the method is invoked on is greater than the other object, 
    	 * -1 if it is smaller, and 0 if they are equal
    	 */
    	
    	if (other.equals(null)) {
    		
    		throw new IllegalArgumentException();
    		
    		
    	} else {
    		
    		 if (this.numSigDigits > other.getNumSigDigits()) {
    			 
    			 return 1;
    			 
    		 } else if (this.numSigDigits < other.getNumSigDigits()) {
    			 
    			 return -1;
    			
    			 
    		 } for (int i = 0; i < this.digits.length; i++) {
    			 
    			 if (this.digits[i] != 0) {
    				 
    				 if (this.digits[i] > other.digits[i]) {
    					 
    					 return 1;
    					 
    				 } else if (this.digits[i] < other.digits[i]) {
    					 
    					 return -1;
    					
    					 
    				 }
    				 
    			 }
    			 
    		 }
    		 	return 0;
    	}

    }
    
    
    
    public BigInt add(BigInt other) {
    	
    	// this method takes another BigInt object and returns its addition of the object on which
    	// the method is invoked 
		
    	
    	int[] sum = new int[20];
    	int carry = 0;
    
    	
    	int longer_number; 
   
    	if (this.toString().equals("0")) {
    		
    		return other;
    		
    	} else if (other.toString().equals("0")){
    		
    		return this;
    		
    	}
    	
    	if ((this.getNumSigDigits() == 20 || other.getNumSigDigits() == 20) && (other.compareTo(new BigInt(0)) != 0 || this.compareTo(new BigInt(0)) != 0)) {
    		
    		throw new ArithmeticException();
    		
    	}
    	
		if (this.numSigDigits >= other.getNumSigDigits()) {
			
			 longer_number = this.numSigDigits;
			
		} else {
			
			 longer_number = other.numSigDigits;
		
		}
    	 
    	for (int i = SIZE - 1; i >= (SIZE - longer_number - 1); i--) {
    		
    		if ((this.digits[i] + other.digits[i] + carry) >= 10) {
    		
    			sum[i] = (this.digits[i] + other.digits[i] + carry) % 10; 			
    			carry = 1;
    	
    			
    		} else {
    			
    			sum[i] = (this.digits[i] + other.digits[i] + carry);
    			carry = 0;
    			
    		}
    		
    		
    	}
    		
    	BigInt result = new BigInt(sum);
    	
    	
    	return result;
    	
	}
    
    public BigInt mul(BigInt other) {
    	// This method multiplies two BigInt objects, this and other
    	
    	if (this.toString().equals("0")) {
    		
    		return other;
    		
    	} else if (other.toString().equals("0")){
    		
    		return this;
    		
    	}
    	
    	
    	
    	
    	
    	int[] sum1 = new int[20];
    	BigInt total_sum = new BigInt();
    	
    	int carry = 0;
    	
    	int shorter_number = 0;
    	int longer_number = 0;
    	
		if (this.numSigDigits >= other.getNumSigDigits()) {
			
			 longer_number = this.numSigDigits;
			 shorter_number = other.numSigDigits;
			
		} else {
			
			longer_number = other.numSigDigits;
			shorter_number = this.numSigDigits;
			
		} 
		
		int digit;
		
    	for (int i = SIZE - 1; i > SIZE - 1 - shorter_number; i--) {
    	
    		for (int j = SIZE - 1; j > SIZE - 1 - longer_number; j--) {
    			
    			if (i != SIZE - 1) {
    				
    				digit = j - 1;
    				
    			} else {
    				
    				digit = j;
    				
    			}
    			
    			if ((this.digits[j] * other.digits[i]) + carry >= 10) {
    				
    				sum1[digit] = ((this.digits[j] * other.digits[i]) + carry) % 10;
    				carry = ((this.digits[j] * other.digits[i]) + carry) / 10;
    				
    				
    			} else {
    				
    				sum1[digit] = ((this.digits[j] * other.digits[i]) + carry);
    				carry = 0;
    				
    			}
    			
    		}
    		
    		
    		
    		BigInt sum_1 = new BigInt(sum1);
    		total_sum = total_sum.add(sum_1);
    		
    		sum1 = new int[20];
  
    		
    	}
    	
    	return total_sum;

    }
    
   
    
    
    public static void main(String [] args) {
        System.out.println("Unit tests for the BigInt class.");
        System.out.println();

        /* 
         * You should uncomment and run each test--one at a time--
         * after you build the corresponding methods of the class.
         */
        
        
         
        System.out.println("Test 1: result should be 7");
        int[] a1 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7};
        BigInt b1 = new BigInt(a1);
        System.out.println(b1.getNumSigDigits());
        System.out.println();
        
         
        System.out.println("Test 2: result should be 1234567");
        b1 = new BigInt(a1);
        System.out.println(b1);
        System.out.println();
       
        
        System.out.println("Test 3: result should be 0");
        int[] a2 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
        BigInt b2 = new BigInt(a2);
        System.out.println(b2);
        System.out.println();
        
        System.out.println("Test 4: should throw an IllegalArgumentException");
        try {
            int[] a3 = { 0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
            BigInt b3 = new BigInt(a3);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();
        
        System.out.println("Test 5: result should be 1234567");
        b1 = new BigInt(1234567);
        System.out.println(b1);
        System.out.println();
       
        System.out.println("Test 6: result should be 0");
        b2 = new BigInt(0);
        System.out.println(b2);
        System.out.println();

        System.out.println("Test 7: should throw an IllegalArgumentException");
        try {
            BigInt b3 = new BigInt(-4);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 8: result should be 0");
        b1 = new BigInt(12375);
        b2 = new BigInt(12375);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 9: result should be -1");
        b2 = new BigInt(12378);
        System.out.println(b1.compareTo(b2));
        System.out.println();
        
        
        System.out.println("Test 10: result should be 1");
        System.out.println(b2.compareTo(b1));
        System.out.println();
        
        
        System.out.println("Test 11: result should be 0");
        b1 = new BigInt(0);
        b2 = new BigInt(0);
        System.out.println(b1.compareTo(b2));
        System.out.println();
        
        
        System.out.println("Test 12: result should be\n123456789123456789");
        int[] a4 = { 3,6,1,8,2,7,3,6,0,3,6,1,8,2,7,3,6 };
        int[] a5 = { 8,7,2,7,4,0,5,3,0,8,7,2,7,4,0,5,3 };
        BigInt b4 = new BigInt(a4); 
        BigInt b5 = new BigInt(a5);
        BigInt sum = b4.add(b5);
        System.out.println(sum);
        System.out.println();

        System.out.println("Test 13: result should be\n123456789123456789");
        System.out.println(b5.add(b4));
        System.out.println();
        
         
        System.out.println("Test 14: result should be\n3141592653598");
        b1 = new BigInt(0);
        int[] a6 = { 3,1,4,1,5,9,2,6,5,3,5,9,8 };
        b2 = new BigInt(a6);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 15: result should be\n10000000000000000000");
        int[] a19 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };    // 19 nines!
        b1 = new BigInt(a19);
        b2 = new BigInt(1);
        System.out.println(b1.add(b2));
        System.out.println();
       
        
        System.out.println("Test 16: should throw an ArithmeticException");
        int[] a20 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };  // 20 nines!
        try {
            b1 = new BigInt(a20);
            System.out.println(b1.add(b2));
        } catch (ArithmeticException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();
		
        
      
        System.out.println("Test 17: result should be 5670");
        b1 = new BigInt(135);
        b2 = new BigInt(42);
        BigInt product = b1.mul(b2);
        System.out.println(product);
        System.out.println();
        
        
       
        
        System.out.println("Test 18: result should be\n99999999999999999999");
        b1 = new BigInt(a20);   // 20 nines -- see above
        b2 = new BigInt(1);
        System.out.println(b1.mul(b2));
        System.out.println();

        System.out.println("Test 19: should throw an ArithmeticException");
        try {
            b1 = new BigInt(a20);
            b2 = new BigInt(2);
            System.out.println(b1.mul(b2));
        } catch (ArithmeticException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();
        
    }
}