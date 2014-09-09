package clinic;

/**
 * A Patient queue implementation using a dynamically-sized circular array.
 * 
 * @author ghousto
 */
public class MyPatientQueue{
	// instance variables
	// declare instance variables
	// -----
	private Patient[] patientArray;
	private int head;
	private int tail;
	private int numOfPatients;

	// constructor
	public MyPatientQueue() {
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
		// return the number of patients in the queue
		return numOfPatients;
		// -----
	}

	/**
	 * add patient to end of queue.
	 * @param p - Patient to add to queue
	 */
	public void enqueue(Patient p) {
		// add patient to end of queue
		// resize array, if needed
		// -----
		
		patientArray[tail++] = p;
		tail %= patientArray.length;
		++numOfPatients;
		
		// If head == tail during an enqueue, then the array must be full and must be resized
		if (head == tail || numOfPatients == patientArray.length)
			changeArraySize(false);
	}

	/**
	 * remove and return next patient from the queue
	 * @return patient at front of queue, null if queue is empty
	 */
	public Patient dequeue() {
		// remove and return the patient at the head of the queue
		// resize array, if needed
		
		Patient patientToReturn = patientArray[head++];
		head %= patientArray.length;
		--numOfPatients;
		
		// If the number of patients is <= 1/4th the size of the patient array, resize it
		if (numOfPatients <= patientArray.length / 4)
			changeArraySize(true);
		
		return patientToReturn;
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
	 * @param decreaseArraySize - variable determining whether you shrink the array or grow it
	 * 
	 * I only used a single method because the implementation for copying the array elements into the
	 * new array was exactly the same for both shrinking and growing the array. The only difference
	 * was the size of the new array.
	 */
	private void changeArraySize(boolean decreaseArraySize) {
		// Protect against shrinking the array when it is too small so that it this condition doesn't need to
		// be checked in other places in the code
		if (decreaseArraySize && patientArray.length <= 7)
			return;
		
		Patient[] newPatientArray;
		if (decreaseArraySize) {
			// Don't allow the array size to become less than 7
			newPatientArray = new Patient[patientArray.length / 2 > 7 ? patientArray.length / 2 : 7];
		}
		else {
			newPatientArray = new Patient[patientArray.length * 2];
		}
		
		// Copy the contents of the patient array into the new patient array
		int i;
		for (i = 0; i < numOfPatients; ++head, ++i) {
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


