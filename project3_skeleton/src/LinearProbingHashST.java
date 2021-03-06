import java.util.Collections;
import java.util.PriorityQueue;

/*************************************************************************
 *  Compilation:  javac LinearProbingHashST.java
 *  Execution:    java LinearProbingHashST
 *
 *  Symbol table implementation with linear probing hash table.
 *
 *  % java LinearProbingHashST
 *  128.112.136.11
 *  208.216.181.15
 *  null
 *
 *
 *************************************************************************/

public class LinearProbingHashST<Key extends Comparable<Key>, Value>{
    private static final int INIT_CAPACITY = 22222223;
    private int N;           // number of key-value pairs in the symbol table
    private int M;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values
    static double startTest, endTest;
    // create an empty hash table - use 16 as default size
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }
    
    // create linear proving hash table of given capacity
    @SuppressWarnings("unchecked")
	public LinearProbingHashST(int capacity) {
        M = capacity;
        keys = (Key[])   new Comparable[M];
        vals = (Value[]) new Object[M];
    }
    
    // return the number of key-value pairs in the symbol table
    public int size() {
        return N;
    }
    
    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // does a key-value pair with the given key exist in the symbol table?
    public boolean contains(Key key) {
        return search(key) != null;
    }
    
    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode()  & 0x7fffffff ) % M;
    }
    
    // insert the key-value pair into the symbol table
    public void insert(Key key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        
        // double table size if 50% full
        
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) { vals[i] = val; return; }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }
    
    // return the value associated with the given key, null if no such value
    public Value search(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }
    
    // delete the key (and associated value) from the symbol table
    public void delete(Key key) {
        if (!contains(key)) return;
        
        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        
        // delete key and associated value
        keys[i] = null;
        vals[i] = null;
        
        // rehash all keys in same cluster
        i = (i + 1) % M;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            insert(keyToRehash, valToRehash);
            i = (i + 1) % M;
        }
        
        N--;
        

    }
    
    // return all of the keys as in Iterable
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < M; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }
    
    // This seemed far too easy...
    public int rank(Key key){
        if (key == null)
        	return 0;
    	
    	int numLess = 0; 
        for (int i = 0; i < M; ++i)
        	if (keys[i] != null) {
        		if (key.compareTo(keys[i]) > 0)
        			++numLess;
        	}
        return numLess;
    }
    
    public Key getValByRank(int k){
    	if (k < 0)
    		return null;
    	
    	if (k > N - 1) k = N - 1; // account for k's that are larger than the possible amount of keys
    	PriorityQueue<Key> maxheap = new PriorityQueue<Key>(k + 1, Collections.reverseOrder()); // a priority that is a max-heap
    	
    	int i = 0;
    	// add the first k+1 elements to the priority queue
    	for (; k + 1 > 0; ++i) {
    		if (keys[i] != null) {
    			maxheap.add(keys[i]);
    			--k;
    		}
    	}
    	// get the maximum element of the k+1 elements
    	Key returnValKey = maxheap.remove();
    	
    	for (; i < keys.length; ++i) {
    		// If the key is less than the key to return, update the key with rank(k)
    		if (keys[i] != null && keys[i].compareTo(returnValKey) < 0) {
    			maxheap.add(keys[i]);
    			returnValKey = maxheap.remove();
    		}
    	}
    	
    	return returnValKey;
    }
    
    // ???question on whether k=0 should return an empty list???
    public Iterable<Key> kSmallest(int k){
    	if (k <= 0) return new PriorityQueue<Key>();
    	
    	if (k > N) k = N;
    	PriorityQueue<Key> maxheap = new PriorityQueue<>(k + 1, Collections.reverseOrder());
    	
    	// add k elements to the maxheap
    	int i = 0;
    	for (; k > 0; ++i) {
    		if (keys[i] != null) {
    			maxheap.add(keys[i]);
    			--k;
    		}
    	}
    	Key maxOfKSmallest = maxheap.peek();
    	
    	// iterate over the rest of the table and use the max-heap to determine the k smallest elements
    	for (; i < keys.length; ++i) {
    		if (keys[i] != null && keys[i].compareTo(maxOfKSmallest) < 0) {
    			maxheap.add(keys[i]);
    			maxOfKSmallest = maxheap.remove(); // delete the largest so that you still have k-smallest elements
    		}
    	}
    	
    	return maxheap;
    }
    
    public Iterable<Key> kLargest(int k){
    	if (k <= 0) return new PriorityQueue<Key>();
    	
    	if (k > N) k = N;
    	PriorityQueue<Key> minheap = new PriorityQueue<Key>(k + 1);
    	
    	// add k elements to the minheap
    	int i = 0;
    	for(; k > 0; ++i) {
    		if (keys[i] != null) {
    			minheap.add(keys[i]);
    			--k;
    		}
    	}
    	Key minOfKLargestKeys = minheap.peek();
    	
    	// iterate over the rest of the table and use the min-heap to determine the k largest elements
    	for (; i < keys.length; ++i) {
    		if (keys[i] != null && keys[i].compareTo(minOfKLargestKeys) > 0) {
    			minheap.add(keys[i]);
    			minOfKLargestKeys = minheap.remove(); // delete the smallest so that you still have k-largest elements
    		}
    	}
    	
    	return minheap;
    }
    
    public int rangeCount(Key low, Key high){
        int cmp = high.compareTo(low);
    	if (cmp < 0) 
        	return 0;
        if (cmp == 0) {
        	if (contains(high)) // contains should run in O(1) time on average
        		return 1;
        	else
				return 0;
        }
    
        int range = 0;
        for (int i = 0; i < M; ++i) {
        	// if not null check to see if range (inclusive)
        	if (keys[i] != null && keys[i].compareTo(low) >= 0 && keys[i].compareTo(high) <= 0)
        		++range;
        }
        
        return range;
    }
    
    
}
