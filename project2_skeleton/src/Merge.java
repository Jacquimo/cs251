



/**
 * General Merge Sort
 * 
 * TODO this code is from the implementation given in the lecture slides
 * 
 * @author ghousto
 * @version 9/25/14
 * @pso   P06
 *
 */
public class Merge extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Merge() {}

	/**
	 * sort the array
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		//mergesort(a, new Comparable[a.length], 0, a.length - 1);
		//Merge.mergesort(a, Arrays.copyOf(a, a.length), 0, a.length - 1);
		//TestQSort.msort(a, new Comparable[a.length], 0, a.length - 1);
		
		Merge.mergesort(a, null, 0, a.length - 1);
	}
	
	/**
	 * 
	 * @param a - the array
	 * @param aux - the auxiliary helper array
	 * @param left - starting index of left part of array to sort
	 * @param right - starting index of right part of array to sort
	 */
	public static void mergesort(Comparable[] a, Comparable[] aux, int left, int right) {
		if (right <= left) return;
		
		if (right - left <= 16) {
			Merge.insertionSort(a, left, right);
			return;
		}
		
		int middle = (left + right) / 2;
		
		/*mergesort(a, aux, left, middle);
		mergesort(a, aux, middle+1, right);
		merge(a, aux, left, middle, right);*/
		
		// Optimization that eliminates the copy to aux in the merge
		// Assumes that aux has already been initialized as a complete (and full) copy of a
		/*Merge.mergesort(aux, a, left, middle);
		Merge.mergesort(aux, a, middle+1, right);
		Merge.merge(aux, a, left, middle, right);*/
		
		Merge.mergesort(a, null, left, middle);
		Merge.mergesort(a, null, middle+1, right);
		Quick.merge(a, left, middle, right);
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
}
