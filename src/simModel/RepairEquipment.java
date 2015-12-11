package simModel;
/**
 * This activity performs repairs on the broken equipment.
 * @author mush
 */
import simulationModelling.ConditionalActivity;

public class RepairEquipment extends ConditionalActivity {

	PanoramaTV model;
	int autoNodeId;
	public RepairEquipment(PanoramaTV model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}
	
	/**
	 * autoNode ← UDP.GetAutoNodeRequiringRepair()
	 * TRUE if autoNode is NOT -1
	 * FALSE if autoNode is -1
	 */
	public static boolean precondition(PanoramaTV model){
		return (model.udp.GetAutoNodeRequiringRepair() != -1);		
	}
	
	/**
	 * autoNode ← UDP.GetAutoNodeRequiringRepair()
	 */
	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		this.autoNodeId = model.udp.GetAutoNodeRequiringRepair();
		model.maintenance.busy = true;
	}
	
	/** t <-- UDP.GetNodeRepairTime(autoNode)
	 * */
	@Override
	protected double duration() {
		return model.udp.GetNodeRepairTime(autoNodeId);
	}

	/**
	 * RC.AutoNode[autoNode].timeUntilFailure <-- RVP.uTimeUntilFailure(autonode);
	 * RC.AutoNode[autoNode].busy â†� FALSE;
	 */
	@Override
	protected void terminatingEvent() {
		model.autoNodes[autoNodeId].setTimeUntilFailure(model.rvp.uTimeUntilFailure(autoNodeId));
		model.autoNodes[autoNodeId].setBusy(false);
		model.maintenance.busy = false;
		

	}
	
	
	
}
