
/**
 * Locality-Aware Selection Sort
 * 
 * @author ghousto
 * @version 9/22/14
 * @pso   P06
 */
public class LSelection extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private LSelection() {}

	/**
	 * sort the array
	 * @param a - array
	 * @param d - locality
	 */
	public static void sort(Comparable[] a, int d) {
		for (int i=0; i < a.length; ++i) { // the index to place the smallest element
			// Find the smallest element in the unsorted region
			int smallest = i;
			for (int j = i + 1; j < i + 1 + d && j < a.length; ++j) {
				if (Sort.less(a[j], a[smallest]))
					smallest = j;
			}
			Sort.exch(a, i, smallest);
		}
	}
}
