package simModel;

import java.util.PriorityQueue;
import java.util.Queue;

import simulationModelling.ScheduledActivity;

/**
 * @author mush
 */
public class Maintenance {
	
	private Queue<AutoNode>   Repair;
	private Queue<AutoNode>   Setup;
	public boolean busy = false;
	
	public void addRepair(AutoNode current){
		Repair.add(current);
	}
	public void addSetup(AutoNode current){
		Setup.add(current);
	}
	public AutoNode getRepair(){
		return Repair.remove();
	}
	public AutoNode getSetup(){
		return Setup.remove();
	}
	

}
