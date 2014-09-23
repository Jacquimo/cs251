
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
		mergesort(a, new Comparable[a.length], 0, a.length - 1, d);
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
		
		if (right - left <= 16) {
			insertionSort(a, left, right);
			return;
		}
		
		int middle = (left + right) / 2;
		mergesort(a, aux, left, middle, d);
		mergesort(a, aux, middle+1, right, d);
		merge(a, aux, middle - d >= left ? middle - d : left, middle, middle + d <= right ? middle + d : right);
	}
	
	public static void merge(Comparable[] a, Comparable[] aux, int left, int mid, int right) {
		for (int k = left; k <= right; ++k)
			aux[k] = a[k];
		
		int i = left, j = mid+1;
		for (int k = left; k <= right; ++k) {
			if (i > mid) a[k] = aux[j++];
			else if (j > right) a[k] = aux[i++];
			else if (Sort.less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
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
}
