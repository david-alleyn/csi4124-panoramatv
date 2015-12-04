package simModel;

import simulationModelling.ScheduledActivity;

/**
 * @author mush
 */
public class Maintenance {
	
	private AutoNode currentNode;
	
	public void setCurrentNode(AutoNode current){
		this.currentNode = current;
	}
	public AutoNode getCurrentNode(){
		return this.currentNode;
	}

}
