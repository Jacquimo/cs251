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
		
		// Optimize code so that all small subarrays are sorted using insertion sort
		if (right - left <= 16) {
			insertionSort(a, left, right);
			return;
		}
		
		// Randomly generate the index of the pivot
		Random generator = new Random();
		int pivot = generator.nextInt(right - left + 1) + left;
		
		pivot = partition(a, pivot, left, right);
		int lengthLeft = pivot - left;
		int lengthRight = right - pivot;
		
		// Optimize code to sort shorter side first
		if (lengthLeft < lengthRight) {
			quicksort(a, left, pivot-1);
			quicksort(a, pivot+1, right);
		}
		else {
			quicksort(a, pivot+1, right);
			quicksort(a, left, pivot-1);
		}
		
		/*if (lengthLeft < lengthRight) { // Sort the shorter side first (optimization)
			if (lengthLeft <= 16)
				insertionSort(a, left, pivot-1);
			else {
				quicksort(a, left, pivot-1);
				quicksort(a, pivot+1, right);
			}
		}
		else {
			if (lengthRight <= 16)
				insertionSort(a, pivot+1, right);
			else {
				quicksort(a, pivot+1, right);
				quicksort(a, left, pivot-1);
			}
		}*/
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
		pivot = right;
		
		int storeIndex = left;
		for (int i = left; i < right; ++i) {
			if (Sort.less(a[i], a[pivot])) {
				Sort.exch(a, i, storeIndex);
				++storeIndex;
			}
		}
		Sort.exch(a, storeIndex, right);
		return storeIndex;
		
		/*int i = left, j = right-1;
		
		while (i < j) {
			// Find index i such that a[i] > a[pivot]
			while (i < j && Sort.less(a[i], a[pivot]))
				++i;
			
			// Find index j such that a[j] < a[pivot]
			while (i < j && Sort.less(a[pivot], a[j]))
				--j;
			
			if (i < j) {
				Sort.exch(a, i, j);
				++i; --j;
			}
		}
		// Need to make sure that i is the correct index to swap the pivot back into
		Sort.exch(a, i, pivot);
		
		return i;*/
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