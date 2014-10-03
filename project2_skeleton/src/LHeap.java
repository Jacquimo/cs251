

/**
 * Locality-Aware Heap Sort
 * 
 * @author ghousto
 * @version 10/3/14
 * @pso   P06
 *
 */
public class LHeap extends Sort {
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
		
		for (int i = 0; i < 2*d; ++i) {
			System.out.printf("%d ", (int)a[i]);
		}
		System.out.printf("\nAfter the first small heap is made\n");
		
		buildHeap(a, 0, d);
		
		for (int i = 0; i < 2*d; ++i) {
			System.out.printf("%d ", (int)a[i]);
		}
	}
	
	
	// Build the (relatively) small min-heap within the array that is to be sorted
	public static void buildHeap(Comparable[] a, int left, int right) {
		/*for (int k = a.length / 2 - 1; k >= 0; --k) {
			sink(a, k, a.length - 1);
		}*/
		
		// Do bottom-up heapify, runs in O(log n) time
		for (int k = right / 2 - 1; k >= left; --k) {
			sink(a, left, right);
		}
	}
	
	public static void sink(Comparable[] a, int left, int right) {		
		int k = 0;
		while(2*k < right - left) {		// Make sure THIS element isn't out of bounds
			int j = 2*k+1;
			if (j < right - left && less(a[j+1 + left], a[j + left])) ++j; // Determine which child is smaller
			if (a[k + left].compareTo(a[j + left]) <= 0) break;		// If the parent is smaller than child, you've found the right spot
			Sort.exch(a, k + left, j + left);
			k = j;
		}
	}
	
	
	/*public static void buildHeap(Comparable[] a) {
		for (int k = a.length / 2; k >= 0; --k) {
			sink(a, k);
		}
	}
	
	public static Comparable delMax(Comparable[] a, int lastIndex) {
		Comparable ret = a[0];
		a[0] = a[lastIndex];
		a[lastIndex] = null;
		sink(a, 0);
		
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
	public static void sink(Comparable[] a, int k) {
		if (!canHaveChildren(a,k)) return;
		
		Comparable left = a[2*k+1];
		Comparable right = 2*k+2 < a.length ? a[2*k+2] : null;
		
		Comparable largerChild;
		boolean leftIsLarger = false;;
		if (left == null) {largerChild = right; leftIsLarger = false; }
		else if (right == null) {largerChild = left; leftIsLarger = true; }
		//else if (left == null && right == null) largerChild = null;
		else {
			largerChild = Sort.less(left, right) ? right : left;
			leftIsLarger = Sort.equal(largerChild, left);
		}
		
		if (largerChild != null && Sort.less(a[k], largerChild)) { // if a[k] is less than its larger child, keep sinking
			Sort.exch(a, k, leftIsLarger ? 2*k+1 : 2*k+2);
			if (canHaveChildren(a, leftIsLarger ? 2*k+1 : 2*k+2))
				sink(a, leftIsLarger ? 2*k+1 : 2*k+2);
		}
	}
	
	public static boolean canHaveChildren(Comparable[] a, int k) {
		return 2*k+1 < a.length;
	}*/
}
