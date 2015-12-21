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
		this.model = model;
	}

	public static boolean precondition(PanoramaTV model){
		return (model.udp.GetAutoNodeRequiringRepair() != -1);
	}

	@Override
	public void startingEvent() {
		this.autoNodeId = model.udp.GetAutoNodeRequiringRepair();
		model.maintenance.busy = true;
	}

	@Override
	protected double duration() {
		return model.rvp.GetNodeRepairTime(autoNodeId);
	}

	@Override
	protected void terminatingEvent() {
		model.autoNodes[autoNodeId].setTimeUntilFailure(model.rvp.uTimeUntilFailure(autoNodeId));
		model.autoNodes[autoNodeId].busy = false;
		model.maintenance.busy = false;
		

	}
	
	
	
}
