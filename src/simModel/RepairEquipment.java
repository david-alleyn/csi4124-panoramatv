package simModel;

import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;

public class RepairEquipment extends ConditionalActivity {

	PanoramaTV local;
	AutoNode localnode;
	public RepairEquipment(PanoramaTV local) {
		// TODO Auto-generated constructor stub
		this.local = local;
		this.localnode = local.AutoNodeArray[local.udp.GetAutoNodeForPartialProcessing()];
	}
	/** t <-- UDP.GetNodeRepairTime(autoNode)
	 * */
	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		local.udp.GetNodeRepairTime(local.udp.GetAutoNodeForPartialProcessing());
		return 0;
	}
	/**
	 * autoNode <-- UDP.GetAutoNodeRequiringRepair()
	 */
	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		this.localnode = local.AutoNodeArray[local.udp.GetAutoNodeRequiringRepair()];
	}
	/**
	 * RC.AutoNode[autoNode].timeUntilFailure <-- RVP.uTimeUntilFailure(autonode);
	 * RC.AutoNode[autoNode].busy â†� FALSE;
	 */
	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub
		local.AutoNodeArray[-1].settimeUntilFailure(local.rvp.uTimeUntilFailure(local));
		local.AutoNodeArray[-1].setBusy(false);
		

	}
	/**
	 * RC.AutoNode[ID].busy = FALSE
	 * R.Maintenance.currentNode = RC.AutoNode[ID]
	 * RC.AutoNode[ID].timeUntilFailure = 0
	 */
	public void PreCondition(Boolean AutoNode ){
		
	}
	
	
}
