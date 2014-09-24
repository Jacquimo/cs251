import java.util.*;

public class TestQSort {

	public static void main(String[] args) {
		Comparable[] a = new Comparable[16];
		Random g = new Random();
		for (int i = 0; i < a.length; ++i)
			a[i] = g.nextInt(19) + 1;
		
		printArray(a, -1, 0, a.length - 1);
		Quick.sort(a);
		System.out.printf("The final array after calling quicksort is:\n");
		printArray(a, -1, 0, a.length - 1);
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
}
