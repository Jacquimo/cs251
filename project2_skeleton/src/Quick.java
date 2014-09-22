import java.util.Random;


/**
 * General Quick Sort
 * 
 * TODO This implementation uses the same logic as the one in the lecture slides
 * 
 * @author ghousto
 * @version 9/22/14
 * @pso   P06
 *
 */
public class Quick extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Quick() {}

	/**
	 * sort the array
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		// TODO implement sort
	}
	
	public static void quicksort() {
		
	}
	
	public static int partition(Comparable[] a, int left, int right) {
		// Randomly generate the pivot
		Random generator = new Random();
		int pivot = generator.nextInt(right - left + 1) + left;
		Sort.exch(a, pivot, right);
		
		// Partition based on pivot value
		int i = left, j = right-1;
		Comparable pivotVal = a[right];
		
		while (true) {
			// Find index i such that a[i] > a[right]
			while (i < right && i < j && Sort.less(a[i], a[right]))
				++i;
			
			// Find index j such that a[j] < a[right]
			while (j >= left && i < j && Sort.less(a[right], a[j]))
				--j;
			
			if (i >= j) break; // No more elements need to be swapped
			Sort.exch(a, i, j);
		}
		// Need to make sure that j is the correct index to swap the pivot back into
		Sort.exch(a, j, right);
		
		return j;
	}
}