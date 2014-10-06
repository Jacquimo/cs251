

/**
 * Locality-Aware Heap Sort
 * 
 * @author ghousto
 * @version 10/5/14
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
		// Build small min-heap of size d+1 and then sort the small heap
		buildHeap(a, 0, d);
		for (int i = 0; i <= d; ++i) {
			a[i] = delMin(a, i, d); 
			
			
		}
		
		// Loop over each grouping of small heaps that we will create
		for (int i =d + 1; i < a.length; i += d + 1) {
			
			int rightHeapBound = i + d >= a.length ? a.length - 1 : i + d;
			buildHeap(a, i, rightHeapBound);
			
			for (int insertIndex = i; insertIndex <= rightHeapBound; ++insertIndex) {
				a[insertIndex] = delMin(a, i, rightHeapBound);
				
				// Insertion sort this element to the left
				int findingFinalPos = insertIndex;
				while (findingFinalPos > insertIndex - d - 1 && Sort.less(a[findingFinalPos], a[findingFinalPos-1])) {
					Sort.exch(a, findingFinalPos, findingFinalPos-1);
					--findingFinalPos;
				}
			}
		}
	}
	
	
	// Build the (relatively) small min-heap within the array that is to be sorted
	public static void buildHeap(Comparable[] a, int left, int right) {		
		// Do bottom-up heapify, runs in O(log n) time
		for (int k = (right - left) / 2; k >= 0; --k) {
			sink(a, k, left, right);
		}
	}
	
	// Create the heap with the root on the right hand side so that we can insert the min on the left-hand side
	// The values of k are determined as if they start at the 0 index and are going left to right. Adjust the values
	// of k and j when you actually need to access the elements inside the array
	public static void sink(Comparable[] a, int k, int left, int right) {		
		while(2*k < right - left) {		// Make sure THIS element isn't out of bounds
			int j = 2*k+1;
			if (j < right - left && less(a[right - (j+1)], a[right - j])) ++j; // Determine which child is smaller
			if (a[right - k].compareTo(a[right - j]) <= 0) break;		// If the parent is smaller than child, you've found the right spot
			Sort.exch(a, right - k, right - j);
			k = j;
		}
	}
	
	public static Comparable delMin(Comparable[] a, int left, int right) {
		Comparable ret = a[right];
		a[right] = a[left];
		sink(a, 0, left, right);
		return ret;
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
