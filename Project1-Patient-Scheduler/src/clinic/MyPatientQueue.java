package clinic;

/**
 * A Patient queue implementation using a dynamically-sized circular array.
 * 
 * @author TODO
 */
public class MyPatientQueue{
	// instance variables
	// TODO ATTENTION: CODE NEEDED HERE
	// declare instance variables
	// -----

	// constructor
	public MyPatientQueue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// initialize instance variables
		// -----
	}

	// functions
	/**
	 * @return the number of patients in the queue
	 */
	public int size() {
		// TODO ATTENTION: CODE NEEDED HERE
		// return the number of patients in the queue
		return -1;
		// -----
	}

	/**
	 * add patient to end of queue.
	 * @param p - Patient to add to queue
	 */
	public void enqueue(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add patient to end of queue
		// resize array, if needed
		// -----
	}

	/**
	 * remove and return next patient from the queue
	 * @return patient at front of queue, null if queue is empty
	 */
	public Patient dequeue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return the patient at the head of the queue
		// resize array, if needed
		return null;
		// -----
	}

	/**
	 * return, but do not remove, the patient at index i
	 * @param i - index of patient to return
	 * @return patient at index i, or null if no such element
	 */
	public Patient get(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// return, but do not remove, the patient at index i
		return null;
		// -----
	}

	/**
	 * add patient to front of queue
	 * @param p - patient being added to queue
	 */
	public void push(Patient p) {
		// TODO ATTENTION: CODE NEEDED HERE
		// add Patient p to front of queue
		// resize array, if needed
		// -----
	}

	/**
	 * remove and return patient at index i from queue
	 * @param i - index of patient to remove
	 * @return patient at index i, null if no such element
	 */
	public Patient dequeue(int i) {
		// TODO ATTENTION: CODE NEEDED HERE
		// remove and return Patient at index i from queue
		// shift patients down to fill hole left by removed patient
		// resize array, if needed
		return null;
		// -----
	}
}
