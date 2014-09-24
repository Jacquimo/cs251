import java.util.Random;
import java.util.concurrent.*;


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
public class Quick extends Sort implements Runnable {
	/**
	 * this class should not be instantiated
	 */
	private Quick(Comparable[] a, int left, int right) {
		this.array = a;
		this.l = left;
		this.r = right;
	}
	private Comparable[] array;
	private int l;
	private int r;

	/**
	 * sort the array
	 * @param a - array
	 */
	public static void sort(Comparable[] a) {
		quicksort(a, 0, a.length - 1);
		//TestQSort.qsort(a, 0, a.length-1);
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
			//quicksort(a, left, pivot-1);
			//quicksort(a, pivot+1, right);
			
			if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2)
				new ThreadQuick(a, left, pivot-1).start();
			else
				quicksort(a, left, pivot-1);
			
			if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2)
				new ThreadQuick(a, pivot+1, right).start();
			else
				quicksort(a, pivot+1, right);
		}
		else {
			//quicksort(a, pivot+1, right);
			//quicksort(a, left, pivot-1);
			
			if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2)
				new ThreadQuick(a, pivot+1, right).start();
			else
				quicksort(a, pivot+1, right);
			
			if (ThreadQuick.num_threads < ThreadQuick.MAX_THREADS * 2)
				new ThreadQuick(a, left, pivot-1).start();
			else
				quicksort(a, left, pivot-1);
		}
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
		
		/*int storeIndex = left;
		for (int i = left; i < right; ++i) {
			if (Sort.less(a[i], a[pivot])) {
				Sort.exch(a, i, storeIndex);
				++storeIndex;
			}
		}
		Sort.exch(a, storeIndex, right);
		return storeIndex;*/
		
		int i = left, j = right-1;
		
		while (true) {
			// Find index i such that a[i] > a[pivot]
			//while (i < pivot && Sort.less(a[i], a[pivot]))
			while (i < pivot && a[i].compareTo(a[pivot]) <= 0)
				++i;
			
			// Find index j such that a[j] < a[pivot]
			//while (j > left && Sort.less(a[pivot], a[j]))
			while (j > left && a[pivot].compareTo(a[j]) <= 0)
				--j;
			
			if (i < j)
				Sort.exch(a, i, j);
			else
				break;
		}
		// Need to make sure that i is the correct index to swap the pivot back into
		if (i == pivot) return pivot;
		Sort.exch(a, i, pivot); 
		return i;
		
		/*if (Sort.less(a[pivot], a[i])) { 
			Sort.exch(a, i, pivot); 
			return i; }
		else if (Sort.less(a[pivot], a[j])) {
			Sort.exch(a, j, pivot);
			return j;
		}
		else
			return pivot;*/
		
		//return i;
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

	@Override
	public void run() {
		quicksort(array, l, r);
	}
}

class ThreadQuick extends Thread {
	static int MAX_THREADS = Runtime.getRuntime().availableProcessors();
	//static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
	public static int num_threads = 0;
	
	private Comparable[] array;
	private int left;
	private int right;
	
	public ThreadQuick(Comparable[] a, int l, int r) {
		/*synchronized((Integer) MAX_THREADS) {
			MAX_THREADS = Runtime.getRuntime().availableProcessors();
		}*/
		
		array = a;
		left = l;
		right = r;
	}

	@Override
	public void run() {
		++num_threads;
		Quick.quicksort(array, left, right);
		--num_threads;
	}	
	
	public void start() {
		this.run();
	}
}