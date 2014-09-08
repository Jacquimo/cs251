package clinic;

/**
 * A Patient queue implementation using a dynamically-sized circular array.
 * 
 * @author ghousto
 */
public class MyPatientQueue{
	// instance variables
	// TODO ATTENTION: CODE NEEDED HERE
	// declare instance variables
	// -----
	private Patient[] patientArray;
	private int head;
	private int tail;
	private int numOfPatients;

	// constructor
	public MyPatientQueue() {
		// TODO ATTENTION: CODE NEEDED HERE
		// initialize instance variables
		// -----
		patientArray = new Patient[7];
		head = 0;
		tail = 0;
		numOfPatients = 0;
	}

	// functions
	/**
	 * @return the number of patients in the queue
	 */
	public int size() {
		// TODO ATTENTION: CODE NEEDED HERE
		// return the number of patients in the queue
		return numOfPatients;
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
	
	/**
	 * copy the array elements into a new, larger array and store the new array as the patientArray
	 */
	private void increaseArraySize() {
		Patient[] newPatientArray = new Patient[patientArray.length * 2];
		
		// Copy the contents of the patient array into the new patient array
		int i;
		for (i = 0; i < patientArray.length; ++head, ++i) {
			// Call modulus operation to account for when head increments outside the length of the array
			head = head % patientArray.length;
			newPatientArray[i] = patientArray[head];
		}
		
		// Reset the head & tail variables to point to the correct indices and reassign the patient array
		head = 0;
		tail = i;
		patientArray = newPatientArray;
	}
}
