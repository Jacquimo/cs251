import java.util.Random;

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
		// quicksort(a, 0, a.length - 1);

		/*int lengthOfDiv = a.length / ThreadQuick.MAX_THREADS;
		ThreadQuick[] threads = new ThreadQuick[ThreadQuick.MAX_THREADS + 2];
		for (int i = 0; i < ThreadQuick.MAX_THREADS; ++i) {
			threads[i] =  new ThreadQuick(a, i * lengthOfDiv, (i + 1) * lengthOfDiv - 1, false);
			threads[i].start();
		}
		
		boolean finishedLeftHalf = false;
		boolean finishedRightHalf = false;
		int indexOfNewestThread = 4;
		// Utilize fact that the max number of threads will be 4 unless changed elsewhere
		// Combine the 4 spearately sorted sections into 2 half of the array
		while (true) {
			if (!finishedLeftHalf && !threads[0].isAlive() && !threads[1].isAlive()) {
				threads[indexOfNewestThread] = new ThreadQuick(a, 0, lengthOfDiv * 2 - 1, false);
				threads[indexOfNewestThread++].start();
				finishedLeftHalf = true;
				// If the value is > 5, then the other helper thread has been instantiated and we
				// need to break out of the infinite loop
				if (indexOfNewestThread > 5) break;
			}
			
			if (!finishedRightHalf && !threads[2].isAlive() && !threads[3].isAlive()) {
				threads[indexOfNewestThread] = new ThreadQuick(a, lengthOfDiv * 2, a.length - 1, false);
				threads[indexOfNewestThread++].start();
				finishedRightHalf = true;
				// Use duplicate if statement (as above) on purpose instead of moving it out a level
				// If the if statement were out a level, there would be significantly more comparisons
				if (indexOfNewestThread > 5) break;
			}
		}
		
		//
		do {
			
		} while (true);*/
		
		int lengthOfDiv = a.length / ThreadQuick.MAX_THREADS;
		ThreadQuick[] threads = new ThreadQuick[ThreadQuick.MAX_THREADS + 2];
		for (int i = 0; i < ThreadQuick.MAX_THREADS; ++i) {
			System.out.printf("Number of active threads: %d\n", Thread.activeCount());
			threads[i] =  new ThreadQuick(a, i * lengthOfDiv, (i + 1) * lengthOfDiv - 1, false);
			threads[i].start();
		}
		
		// Wait for the last thread to finish
		try {
			threads[3].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		threads[4] = new ThreadQuick(a, 0, lengthOfDiv*2 - 1, true); threads[4].start();
		threads[5] = new ThreadQuick(a, lengthOfDiv*2, a.length-1, true); threads[5].start();
		try {
			threads[4].join();
			threads[5].join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		threads[0] = new ThreadQuick(a, 0, a.length - 1, true); threads[0].start();
		
		try {
			threads[0].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Sort.show(a);
	}

	public static void quicksort(Comparable[] a, int left, int right) {
		// Base case
		if (left >= right)
			return;

		// Optimize code so that all small subarrays are sorted using insertion
		// sort
		if (right - left <= 16) {
			Quick.insertionSort(a, left, right);
			return;
		}

		// Randomly generate the index of the pivot
		Random generator = new Random();
		int pivot = generator.nextInt(right - left + 1) + left;
		pivot = Quick.partition(a, pivot, left, right);

		int lengthLeft = pivot - left;
		int lengthRight = right - pivot;

		// Optimize code to sort shorter side first
		if (lengthLeft < lengthRight) {
			Quick.quicksort(a, left, pivot - 1);
			Quick.quicksort(a, pivot + 1, right);

			/*
			 * if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2) new
			 * ThreadQuick(a, left, pivot-1).start(); else quicksort(a, left,
			 * pivot-1);
			 * 
			 * if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2) new
			 * ThreadQuick(a, pivot+1, right).start(); else quicksort(a,
			 * pivot+1, right);
			 */
		} else {
			Quick.quicksort(a, pivot + 1, right);
			Quick.quicksort(a, left, pivot - 1);

			/*
			 * if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2) new
			 * ThreadQuick(a, pivot+1, right).start(); else quicksort(a,
			 * pivot+1, right);
			 * 
			 * if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2) new
			 * ThreadQuick(a, left, pivot-1).start(); else quicksort(a, left,
			 * pivot-1);
			 */
		}
	}

	/**
	 * Partition a region of the array based upon the value of a given pivot
	 * index
	 * 
	 * @param a
	 *            - the array
	 * @param pivot
	 *            - the index of the pivot
	 * @param left
	 *            - starting index of region of array to be partitioned
	 * @param right
	 *            - ending index of region of array to be partitioned
	 * @return the final position of the pivot
	 */
	public static int partition(Comparable[] a, int pivot, int left, int right) {
		Sort.exch(a, pivot, right);
		pivot = right;

		/*
		 * int storeIndex = left; for (int i = left; i < right; ++i) { if
		 * (Sort.less(a[i], a[pivot])) { Sort.exch(a, i, storeIndex);
		 * ++storeIndex; } } Sort.exch(a, storeIndex, right); return storeIndex;
		 */

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

		/*
		 * if (Sort.less(a[pivot], a[i])) { Sort.exch(a, i, pivot); return i; }
		 * else if (Sort.less(a[pivot], a[j])) { Sort.exch(a, j, pivot); return
		 * j; } else return pivot;
		 */

		// return i;
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

class ThreadQuick extends Thread {
	final static int MAX_THREADS = Runtime.getRuntime().availableProcessors();
	// static final ExecutorService executor =
	// Executors.newFixedThreadPool(MAX_THREADS);
	public static int num_threads = 0;

	private Comparable[] array;
	private int left;
	private int right;
	private boolean merge;

	public ThreadQuick(Comparable[] a, int l, int r, boolean m) {
		/*
		 * synchronized((Integer) MAX_THREADS) { MAX_THREADS =
		 * Runtime.getRuntime().availableProcessors(); }
		 */

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
}