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
	 * autoNode ← UDP.GetAutoNodeRequiringRepair()
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
		int indexOflocalnode = -1;
		for (int index = 0; index < local.AutoNodeArray.length ; index++){
			if (local.AutoNodeArray[index].equals(localnode)) indexOflocalnode = index;
		}
		// TODO Auto-generated method stub
		local.AutoNodeArray[local.udp.GetAutoNodeRequiringRepair()].settimeUntilFailure(local.rvp.uTimeUntilFailure(indexOflocalnode));
		local.AutoNodeArray[local.udp.GetAutoNodeRequiringRepair()].setBusy(false);
		

	}
	/**
	 * autoNode ← UDP.GetAutoNodeRequiringRepair()
	 * TRUE if autoNode is NOT -1
	 * FALSE if autoNode is -1
	 */
	public static boolean PreCondition(PanoramaTV TVmodel ){
		return (TVmodel.udp.GetAutoNodeRequiringRepair() != -1);		
	}
	
	
}
