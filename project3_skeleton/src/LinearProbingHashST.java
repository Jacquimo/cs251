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
    
    public int rank(Key key){
        if (key == null)
        	return 0;
    	
    	int numLess = 0;
        
        for (int i = 0; i < keys.length; ++i)
        	if (key.compareTo(keys[i]) > 0)
        		++numLess;
        
        return numLess;
    }
    
    public Key getValByRank(int k){
        /* TODO: Implement getValByRank here... */
    	return null;
    }
    
    public Iterable<Key> kSmallest(int k){
        /* TODO: Implement kSmallest here... */
    	return null;
    }
    
    public Iterable<Key> kLargest(int k){
        /* TODO: Implement kLargest here... */
    	return null;
    }
    
    public int rangeCount(Key low, Key high){
        /* TODO: Implement rangeCount here... */
    	return 1;
    }
    
    
}
