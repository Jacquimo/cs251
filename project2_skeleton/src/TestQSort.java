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
}
