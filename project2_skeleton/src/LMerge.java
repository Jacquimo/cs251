import java.util.Arrays;


/**
 * Locality-Aware Merge Sort
 * 
 * @author ghousto
 * @version 9/22/14
 * @pso   P06
 *
 */
public class LMerge extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private LMerge() {}

	/**
	 * sort the array
	 * @param a - array
	 * @param d - locality
	 */
	public static void sort(Comparable[] a, int d) {
		//mergesort(a, new Comparable[a.length], 0, a.length - 1, d);
		Comparable[] aux = Arrays.copyOf(a, a.length); 
		mergesort(a, aux, 0, a.length - 1, d);
		//merge(aux, a, 0, a.length/2, a.length-1);
		Sort.show(a);
	}
	
	/**
	 * 
	 * @param a - the array
	 * @param aux - the auxiliary helper array
	 * @param left - starting index of left part of array to sort
	 * @param right - starting index of right part of array to sort
	 * @param d - locality condition: element at k is at most d positions from its final position
	 */
	public static void mergesort(Comparable[] a, Comparable[] aux, int left, int right, int d) {
		if (right <= left) return;
		
		/*if (right - left <= 16) {
			insertionSort(a, left, right);
			return;
		}*/
		
		int middle = (left + right) / 2;
		/*mergesort(a, aux, left, middle, d);
		mergesort(a, aux, middle+1, right, d);
		merge(a, aux, middle - d >= left ? middle - d : left, middle, middle + d <= right ? middle + d : right);
		*/
		
		boolean localAisA = !TestQSort.aIsA;
		TestQSort.aIsA = localAisA;
		//Integer find = 10;
		
		int sizeToPrint = 4;
		
		//if (right - left + 1 < sizeToPrint) TestQSort.printArray(a, aux, left, right, 0);
		mergesort(aux, a, left, middle, d);
		TestQSort.aIsA = localAisA;
		
		//if (right - left + 1 < sizeToPrint)TestQSort.printArray(a, aux, left, right, 1);
		mergesort(aux, a, middle+1, right, d);
		TestQSort.aIsA = localAisA;
		
		//if (right - left + 1 < sizeToPrint)TestQSort.printArray(a, aux, left, right, 2);
		merge(aux, a, middle - d >= left ? middle - d : left, middle, 
				middle + d <= right ? middle + d : right);
		TestQSort.aIsA = localAisA;
		//if (right - left + 1 < sizeToPrint)TestQSort.printArray(a, aux, left, right, 3);
		//if (right - left + 1 >= sizeToPrint && !LMerge.isSorted(a, left, right-1))TestQSort.printArray(a, aux, left, right, 3);
		
		//merge(aux, a, left, middle, right);
	}
	
	public static void merge(Comparable[] a, Comparable[] aux, int left, int mid, int right) {
		/*for (int k = left; k <= right; ++k)
			aux[k] = a[k];
		
		int i = left, j = mid+1;
		for (int k = left; k <= right; ++k) {
			if (i > mid) a[k] = aux[j++];
			else if (j > right) a[k] = aux[i++];
			else if (Sort.less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
		}*/
		
		int i = left, j = mid+1;
		for (int k = left; k <= right; ++k) {
			if (i > mid) aux[k] = a[j++];
			else if (j > right) aux[k] = a[i++];
			else if (Sort.less(a[j], a[i])) aux[k] = a[j++];
			else aux[k] = a[i++];
		}
	}
	
	public static void insertionSort(Comparable[] a, int left, int right) {
		for (int i = left; i <= right; ++i) {
			int j = i;
			while (j > left && Sort.less(a[j], a[j-1])) {
				Sort.exch(a, j, j-1);
				--j;
			}
		}
	}
	
	public static boolean contains(Comparable[] a, Comparable c, int left, int right) {
		if (left > right) return false;
		if (left == right) return c.equals(a[left]);
		
		Comparable mid = a[(right - left) / 2];
		if (mid.equals(c))
			return true;
		if (Sort.less(c, mid))
			return contains(a, c, left, (right - left) / 2 - 1);
		else /*if (Sort.less(mid, c))*/
			return contains(a, c, (right - left) / 2 + 1, right);
		//return true;
	}
	
	public static boolean isSorted(Comparable[] a, int left, int right) {
		for (int i = left; i <= right; i++) {
			if (less(a[i+1],a[i]))
				return false;
		}
		return true;
	}
	
	public static void show(Comparable[] a, int left, int right) {
		for (int i = left; i <= right; i++) {
        	if (i > 0 && less(a[i],a[i-1]))
        		StdOut.println(a[i] + " <-- X");
        	else if (i < a.length-1 && less(a[i+1],a[i]))
        		StdOut.println(a[i] + " <-- X");
        	else
        		StdOut.println(a[i]);
        }
    }
}
