

/**
 * Locality-Aware Heap Sort
 * 
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso   TODO put your PSO section here
 *
 */
public class LHeap extends Sort {
	public static int rootIndex;
	
	/**
	 * this class should not be instantiated
	 */
	private LHeap() {}

	/**
	 * sort the array
	 * @param a - array
	 * @param d - locality
	 */
	public static void sort(Comparable[] a, int d) {
		//d = a.length-1;
		/*Comparable[] extra = new Comparable[d+1];
		int index = a.length-1;
		for (int i = d; i >= 0; --i){ // copy over the first d+1 elements, keeping track of where to get the next element
			extra[i] = a[index--];
		}
		
		buildHeap(extra);
		
		for (int i = a.length - 1; i >= 0; --i) {
			if (index >= 0) { // if we still are adding values to the heap
				a[i] = delMax(extra, d);
				insert(extra, a[index--]);
			}
			else
				a[i] = delMax(extra, i);
		}*/
		
		rootIndex = buildHeap(a, d);
	}
	
	public static int buildHeap(Comparable[] a, int d) {
		/*for (int k = a.length / 2; k >= 0; --k) {
			sink(a, k);
		}*/
		
		// Create a heap whose roots starts at a.length - d - 1
		for (int k = a.length - d / 2 - 1; k < a.length; ++k) {
			sink(a, k, a.length-1);
		}
		return a.length - d - 1;
	}
	
	public static Comparable delMax(Comparable[] a, int lastIndex) {
		Comparable ret = a[rootIndex];
		a[rootIndex] = a[lastIndex];
		//a[lastIndex] = null;
		sink(a, rootIndex, lastIndex);
		
		return ret;
	}
	
	// this function will utilize the fact that only the last element will be missing from the heap for each insertion
	public static void insert(Comparable[] a, Comparable element) {
		a[a.length-1] = element;
		swimUp(a, a.length-1);
	}
	
	public static void swimUp(Comparable[] a, int k) {
		if (k <= 0) return;
		Comparable child = a[k];
		Comparable parent = a[(k-1)/2];
		if (Sort.less(parent, child)) {
			Sort.exch(a, k, (k-1)/2);
			swimUp(a, (k-1)/2);
		}
	}
	
	// a pre-condition for this method is that it will never be called on a leaf. Otherwise, there will likely be an exception
	public static void sink(Comparable[] a, int root, int maxIndex) {
		int k = 0;
		while(2*k < maxIndex) {
			int j = 2*k+1;
			if (j < maxIndex && less(a[j], a[j+1])) ++j;
			if (a[root+k].compareTo(a[root+j]) > 0) break;
			Sort.exch(a, root+k, root+j);
			root = root + j - k;
		}
	}
	
	public static boolean canHaveChildren(Comparable[] a, int k) {
		return 2*k+1 < a.length;
	}
	
	public static boolean isHeap(Comparable[] a, int k, int maxIndex) {		
		if (2*k >= maxIndex)
			return true;
			
		int i = 2*k+1;
		if (i < maxIndex && less(i, i+1)) ++i;
		if (a[k].compareTo(a[i]) < 0) return false;
			
		if (isHeap(a, 2*k+1, maxIndex))
			return isHeap(a, 2*k+2, maxIndex);
		else
			return false;
	}
}
