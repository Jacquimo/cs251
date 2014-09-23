import java.util.Arrays;


/**
 * Locality-Aware Heap Sort
 * 
 * @author TODO put your username here
 * @version TODO put the date here
 * @pso   TODO put your PSO section here
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
		Comparable[] extra = new Comparable[d+1];
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
		}
	}
	
	public static void buildHeap(Comparable[] a) {
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
	}
}
