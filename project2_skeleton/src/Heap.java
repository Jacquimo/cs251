








/**
 * General Heap Sort
 * 
 * 
 * @author ghousto
 * @version 9/22/14
 * @pso   P06
 *
 */
public class Heap extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Heap() {}

	/**
	 * sort the array
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		/*Comparable[] extra = Arrays.copyOf(a, a.length);
		buildHeap(extra);
		StdOut.printf("Was \"a\" made into a heap? %s\n", isHeap(extra, 0, extra.length-1));
		for (int i = extra.length - 1; i >= 0; --i) {
			a[i] = delMax(extra, i);
		}*/
		
		buildHeap(a);
		//StdOut.printf("Was \"a\" made into a heap? %s\n", isHeap(a, 0, a.length-1));
		for (int i = a.length - 1; i >= 0; --i) {
			a[i] = delMax(a, i);
		}
		
		//Sort.show(a);
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
	
	public static void buildHeap(Comparable[] a) {
		for (int k = a.length / 2; k >= 0; --k) {
			sink(a, k, a.length - 1);
		}
	}
	
	public static Comparable delMax(Comparable[] a, int lastIndex) {
		Comparable ret = a[0];
		a[0] = a[lastIndex];
		//a[lastIndex] = null;
		sink(a, 0, lastIndex-1);
		
		return ret;
	}
	
	// a pre-condition for this method is that it will never be called on a leaf. Otherwise, there will likely be an exception
	public static void sink(Comparable[] a, int k, int maxIndex) {
		/*if (a[k] == null || 2*k+1 >= maxIndex)
			return;*/
		
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
	
	public static boolean canHaveChildren(Comparable[] a, int k) {
		return 2*k+1 < a.length;
	}
	
	public static Comparable maxComp(Comparable a, Comparable b) {
		if (a == null)
			return b;
		if (b == null)
			return a;
		
		int comp = a.compareTo(b);
		if (comp < 0)
			return b;
		else
			return a;
	}
}
