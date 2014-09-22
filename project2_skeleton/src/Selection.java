
/**
 * General Selection Sort
 * 
 * TODO if this is an off-the-shelf implementation, cite where you got it from.
 * 
 * @author ghousto
 * @version 9/22/14
 * @pso   P06
 *
 */
public class Selection extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Selection() {}

	/**
	 * sort the array
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		for (int i=0; i < a.length; ++i) { // the index to place the smallest element
			// Find the smallest element in the unsorted region
			int smallest = i;
			for (int j = i + 1; j < a.length; ++j) {
				if (Sort.less(a[j], a[smallest]))
					smallest = j;
			}
			Sort.exch(a, i, smallest);
		}
	}
}
