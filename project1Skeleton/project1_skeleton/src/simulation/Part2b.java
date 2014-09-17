
package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import clinic.Doctor;
import clinic.MyPatientQueue;
import clinic.Parameters;
import clinic.Patient;
import static clinic.Parameters.*;

/**
 * All doctors serve all queues.
 * Patients are sorted into queues by urgency level.
 * Patients with emergencies see next available doctor.
 * Patients in Level 1 or 2 waiting 90 minutes promote to next level.
 *
 * @author TODO
 *
 */

public class Part2b {
	// PROVIDED
	// simulation clock
	private static int time;
	// free physician list
	private static LinkedList<Doctor> free_doctors;
	// busy physician list
	private static LinkedList<Doctor> busy_doctors;
	// time next busy doctor becomes available
	private static int next_available_busy_doctor_time;
	// patient queues
	private static MyPatientQueue[] Q;
	// random number generator
	private static Random random;
	// declare statistics keeping variables
	private static int[] n;			// count for each queue
	private static double[] w_mean;	// mean wait time for each queue.
	private static int[] w_max;		// max wait time for each queue
	// -----
    
	// PROVIDED
	/**
	 * initialize the simulation
	 */
	private static void init() {
		Q = new MyPatientQueue[4];
		for (int i = 0; i < Q.length; i++)
			Q[i] = new MyPatientQueue();
		// initialize statistics keeping variables
		n = new int[4];
		w_mean = new double[4];
		w_max = new int[4];
        
		// initialize simulation clock to zero
		time = 0;
        
		// initialize free and busy lists
		free_doctors = new LinkedList<Doctor>();
		busy_doctors = new LinkedList<Doctor>();
		for (int i = 0; i < num_doctors; i++)
			free_doctors.add(new Doctor());
		// initialize time at which the next busy doctor will be available
		// set to max int because there are no busy doctors yet
		next_available_busy_doctor_time = Integer.MAX_VALUE;
        
		random = new Random(seed);
	}
	// -----
    
