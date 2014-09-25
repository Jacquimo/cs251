import java.util.*;

public class TestQSort {
	public static boolean aIsA = true;
	public static boolean dontPrint = false;
	
	public static void main(String[] args) {
		Comparable[] a = new Comparable[20];
		Random g = new Random();
		for (int i = 0; i < a.length; ++i)
			a[i] = g.nextInt(20) + 1;
		
		/*printArray(a, -1, 0, a.length - 1);
		Quick.sort(a);
		System.out.printf("The final array after calling quicksort is:\n");
		printArray(a, -1, 0, a.length - 1);*/
		
		printArray(a, null, 0, a.length-1);
		aIsA = false;
		LMerge.sort(a, a.length-1);
		//printArray(a, null, 0, a.length-1);
		System.out.printf("%s\n", Sort.isSorted(a) ? "Sorted" : "Sort Failed");
	}
	
	public static void printArray(Comparable[] a, Comparable[] aux, int left, int right, int timing) {
		if (dontPrint) return;
		
		switch (timing) {
		case 0: System.out.printf("Before left sort\n"); break;
		case 1: System.out.printf("After left sort, before right sort\n"); break;
		case 2: System.out.printf("After right sort\n"); break;
		case 3: System.out.printf("After merge\n"); break;
		case 4: System.out.printf("Array contains specified element\n"); break;
		default: System.out.printf("The array within indices %d - %d, inclusive\n", left, right);
		}
		//System.out.printf("%s\t%s\n", aIsA ? "A" : "Aux" , aIsA ? "Aux" : "A" );
		/*for (int i = left; i <= right; ++i)
			System.out.printf("%d\t%d\n", a[i], aux!= null ? aux[i] : -1);*/
		LMerge.show(aux,  left, right);
	}
	
	public static void printArray(Comparable[] a, Comparable[] aux, int left, int right) {
		printArray(a, aux, left, right, -1);
	}
	
	public static void printArray(Comparable[] a, int pIndex, int left, int right) {
		if (pIndex >= 0)
			System.out.printf("Pivot Index = %d\tPivot Val = %d\n", pIndex, a[pIndex]);
		
		for(int i = left; i <= right; ++i) {
			System.out.printf("%d", a[i]);
			if (i == pIndex)
				System.out.printf(" <-- Pivot");
			System.out.printf("\n");
		}
	}
	
	public static void qsort(Comparable[] a, int left, int right) {
		if (left >= right) return;
		int pivotIndex = (int)((right - left + 1) * Math.random() + left); // random pivot index
		pivotIndex = partition(a, pivotIndex, left, right);
		qsort(a, left, pivotIndex-1);
		qsort(a, pivotIndex+1, right);
	}
	
	public static int partition(Comparable[] a, int pIndex, int left, int right) {
		// move the pivot over to the right hand index
		Comparable pValue = a[pIndex];
		Sort.exch(a, pIndex, right);
		pIndex = right;
		
		// look for elements less than the pivot value (that are on the right hand side) and move them to the left
		int storeIndex = left;
		for (int i = left; i < pIndex; ++i) {
			if (a[i].compareTo(pValue) < 0) {
				Sort.exch(a, i, storeIndex);
				++storeIndex;
			}
		}
		// put the pivot into its final position
		Sort.exch(a, storeIndex, pIndex);
		return storeIndex;
	}
	
	public static void msort(Comparable[] a, Comparable[] aux, int left, int right) {
		if (left >= right) return; // base case
		
		if (right - left <= 16) { // optimization
			insertionSort(a, left, right);
			return;
		}
		
		int mid = (right + left) / 2;
		msort(a, aux, left, mid);
		msort(a, aux, mid+1, right);
		merge(a, aux, left, mid, right);
	}
	
	public static void merge (Comparable[] a, Comparable[] aux, int left, int mid, int right) {
		// copy the subarray into the auxiliary array
		for (int i = left; i <= right; ++i)
			aux[i] = a[i];
		
		int l = left, r = mid+1;
		for (int i = left; i <= right; ++i) {
			if (l > mid) a[i] = aux[r++]; // if we used all the elements in the left array
			else if (r > right) a[i] = aux[l++]; // if we used all the elements in the right array
			else if (aux[r].compareTo(aux[l]) < 0) a[i] = aux[r++];
			else a[i] = aux[l++];
		}
	}
	
	public static void insertionSort(Comparable[] a, int left, int right) {
		for (int i = left; i <= right; i++) {
			for (int j = i; j > left && a[j].compareTo(a[j-1]) < 0; --j) {
				Sort.exch(a, j, j-1);
			}
		}
	}
}
