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
		quicksort(a, 0, a.length - 1);
	}
	
	public static void quicksort(Comparable[] a, int left, int right) {
		// Base case
		if (left >= right) return;
		
		// Randomly generate the index of the pivot
		Random generator = new Random();
		int pivot = generator.nextInt(right - left + 1) + left;
		
		pivot = partition(a, pivot, left, right);
		quicksort(a, left, pivot-1);
		quicksort(a, pivot+1, right);
	}
	
	/**
	 * Partition a region of the array based upon the value of a given pivot index
	 * @param a - the array
	 * @param pivot - the index of the pivot
	 * @param left - starting index of region of array to be partitioned
	 * @param right - ending index of region of array to be partitioned
	 * @return the final position of the pivot
	 */
	public static int partition(Comparable[] a, int pivot, int left, int right) {
		Sort.exch(a, pivot, right);
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