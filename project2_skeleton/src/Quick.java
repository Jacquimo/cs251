
/**
 * General Quick Sort
 * 
 * TODO This implementation uses the same logic as the one in the lecture slides
 * 
 * @author ghousto
 * @version 10/01/14
 * @pso P06
 *
 */
public class Quick extends Sort {
	/**
	 * this class should not be instantiated
	 */
	private Quick() {
	}

	/**
	 * sort the array
	 * 
	 * @param a
	 *            - array
	 */
	public static void sort(Comparable[] a) {		
		quicksort(a, 0, a.length - 1);
		//Sort.show(a);
		//qsort2pivot(a, 0, a.length - 1);
		//Sort.show(a);
	}

	public static void quicksort(Comparable[] a, int left, int right) {
		// Base case
		if (left >= right)
			return;

		// Optimize code so that all small subarrays are sorted using insertion
		// sort
		if (right - left <= 16) {
			insertionSort(a, left, right);
			return;
		}
		
		// Pick the middle value as the pivot. This value is likely to be (somewhat) close to the median value
		int pivot = partition(a, left + (right - left) / 2, left, right);


		int lengthLeft = pivot - left;
		int lengthRight = right - pivot;

		// Optimize code to sort shorter side first
		if (lengthLeft < lengthRight) {
			Quick.quicksort(a, left, pivot - 1);
			Quick.quicksort(a, pivot + 1, right);
		} else {
			Quick.quicksort(a, pivot + 1, right);
			Quick.quicksort(a, left, pivot - 1);
		}
	}

	
	/**
	 * Partition a region of the array based upon the value of a given pivot index
	 * 
	 * @param a - the array
	 * @param pivot - the index of the pivot
	 * @param left - starting index of region of array to be partitioned
	 * @param right - ending index of region of array to be partitioned
	 * @return the final position of the pivot
	 */
	public static int partition(Comparable[] a, int pivot, int left, int right) {
		Sort.exch(a, pivot, right);
		pivot = right;

		int i = left, j = right - 1;

		while (true) {
			// Find index i such that a[i] > a[pivot]
			// while (i < pivot && Sort.less(a[i], a[pivot]))
			while (i < pivot && a[i].compareTo(a[pivot]) <= 0)
				++i;

			// Find index j such that a[j] < a[pivot]
			// while (j > left && Sort.less(a[pivot], a[j]))
			while (j > left && a[pivot].compareTo(a[j]) <= 0)
				--j;

			if (i < j)
				Sort.exch(a, i, j);
			else
				break;
		}
		// Need to make sure that i is the correct index to swap the pivot back
		// into
		if (i == pivot)
			return pivot;
		Sort.exch(a, i, pivot);
		return i;
	}
	
	// Currently, this partitiioning method doesn't work yet. There appears to be an infinite loop somewhere
	public static int partition(Comparable[] a, Comparable pivot, int left, int right) {
		int i = left, j = right;

		while (true) {
			// Find index i such that a[i] > a[pivot]
			// while (i < pivot && Sort.less(a[i], a[pivot]))
			while (i < right && a[i].compareTo(pivot) < 0)
				++i;

			// Find index j such that a[j] < a[pivot]
			// while (j > left && Sort.less(a[pivot], a[j]))
			while (j > left && pivot.compareTo(a[j]) < 0)
				--j;

			if (i < j)
				Sort.exch(a, i, j);
			else
				break;
		}
		// Need to make sure that i is the correct index to swap the pivot back
		// into
		//if (i == right)
			//return right;
		//Sort.exch(a, i, pivot);
		return i;
	}

	public static void insertionSort(Comparable[] a, int left, int right) {
		for (int i = left; i <= right; ++i) {
			int j = i;
			while (j > left && Sort.less(a[j], a[j - 1])) {
				Sort.exch(a, j, j - 1);
				--j;
			}
		}
	}
	
