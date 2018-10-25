// Name: Alex Hale
// ID: 260672475
// Collaborators: none

package A2;
import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * The way you implement this method will define which order the assignments appear in when you sort.
	 * Return 1 if a1 should appear after a2
	 * Return -1 if a1 should appear before a2
	 * Return 0 if a1 and a2 are equivalent 
	 */
	@Override 		// override the default comparison method
	public int compare(Assignment a1, Assignment a2) {
		// compare by deadline, prioritize the assignment with the sooner deadline
		if (a1.deadline < a2.deadline) {
			return -1;
		} else if (a1.deadline > a2.deadline) {
			return 1;
		} else {
			// a1 and a2 have the same deadline, compare value
			if (a1.weight < a2.weight) {
				return 1;
			} else if (a1.weight > a2.weight) {
				return -1;
			} else {
				// if both weight and deadline are the same, return no preference
				return 0;
			}
		}
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to when assignment #i will be completed. output[i] is 0 if assignment #i is never completed.
	 * The homework you complete first will be given an output of 1, the second, 2, etc.
	 */
	public int[] SelectAssignments() {	
		//Use the following command to sort your Assignments: 
		//Collections.sort(Assignments, new Assignment());
		//This will re-order your assignments. The resulting order will depend on how the compare function is implemented
			// NOTE: I implemented the compare() function such that this list is now in order of ascending due time
				// if two functions have the same due time, they are sorted by value
					// if they have the same value and due time, they are identical and therefore sorted randomly
		Collections.sort(Assignments, new Assignment());
		
		//Initializes the homeworkPlan, which you must fill out and output
		int[] homeworkPlan = new int[Assignments.size()];

		// iterate through the entire collecton of assignments
		int lastFinishingTime = 0;
		for (Assignment a : Assignments) {
			// find the finishing time (NOT the deadline) of the last assignment in the homeworkPlan (use variable)

			// if the deadline of a is >= this finishing time, add a to the homeworkPlan as the next in line
			if (a.deadline > lastFinishingTime) {
				lastFinishingTime++;
				homeworkPlan[a.number] = lastFinishingTime;
			} else {
				// otherwise, decide that a will not be completed
				homeworkPlan[a.number] = 0;
			}
		}
	
		
		return homeworkPlan;
	}
}
	



