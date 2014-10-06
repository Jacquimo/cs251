/**
 * General Heap Sort
 * 
 * Some of the implementation is based off of the ideas in the lecture slides
 * 
 * @author ghousto
 * @version 10/2/14
 * @pso   P06
 *
 */
public class Heap extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Heap() {}

	/**
	 * sort a specified array using heapsort
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		buildMaxHeap(a);
		for (int i = a.length - 1; i >= 0; --i) {
			a[i] = delMax(a, i);
		}
	}
	
	/**
	 * Tests if the specified array is a valid max heap with a specified root index.
	 * This method may not work as intended. Hasn't been tested with new (i.e. in-place) implementation
	 * @param a - the array
	 * @param k - the root index
	 * @param maxIndex - the maximum index to check (so that you don't check outside of the heap)
	 * @return
	 */
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
	
	/**
	 * Build a max-heap out of a specified array
	 * @param a - the array to heapify
	 */
	public static void buildMaxHeap(Comparable[] a) {
		for (int k = a.length / 2 - 1; k >= 0; --k) {
			sink(a, k, a.length - 1);
		}
	}
	
	/**
	 * Deletes the maximum element (root) from the heap and then corrects the heap
	 * @param a - the array in which the heap is represented
	 * @param lastIndex - the index of the last element in the heap
	 * @return the maximum element in the heap
	 */
	public static Comparable delMax(Comparable[] a, int lastIndex) {
		Comparable ret = a[0];
		a[0] = a[lastIndex];
		//a[lastIndex] = null;
		sink(a, 0, lastIndex-1);
		
		return ret;
	}
	
	/**
	 * Sink the element at the specified index until it logically conforms to the rules of the heap (e.g. value is less than parent value)
	 * @param a - the array
	 * @param k - the index of the element to sink
	 * @param maxIndex - the maximum index to check (so that you don't check outside of the heap)
	 */
	public static void sink(Comparable[] a, int k, int maxIndex) {		
		while(2*k < maxIndex) {
			int j = 2*k+1;
			if (j < maxIndex && less(a[j], a[j+1])) ++j;
			if (a[k].compareTo(a[j]) > 0) break;
			Sort.exch(a, k, j);
			k = j;
		}
		
		/*if (!canHaveChildren(a,k)) return;
		
		Comparable left = 2*k+1 < a.length ? a[2*k+1] : null;
		Comparable right = 2*k+2 < a.length ? a[2*k+2] : null;
		
		Comparable largerChild = maxComp(left, right);
		
		
		/*boolean leftIsLarger = false;;
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
				sink(a, leftIsLarger ? 2*k+1 : 2*k+2, maxIndex);
		}*/
		
		
		/*if (largerChild != null && Sort.less(a[k], largerChild)) {
			if (largerChild == left) {
				Sort.exch(a, k, 2*k+1);
				if (2*k+1 < maxIndex)
					sink(a, 2*k+1, maxIndex);
			}
			else {
				Sort.exch(a, k, 2*k+2);
				if (2*k+2 < maxIndex)
					sink(a, 2*k+2, maxIndex);
			}
		}*/
	}
}
