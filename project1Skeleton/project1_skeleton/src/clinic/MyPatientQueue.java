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
		
		if (numOfPatients == 0)
			return null;
		
		Patient patientToReturn = patientArray[head++];
		head %= patientArray.length;
		--numOfPatients;
		
		// If the number of patients is <= 1/4th the size of the patient array, resize it
		if (numOfPatients <= patientArray.length / 4)
			changeArraySize(true);
		
		if (numOfPatients == 0) {
			head = 0;
			tail = 0;
		}
		
		return patientToReturn;
	}

	/**
	 * return, but do not remove, the patient at index i
	 * @param i - index of patient to return
	 * @return patient at index i, or null if no such element
	 */
	public Patient get(int i) {
		// return, but do not remove, the patient at index i

		// if the index is outside of the bounds of the queue, adjusted for index being 0-based
		if (i > numOfPatients - 1 || i < 0)
			return null;
		
		int indexOfPatient = (head + i) % patientArray.length;
		return patientArray[indexOfPatient];
	}

	/**
	 * add patient to front of queue
	 * @param p - patient being added to queue
	 */
	public void push(Patient p) {
		// add Patient p to front of queue
		// resize array, if needed
		// -----
		
		--head;
		if (head < 0) // This case should only be hit when head = -1. Otherwise, the math will be off
			head += patientArray.length;
		patientArray[head] = p;
		++numOfPatients;
		
		// If head == tail, head must have wrapped around to the end of the array and filled the last empty spot.
		// Therefore, the array must be full and should be expanded
		if (head == tail || numOfPatients == patientArray.length)
			changeArraySize(false);
	}

	/**
	 * remove and return patient at index i from queue
	 * @param i - index of patient to remove
	 * @return patient at index i, null if no such element
	 */
	public Patient dequeue(int i) {
		// remove and return Patient at index i from queue
		// shift patients down to fill hole left by removed patient
		// resize array, if needed
		
		// if the index is outside of the bounds of the queue, adjusted for index being 0-based
		if (i > numOfPatients - 1 || i < 0)
			return null;
		
		if (i == 0)
			return dequeue();
		
		// don't use get method which I have already implemented because I need the index of the patient
		int indexOfPatient = (head + i) % patientArray.length;
		Patient patientToReturn = patientArray[indexOfPatient++];
		--numOfPatients;
		
		// Roll all the patients to the left 1 index
		while ((indexOfPatient % patientArray.length) != tail) { // handle case where the index is last element in array
			indexOfPatient %= patientArray.length;
			// use this math to handle case when indexOfPatient wraps around the array back to 0
			int indexToFill = ((indexOfPatient - 1) % patientArray.length + patientArray.length) % patientArray.length;
			patientArray[indexToFill] = patientArray[indexOfPatient];
			++indexOfPatient;
		}
		
		// move the tail to the left 1 index
		tail = ((tail - 1) % patientArray.length + patientArray.length) % patientArray.length;
		
		// At this point, the logic should continue as if performing a standard dequeue operation
		
		// If the number of patients is <= 1/4th the size of the patient array, resize it
		if (numOfPatients <= patientArray.length / 4)
			changeArraySize(true);
				
		if (numOfPatients == 0) {
			head = 0;
			tail = 0;
		}
		
		return patientToReturn;
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
	
	public boolean isEmpty() {
		return numOfPatients < 1;
	}
	
	/**
	 * returns the head of the queue without dequeueing it
	 * @return head of the queue without dequeueing it
	 */
	public Patient peek() {
		return patientArray[head];
	}
}