	// Use the Dual-Pivot Quicksort that uses 2 pivots instead of the classical 1
	public static void qsort2pivot(Comparable[] a, int left, int right) {
		if (left >= right)
			return;
		
		/*if (right - left < 17) {
			insertionSort(a, left, right);
			return;
		}*/
		
		// Pick the 2 pivots so that they are about 1/3 and 2/3 of the way through the array
		int p1 = (right - left) / 3;
		int p2 = 2 * p1;
		
		// 1st pivot must be less than the 2nd pivot
		if (a[p2].compareTo(p1) < 0) {
			Sort.exch(a, p1, p2);
			int temp = p2;
			p2 = p1;
			p1 = temp;
		}
		
		// Move the pivots outside the searching area
		Sort.exch(a, left, p1);
		p1 = left;
		Sort.exch(a, p2, right);
		p2 = right;
		
		int less = left + 1;
		int mid = left + 1;
		int greater = right - 1;
		
		for (int i = mid; i < greater; ++i) {
			Comparable temp = a[i];
			if (a[i].compareTo(a[p1]) < 0) { // Division where element is less than pivot 1
				System.arraycopy(a, less, a, less+1, i - less);
				a[less] = temp;
				++less; ++mid;
			}
			else if (a[i].compareTo(a[p2]) < 0) {
				System.arraycopy(a, mid, a, mid+1, i - mid);
				a[mid] = temp;
				++mid;
			}
			else {
				System.arraycopy(a, i+1, a, i, greater - i);
				a[greater] = temp;
				--greater;
			}
		}
		
		Sort.exch(a, p1, less);
		Sort.exch(a, p2, greater);
		
		qsort2pivot(a, left, less - 1);
		qsort2pivot(a, less + 1, greater -1);
		qsort2pivot(a, greater + 1, right);
		
	}
	
	
	/**
	 * executes an in-place merge just like a merge in Mergesort
	 * @param a - the array containing the values
	 * @param left - leftmost index of values in left-hand group
	 * @param mid TODO
	 * @param right - rightmost index of values in right-hand group
	 */
	public static void merge(Comparable[] a, int left, int mid, int right) {
		//if (right <= mid) return;
		int leftCounter = left, rightCounter = mid + 1;
		int leftRelevantRange = mid , rightRelevantRange = mid+1;
		
		// Check to see if we can skip the merge
		// This check is beneficial since this method won't be called recursively (i.e. only add 1 extra comparison)
		/*if (a[mid].compareTo(a[rightCounter]) <= 0) 
			return;*/
		
		
		// The folowing code is an algorithm I created my self which I call "Relevant Range". The
		// boundaries of the range are expanded until the value at the left boundary is the largest
		// value on the left smaller than the smallest element in the right subarray and the value
		// at the right boundary is the smallest number on the right greater than the largest element
		// in the left subarray. The values wihtin the two boundaries are the only values that actually
		// need to be be merged together, significantly reducing running time.
		
		
		while (leftRelevantRange > left && a[rightCounter].compareTo(a[leftRelevantRange]) <= 0) {
			--leftRelevantRange;
		}
	
		while(rightRelevantRange < right && a[rightRelevantRange].compareTo(a[mid]) <= 0)
			++rightRelevantRange;
	
		Quick.merge(a, leftRelevantRange, mid, rightRelevantRange, true);
		
	}
	
	
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
}

/*class ThreadQuick extends Thread {
	final static int MAX_THREADS = Runtime.getRuntime().availableProcessors();
	// static final ExecutorService executor =
	// Executors.newFixedThreadPool(MAX_THREADS);
	public static int num_threads = 0;

	private Comparable[] array;
	private int left;
	private int right;
	private boolean merge;

	public ThreadQuick(Comparable[] a, int l, int r, boolean m) {
		
		 //synchronized((Integer) MAX_THREADS) { MAX_THREADS =
		 //Runtime.getRuntime().availableProcessors(); }
		 

		array = a;
		left = l;
		right = r;
		merge = m;
	}

	@Override
	public void run() {
		if (!merge) {
			//++ThreadQuick.num_threads;
			Quick.quicksort(array, left, right);
			//--ThreadQuick.num_threads;
		}
		else {
			Quick.merge(array, left, (left + right) / 2 , right);
		}
	}

	@Override
	public void start() {
		super.run();
	}
}*/