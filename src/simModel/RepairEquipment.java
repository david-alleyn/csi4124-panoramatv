package simModel;

import simulationModelling.ScheduledActivity;

public class RepairEquipment extends ScheduledActivity {

	@Override
	protected double timeSequence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub

	}
	/**
	 * RC.AutoNode[ID].busy = FALSE
	 * R.Maintenance.currentNode = RC.AutoNode[ID]
	 * RC.AutoNode[ID].timeUntilFailure = 0
	 */
	public void PreCondition(Boolean AutoNode )
	{
		
	}
	/*
	 * RC.AutoNode[ID].busy = TRUE
	 */
	public void EventRepair(Boolean AutoNode){
		
	}
	public void Duraiton (){
		
	}
	public void EventRepair(AutoNode timeUntilFailure, Boolean Busy ){
		
	}
}
