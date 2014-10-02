import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class RunSort {
	public static enum Algorithm{Selection, Merge, Heap, Quick};
	
	public static void main(String[] args) {
		// parse CLI arguments
		Algorithm alg = Algorithm.Selection;
		int d = -1;
		int iterations = 1;
		for (int i = 0; i < args.length; i++) {
			switch(args[i]) {
			case "-h" :
			case "--help" :
				StdOut.println("usage: Sort [options] [< data]");
				StdOut.println("-h,--help\tprint help and exit");
				StdOut.println("-l <locality>\tuse locality-aware sorting");
				StdOut.println("-a <algorithm>\tuse specified algorithm (default = selection)");
				StdOut.println("-f <file>\tread data from file (default = StdIn)");
				return;
			case "-l" :
				try {
					d = Integer.parseInt(args[++i]);
				} catch (NumberFormatException e) {
					StdOut.println("ERROR: locality ("+args[i]+") must be an integer.");
					return;
				}
				if (d < 0) {
					StdOut.println("ERROR: locality ("+d+") must be non-negative.");
					return;
				}
				break;
			case "-a" :
				switch(args[++i].toLowerCase()) {
				case "selection" :
				case "s" :
					alg = Algorithm.Selection;
					break;
				case "merge" :
				case "m" :
					alg = Algorithm.Merge;
					break;
				case "heap" :
				case "h" :
					alg = Algorithm.Heap;
					break;
				case "quick":
				case "q":
					alg = Algorithm.Quick;
					break;
				default :
					StdOut.println("ERROR: unknown algorithm ("+args[i]+").");
					return;
				}
				break;
			case "-f" :
				String file = args[++i];
				FileInputStream in = null;
				try {
					in = new FileInputStream(new File(file));
				} catch (FileNotFoundException e) {
					StdOut.println("ERROR: file ("+file+") not found.");
					return;
				}
				System.setIn(in);
				break;
			case "-i" :
				try {
					iterations = Integer.parseInt(args[++i]);
				} catch (NumberFormatException e) {
					StdOut.println("ERROR: iterations ("+args[i]+") must be an integer.");
					return;
				}
				if (iterations < 1) {
					StdOut.println("ERROR: iterations ("+d+") must be at least 1.");
					return;
				}
				break;
			default :
				StdOut.println("ERROR: unknown option ("+args[i]+")");
				return;
			}
		}
		
		
		
		// read all data
		Comparable[] data = null;
		int[] int_data = StdIn.readAllInts();
		data = new Comparable[int_data.length];
		for (int i = 0; i < int_data.length; i++)
			data[i] = int_data[i];
		
		// Make multiple copies of the data
		//Comparable[] duplicateData = Arrays.copyOf(data, data.length);
		//for (int i = 0; i < 2; ++i)
			//duplicateData[i] = Arrays.copyOf(data, data.length);
		
		long total1 = System.nanoTime();

		// run sort on data
		// double[] runTimes = new double[iterations];
		// double totalRuntime = 0;
		// Comparable[] duplicateData = Arrays.copyOf(data, data.length);

		for (int i = 0; i < iterations; ++i) {
			if (ThreadSorts.activeCount() < ThreadSorts.MAX_THREADS)
				(new ThreadSorts(Arrays.copyOf(data, data.length), iterations, alg, d, i + "")).start();
			/*else {
				Comparable[] duplicateData = Arrays.copyOf(data, data.length);
				
				long t1 = System.nanoTime();
				if (d >= 0) {
					// use locality-aware algorithms
					switch (alg) {
					default:
					case Selection:
						LSelection.sort(duplicateData, d);
						break;
					case Merge:
						LMerge.sort(duplicateData, d);
						break;
					case Heap:
						LHeap.sort(duplicateData, d);
						break;
					case Quick:
						// No location-aware considered for Quicksort
						Quick.sort(duplicateData);
						break;
					}
				} else {
					// use general algorithms
					switch (alg) {
					default:
					case Selection:
						Selection.sort(duplicateData);
						break;
					case Merge:
						Merge.sort(duplicateData);
						break;
					case Heap:
						Heap.sort(duplicateData);
						break;
					case Quick:
						Quick.sort(duplicateData);
						break;
					}
				}
				long t2 = System.nanoTime();

				if (!Sort.isSorted(duplicateData)) {
					StdOut.print("Sort failed.");
					return;
				}

				double millis = (t2 - t1) / 1000000.0;
				ThreadSorts.runTimes[i] = millis;*/
				//totalRuntime += millis;

				//duplicateData = Arrays.copyOf(data, data.length);
			//}
		}
		
		long total2 = System.nanoTime();
		
		try {
			synchronized (ThreadSorts.threads[7]) {
				ThreadSorts.threads[7].join(); // Wait until the last thread is finished
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double totalRuntime = 0;
		for (int i = 0; i < iterations; ++i)
			totalRuntime += ThreadSorts.runTimes[i];
			
		// StdOut.printf("%.4f\n",millis);
		double mean = totalRuntime / iterations;
		double variance = 0;
		for (int i = 0; i < iterations; ++i) {
			variance += Math.pow((ThreadSorts.runTimes[i] - mean), 2) ;//* (runTimes[i] - mean);
			StdOut.printf("Run %d: %.4f ms\n", i + 1, ThreadSorts.runTimes[i]);
		}
		variance /= (iterations - 1);
		double std = Math.sqrt(variance);
		
		// finished al calculation, so can end total running time
		
		
		StdOut.printf("Mean = %.4f\n", mean);
		StdOut.printf("Variance = %.4f\n", variance);
		StdOut.printf("Standard Deviation = %.4f\n", std);
		System.out.printf("Total Running Time: %.2f ms\n", (total2 - total1) / 1000000.0);
		
		System.setIn(System.in);
	}
	
	
}

class ThreadSorts extends Thread {
	final static int MAX_THREADS = Runtime.getRuntime().availableProcessors();
	public static double[] runTimes = null;
	public static int iterations = 0;
	public static ThreadSorts[] threads = null;
	
	private Comparable[] data;
	private RunSort.Algorithm alg;
	private int d;
	
	public ThreadSorts(Comparable[] data, int iters, RunSort.Algorithm algorithm, int locality, String number) {
		super(number);
		this.data = data;
		alg = algorithm;
		d = locality;
	
		if (iterations == 0) 
			iterations = iters;
		if (runTimes == null) 
			runTimes = new double[iterations];
		if (threads == null)
			threads = new ThreadSorts[iterations];
	}
	
	@Override
	public void start() {
		this.run();
	}
	
	@Override
	public void run() {
		int id = Integer.parseInt(super.getName());
		synchronized (threads) {
			threads[id] = this;
		}
		
		long t1 = System.nanoTime();
		if (d >= 0) {
			// use locality-aware algorithms
			switch (alg) {
			default:
			case Selection:
				LSelection.sort(data, d);
				break;
			case Merge:
				LMerge.sort(data, d);
				break;
			case Heap:
				LHeap.sort(data, d);
				break;
			case Quick:
				// No location-aware considered for Quicksort
				Quick.sort(data);
				break;
			}
		} else {
			// use general algorithms
			switch (alg) {
			default:
			case Selection:
				Selection.sort(data);
				break;
			case Merge:
				Merge.sort(data);
				break;
			case Heap:
				Heap.sort(data);
				break;
			case Quick:
				Quick.sort(data);
				break;
			}
		}
		long t2 = System.nanoTime();
		
		if (!Sort.isSorted(data)) {
			StdOut.print("Sort failed.");
			return;
		}
		
		double millis = (t2 - t1) / 1000000.0;
		
		synchronized (runTimes) {
			runTimes[id] = millis;
		}
	}
}


