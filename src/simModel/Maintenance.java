package simModel;

import java.util.PriorityQueue;


/**
 * @author mush
 */
public class Maintenance {


	//since autonodes are specified in ascending order, the default comparator is used for integers
	private PriorityQueue<Integer> repairQueue;
	private PriorityQueue<Integer> setupQueue;
	public boolean busy = false;

	public Maintenance(){
		repairQueue = new PriorityQueue<Integer>();
		setupQueue = new PriorityQueue<Integer>();
	}
	
	public void addRepair(int autoNodeID){
		repairQueue.add(autoNodeID);
	}
	public void addSetup(int autoNodeID){
		setupQueue.add(autoNodeID);
	}
	public int getRepair(){
		return repairQueue.remove();
	}
	public int getSetup(){
		return setupQueue.remove();
	}

}
