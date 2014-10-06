

/**
 * Locality-Aware Merge Sort
 * 
 * @author ghousto
 * @version 10/3/14
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
		//Comparable[] aux = Arrays.copyOf(a, a.length); 
		mergesort(a, 0, a.length - 1, d);
		//merge(aux, a, 0, a.length/2, a.length-1);
		//Sort.show(a);
	}
	
	/**
	 * 
	 * @param a - the array
	 * @param left - starting index of left part of array to sort
	 * @param right - starting index of right part of array to sort
	 * @param d - locality condition: element at k is at most d positions from its final position
	 */
	public static void mergesort(Comparable[] a, int left, int right, int d) {
		if (right <= left) return;
		
		if (right - left <= 16) {
			insertionSort(a, left, right);
			return;
		}
		
		int middle = (left + right) / 2;
		mergesort(a, left, middle, d);
		mergesort(a, middle+1, right, d);
		merge(a, middle - d >= left ? middle - d : left, middle, middle + d <= right ? middle + d : right, true);
		
	}
	
	public static void merge(Comparable[] a, int left, int mid, int right) {
		// The folowing code is an algorithm I created my self which I call "Relevant Range". The
		// boundaries of the range are expanded until the value at the left boundary is the largest
		// value on the left smaller than the smallest element in the right subarray and the value
		// at the right boundary is the smallest number on the right greater than the largest element
		// in the left subarray. The values wihtin the two boundaries are the only values that actually
		// need to be be merged together, significantly reducing running time.
		
		int leftCounter = left, rightCounter = mid + 1;
		int leftRelevantRange = mid , rightRelevantRange = mid+1;
		
		
		while (leftRelevantRange > left && a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) {
			--leftRelevantRange;
		}
	
		while(rightRelevantRange < right && a[rightRelevantRange].compareTo(a[mid]) <= 0)
			++rightRelevantRange;
	
		merge(a, leftRelevantRange, mid, rightRelevantRange, true);
		
		
		
		// The following merge implementation is a combination of the relevant range algorithm used
		// above and the "standard" in-place merge. It is nearly as fast as the purley relevant range
		// algorithm shown above, yet the extra comparisons (likely) slow it down.
		
		// It is useful for the worst-case scenario, so that if no relevant range can be found, the
		// array will still get sorted in a similar amount of time as simply calling the in-placec sort

		/*while (leftCounter <= mid && rightCounter <= right) {
			// Expand the bounds of the relevant range
			// Unless both of the boundaries have been found, expand both boundaries outwards
			if (a[rightCounter].compareTo(a[leftRelevantRange]) <= 0 || a[rightRelevantRange].compareTo(a[mid]) <= 0) {
				if (leftRelevantRange > left) --leftRelevantRange;
				if (rightRelevantRange < right) ++rightRelevantRange;
			}
			else { // both boundaries have been found
				break;
			}
	
			// Perform normal calculations/adjustments for in-place merge
			if (a[leftCounter].compareTo(a[rightCounter]) <= 0)
				++leftCounter;
			else { 
				Comparable elementToMove = a[rightCounter];
				// Shift over the elements starting at leftCounter (inclusive) ending at
				// rightCounter (exclusive) by 1 index to the right
				System.arraycopy(a, leftCounter, a, leftCounter+1, rightCounter-leftCounter);
				a[leftCounter] = elementToMove;
				++leftCounter; ++mid; ++rightCounter;
			}
		}
		// At this point, check if the relevant range has been found, otherwise the arrays have been merged
		//if (leftCounter <= mid && rightCounter <= right)
		merge(a, leftRelevantRange, mid, rightRelevantRange, true);*/
	}
	
	/**
	 * Merges two subarrays in place
	 * @param a - the arry
	 * @param left - left boundary of the left subarray
	 * @param mid - midpoint, the (inclusive) right end of the left subarray
	 * @param right - the right hand (inclusive) boundary of the right subarray
	 * @param foundRelevantRange - flag used for method overloading
	 */
	public static void merge(Comparable[] a, int left, int mid, int right, boolean foundRelevantRange) {
		if (a[mid].compareTo(a[mid+1]) <= 0) return;
		int leftCounter = left, rightCounter = mid + 1, leftRelevantRange = mid - 1;
		
		while (leftCounter <= mid && rightCounter <= right) {
			if (a[leftCounter].compareTo(a[rightCounter]) <= 0)
				++leftCounter;
			else { 
				Comparable elementToMove = a[rightCounter];
				System.arraycopy(a, leftCounter, a, leftCounter+1, rightCounter-leftCounter);
				a[leftCounter] = elementToMove;
				++leftCounter; ++mid; ++rightCounter;
			}
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
