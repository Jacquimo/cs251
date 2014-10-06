



/**
 * General Merge Sort
 * 
 * TODO this code is from the implementation given in the lecture slides
 * 
 * @author ghousto
 * @version 10/2/14
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
		
		Merge.mergesort(a, 0, a.length - 1);
	}
	
	/**
	 * 
	 * @param a - the array
	 * @param left - starting index of left part of array to sort
	 * @param right - starting index of right part of array to sort
	 */
	public static void mergesort(Comparable[] a, int left, int right) {
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
		
		// Calling Mergesort without having to use an auxiliary array
		Merge.mergesort(a, left, middle);
		Merge.mergesort(a, middle+1, right);
		merge(a, left, middle, right, true);
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
		
		
		/*while (leftRelevantRange > left && a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) {
			--leftRelevantRange;
		}
	
		while(rightRelevantRange < right && a[rightRelevantRange].compareTo(a[mid]) <= 0)
			++rightRelevantRange;
	
		merge(a, leftRelevantRange, mid, rightRelevantRange, true);*/
		
		
		
		
		
		/*while (((leftRelevantRange > left) && a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) || 
		((rightRelevantRange < right) && a[rightRelevantRange].compareTo(a[mid]) <= 0)) {
		
			if (a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) {
				//--leftRelevantRange;
				leftRelevantRange -= (leftRelevantRange - left) / 4;
			}
			else {
				++leftRelevantRange;
			}
			if (a[rightRelevantRange].compareTo(a[mid]) <= 0) {
				//++rightRelevantRange;
				rightRelevantRange += (right - rightRelevantRange) / 4;
			}
			else {
				--rightRelevantRange;
			}
		}

		merge(a, leftRelevantRange, mid, rightRelevantRange, true);*/







		/*while (((leftRelevantRange > left) && a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) || 
		((rightRelevantRange < right) && a[rightRelevantRange].compareTo(a[mid]) <= 0)) {
		
			if (leftRelevantRange > left)
				--leftRelevantRange;
	
			if (rightRelevantRange < right)
				++rightRelevantRange;
		}

		merge(a, leftRelevantRange, mid, rightRelevantRange, true);*/






		// The following merge implementation is a combination of the relevant range algorithm used
		// above and the "standard" in-place merge. It is nearly as fast as the purley relevant range
		// algorithm shown above, yet the extra comparisons (likely) slow it down.
		
		// It is useful for the worst-case scenario, so that if no relevant range can be found, the
		// array will still get sorted in a similar amount of time as simply calling the in-placec sort

		while (leftCounter <= mid && rightCounter <= right) {
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
		merge(a, leftRelevantRange, mid, rightRelevantRange, true);




		/*boolean leftBoundFound  = false;
		boolean rightBoundFound = false;

		// works on the assumption that the relevant range will be found before the left subarray is traversed
		while (leftCounter <= mid && rightCounter <= right) {
			// Expand the bounds of the relevant range
			// expand left bound
			if (!leftBoundFound) {
				if (a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) {
					if (leftRelevantRange == left)
						leftBoundFound = true;
					--leftRelevantRange;
				}
				else {
					if (rightBoundFound) // the whole relevant range has been found
						break;
					else
						leftBoundFound = true;
				}
			}
			// expand right bound
			if (!rightBoundFound) {
				if (a[rightRelevantRange].compareTo(a[mid]) <= 0) {
					if (rightRelevantRange == right)
						rightBoundFound = true;
					++rightRelevantRange;
				}
				else {
					if (leftBoundFound)
						break; // the whole relevant range has been found
					else {
						rightBoundFound = true;
					}
				}
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
		if (leftCounter <= mid && rightCounter <= right)
			merge(a, leftRelevantRange, mid, rightRelevantRange, true);*/




		/*while (leftCounter <= mid && rightCounter <= right) {
			if (a[leftRelevantRange].compareTo(a[rightRelevantRange]) <= 0) {
				Quick.mergeRelevantRange(a, leftRelevantRange, mid, rightRelevantRange, true);
				return;
			}
			else {
				--leftRelevantRange;
				++rightRelevantRange;
			}
	
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
		}*/
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
}