	// PROVIDED
	/**
	 * run the simulation on the specified input file
	 * @param input_file - patient arrival and urgency schedule
	 * @return results of simulation<br>
	 * [0] = mean wait time array<br>
	 * [1] = max wait time array<br>
	 * [2] = doctor report array<br>
	 * [3] = proportion of patients served
	 */
	public static Object[] run_simulation(String input_file) {
		// initialize the simulation
		init();
        
		// read in input
		int N = 0;
		try {
			// start reading input file
			Scanner s = new Scanner(new File(input_file));
			while (s.hasNext()) {
				// get next patient
				Patient p = parse_patient(s.nextLine());
				N++;
                
				// advance simulation to arrival_time
				advance_simulation(p.arrival_time());
                
				// process patient
				process_patient(p);
                
			}
			// clear out queues
			finish_simulation();
            
			// close scanner
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
        
		// compute mean doctor busyness and proportion of patients treated
		double mean_busyness = 0;
		int n = 0;
		for (Doctor d : free_doctors) {
			n += (int)d.getReport()[0];
			mean_busyness += (double)d.getReport()[3];
		}
		mean_busyness /= free_doctors.size();
		// return {mean wait time array,max wait time array,mean doctor busyness,proportion of patients seen}
		return new Object[]{w_mean,w_max,mean_busyness,(double)n/(double)N};
	}
	// -----
    
	// PROVIDED
	/**
	 * extract name, arrival time, and urgency from given line
	 * @param line - "name arrival_time urgency"
	 * @return
	 */
	private static Patient parse_patient(String line) {
		String[] tok = line.split(" ");
		String name = tok[0];
		int arrival_time = Integer.parseInt(tok[1]);
		int urgency = Integer.parseInt(tok[2]);
		return new Patient(name,arrival_time,urgency);
	}
	// -----
    
	// STUDENT MUST WRITE
	/**
	 * admit patient by sticking them in the correct queue
	 * @param p - patient being admitted
	 */
	private static void process_patient(Patient p) {
		// put patient in the correct queue
		// -----
		
		switch(p.urgency()) {
			case 1: Q[0].enqueue(p); break;
			case 2: Q[1].enqueue(p); break;
			case 3: Q[2].enqueue(p); break;
			default: Q[3].enqueue(p); // If there is no specified/valid urgency, assume it's a Level 4
		}
	}
	// -----
    
	// PROVIDED
	/**
	 * advance simulation to time t
	 * @param t
	 */
	private static void advance_simulation(int t) {
		while (time < t) {
			// update FP and BP lists
			update_lists();
            
			// check for emergency
			check_emergency();
            
			// check for escalation due to delay in Q1 and Q2
			check_promotion();
            
			//check for walk out
			check_walkout();
            
			// assign free doctors to see patients
			assign_doctors();
            
			// advance simulation clock
			time++;
		}
	}
	// -----
    
	// PROVIDED
	/**
	 * advance simulation until all queues are empty
	 */
	private static void finish_simulation() {
		while (!Q[0].isEmpty() ||
               !Q[1].isEmpty() ||
               !Q[2].isEmpty() ||
               !Q[3].isEmpty())
			advance_simulation(time+1);
		free_doctors.addAll(busy_doctors);
		busy_doctors.clear();
	}
	// -----
    
	// PROVIDED
	/**
	 * update the free and busy physician lists
	 */
	private static void update_lists() {
		if (next_available_busy_doctor_time <= time) {
			// find free doctors
			int next_available_time = -1;
			for (Doctor d : busy_doctors)
				if (!d.busy(time))
					free_doctors.add(d);
				else if (next_available_time == -1)
					next_available_time = d.busy_until();
				else if (d.busy_until() < next_available_time)
					next_available_time = d.busy_until();
			next_available_busy_doctor_time = next_available_time;
            
			// remove free doctors from busy list
			for (Doctor d : free_doctors)
				busy_doctors.remove(d);
		}
	}
	// -----
    
	// PARTIALLY PROVIDED
	/**
	 * with some probability each time step, a patient in any queue will
	 * have an emergency.  if so, they will be seen by next available physician.
	 */
	private static void check_emergency() {
		// check if there is an emergency
		if (random.nextDouble() < p_emergency) {
			// STUDENT MUST WRITE
			int n = 0;
			for (int i = 0; i < 4; i++)
				n += Q[i].size();
			if (n > 0) {
				Patient p = null;
				// select a random patient
				int i = random.nextInt(n);
				for (int j = 0; j < 4 && p == null; j++)
					if (i < Q[j].size())
						p = Q[j].dequeue(i);
					else
						i -= Q[j].size();
                
				// make them the next patient to be seen by a doctor
				// this may even mean they get to go before an earlier emergency
				Q[3].push(p);
			}
			// -----
		}
	}
	// -----
    
	// STUDENT MUST WRITE
	/**
	 * patients in Level 1 and 2 get promoted to front of next highest queue
	 * after waiting for a certain amount of time.
	 */
	private static void check_promotion() {
		// STUDENT MUST WRITE
		// just in case several get promoted in the same time step
		// should preserve their order
		MyPatientQueue S = new MyPatientQueue(); // push + dequeue = stack
		// do level 2 promotions
		int i = 0;
		while (i < Q[1].size()) {
			Patient p = Q[1].get(i);
			if (p.wait_time(time) > (3-p.urgency())*l12_escalation_delay)
				S.push(Q[1].dequeue(i));
			else if (p.urgency() == 2)
				break;
			else
				i++;
		}
		while (!S.isEmpty())
			Q[2].push(S.dequeue());
        
		// do level 1 promotions
		while (!Q[0].isEmpty() && Q[0].peek().wait_time(time) > l12_escalation_delay)
			S.push(Q[0].dequeue());
		while (!S.isEmpty())
			Q[1].push(S.dequeue());
		// -----
	}
	// -----
    
	// PARTIALLY PROVIDED
	/**
	 * after a certain amount of time waiting,
	 * a patient in Level 1 will randomly decide to walk out
	 */
	private static void check_walkout() {
		// check if there is a walkout
		if (random.nextDouble() < p_walkout) {
			// STUDENT MUST WRITE
			// get count of level 1 patients which have been waiting
			// for at least l1_walkout_delay steps
			int c = 0;
			for (int i = 0; i < Q[0].size(); i++)
				if (Q[0].get(i).wait_time(time) > l1_walkout_delay)
					c++;
				else
					break;
            
			if (c > 0) // remove a random walkout from the Level 1 queue
				Q[0].dequeue(random.nextInt(c));
			// -----
		}
	}
	// -----
    
	// PROVIDED
	/**
	 * assign a doctor from the free list to see the patient
	 * @param p - Patient for doctor to treat
	 */
	private static void assign_doctor(Patient p) {
		Doctor d = free_doctors.remove();
		d.see_patient(p, time);
		busy_doctors.add(d);
		if (d.busy_until() < next_available_busy_doctor_time)
			next_available_busy_doctor_time = d.busy_until();
	}
	// -----
    
	// PROVIDED
	/**
	 * assign doctors in the free list to see patients
	 */
	private static void assign_doctors() {
		while (!free_doctors.isEmpty()) {
			// check queues for a patient to see
			Patient p = null;
			for (int i = 3; i >= 0 && p == null; i--)
				if (!Q[i].isEmpty())
					p = Q[i].dequeue();
			if (p != null) {
				// update mean and max wait times
				int wt = p.wait_time(time);
				int i = p.urgency()-1;
				w_mean[i] = (((double)n[i])/(n[i]+1))*w_mean[i] + ((double)(wt))/(n[i]+1);
				n[i]++;
                
				if (wt > w_max[i])
					w_max[i] = wt;
                
				// have doctor see patient
				assign_doctor(p);
			} else
				break;
		}
	}
	// -----
    
	// PROVIDED
	/**
	 * the main function
	 * @param args - command line arguments<br>
	 * [options] [input_file]<br>
	 * default input file is "test_input.txt"
	 */
	public static void main(String[] args) {
		// parse command line arguments
		String input_file = "test_input.txt";
		int d;
		long s;
		double p;
		int R = 1;
		for (int i = 0; i < args.length; i++) {
			switch(args[i]) {
                case "-h" :
                case "--help":
                    System.out.println("Part 2B Command Line Arguments Help");
                    System.out.println("-h, --help\tprint this help information");
                    System.out.println("-d, --doctors\tnumber of doctors");
                    System.out.println("-s, --seed\tRNG seed");
                    System.out.println("-r, --runs\tnumber of times to run simulation");
                    System.out.println("-e, --p_emergency\tprobability of emergency");
                    System.out.println("-w, --p_walkout\tprobability of walkout");
                    System.out.println("-D, --walkout_delay\twait time before liable to walk out in level 1");
                    System.out.println("-p, --promotion_delay\twait time before promoting in levels 1 and 2");
                    return;
                case "-d":
                case "--doctors" :
                    try {
                        d = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Number of doctors must be an integer.");
                        return;
                    }
                    if (d <= 0) {
                        System.out.println("Cannot have less than 1 doctor.");
                        return;
                    }
                    Parameters.num_doctors = d;
                    break;
                case "-s":
                case "--seed":
                    try {
                        s = Long.parseLong(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("RNG seed must be a number.");
                        return;
                    }
                    Parameters.seed = s;
                    break;
                case "-r" :
                case "--runs" :
                    try {
                        R = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Number of runs must be a number.");
                        return;
                    }
                    if (R <= 0) {
                        System.out.println("Must have at least 1 run.");
                        return;
                    }
                    break;
                case "-e":
                case "--p_emergency":
                    try {
                        p = Double.parseDouble(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("p(emergency) must be a number.");
                        return;
                    }
                    if (p < 0 || p > 1) {
                        System.out.println("p(emergency) must be between 0 and 1.");
                        return;
                    }
                    Parameters.p_emergency = p;
                    break;
                case "-w":
                case "--p_walkout":
                    try {
                        p = Double.parseDouble(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("p(walkout) must be a number.");
                        return;
                    }
                    if (p < 0 || p > 1) {
                        System.out.println("p(walkout) must be between 0 and 1.");
                        return;
                    }
                    Parameters.p_walkout = p;
                    break;
                case "-D":
                case "--walkout_delay":
                    try {
                        d = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Walkout delay must be an integer.");
                        return;
                    }
                    if (d < 0) {
                        System.out.println("Cannot have negative walkout delay.");
                        return;
                    }
                    Parameters.l1_walkout_delay = d;
                    break;
                case "-p":
                case "--promotion_delay":
                    try {
                        d = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Promotion delay must be an integer.");
                        return;
                    }
                    if (d < 0) {
                        System.out.println("Cannot have negative promotion delay.");
                        return;
                    }
                    Parameters.l12_escalation_delay = d;
                    break;
                default:
                    input_file = args[i];
			}
		}
        
		// run simulation
		Object[] results;
		double[][] mean_wait_time = new double[R][4];
		int[][] max_wait_time = new int[R][4];
		double[] mean_busyness = new double[R];
		double[] mean_proportion = new double[R];
		double mean_length_of_day = 0;
		for (int r = 0; r < R; r++) {
			if (R > 1)
				Parameters.seed = System.currentTimeMillis();
			results = run_simulation(input_file);
			if (results == null)
				return;
            
			for (int i = 0; i < 4; i++) {
				mean_wait_time[r][i] = ((double[])results[0])[i];
				max_wait_time[r][i] = ((int[])results[1])[i];
			}
			mean_busyness[r] = (double)results[2];
			mean_proportion[r] = (double)results[3];
			mean_length_of_day += ((double)time)/R;
		}
        
		// compute mean and standard deviation over runs
		double[] mu_mean = new double[4];
		double[] sigma_mean = new double[4];
		double[] mu_max = new double[4];
		double[] sigma_max = new double[4];
		double mu_busyness = 0;
		double sigma_busyness = 0;
		double mu_proportion = 0;
		double sigma_proportion = 0;
		for (int i = 0; i < 4; i++) {
			mu_mean[i] = 0;
			sigma_mean[i] = 0;
			mu_max[i] = 0;
			sigma_max[i] = 0;
		}
		for (int r = 0; r < R; r++) {
			for (int i = 0; i < 4; i++) {
				mu_mean[i] += mean_wait_time[r][i];
				mu_max[i] += max_wait_time[r][i];
			}
			mu_busyness += mean_busyness[r];
			mu_proportion += mean_proportion[r];
		}
		for (int i = 0; i < 4; i++) {
			mu_mean[i] /= R;
			mu_max[i] /= R;
		}
		mu_busyness /= R;
		mu_proportion /= R;
		for (int r = 0; r < R; r++) {
			for (int i = 0; i < 4; i++) {
				sigma_mean[i] += Math.pow(mu_mean[i]-mean_wait_time[r][i],2);
				sigma_max[i] += Math.pow(mu_max[i]-max_wait_time[r][i],2);
			}
			sigma_busyness += Math.pow(mu_busyness-mean_busyness[r],2);
			sigma_proportion += Math.pow(mu_proportion-mean_proportion[r],2);
		}
		if (R > 1) {
			for (int i = 0; i < 4; i++) {
				sigma_mean[i] /= R-1;
				sigma_max[i] /= R-1;
			}
			sigma_busyness /= R-1;
			sigma_proportion /= R-1;
		}
		for (int i = 0; i < 4; i++) {
			sigma_mean[i] = Math.sqrt(sigma_mean[i]);
			sigma_max[i] = Math.sqrt(sigma_max[i]);
		}
		sigma_busyness = Math.sqrt(sigma_busyness);
		sigma_proportion = Math.sqrt(sigma_proportion);
        
		// pretty print results to standard out
		System.out.println("PART 2B");
		for (int i = 0; i < 4; i++) {
			System.out.printf("mean mean wait time level %d: %.4f minutes\n",i+1,mu_mean[i]);
			System.out.printf("stdev mean wait time level %d: %.4f minutes\n",i+1,sigma_mean[i]);
		}
		System.out.println();
		for (int i = 0; i < 4; i++) {
			System.out.printf("mean max wait time level %d: %.4f minutes\n",i+1,mu_max[i]);
			System.out.printf("stdev max wait time level %d: %.4f minutes\n",i+1,sigma_max[i]);
		}
		System.out.println();
		System.out.printf("mean mean doctor busyness: %.4f%%\n",mu_busyness*100);
		System.out.printf("stdev mean doctor busyness: %.4f%%\n",sigma_busyness*100);
		System.out.println();
		System.out.printf("mean proportion of patients served: %.4f%%\n",mu_proportion*100);
		System.out.printf("stdev proportion of patients served: %.4f%%\n",sigma_proportion*100);
		System.out.println();
		System.out.printf("mean length of day: %.4f\n",mean_length_of_day);
	}
	// -----
}