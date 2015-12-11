package simModel;


import simulationModelling.ConditionalActivity;

/**
 * @author: mush
 * @version : 1.0
 * @since : 21 nov
 */
public class ManualProcessing extends ConditionalActivity {

	private PanoramaTV model;
	private int manualNodeId;

	public ManualProcessing(PanoramaTV panoramaTV) {
		model = panoramaTV;
	}
	
	/**
	 * manualNodeId <-- UDP.ManualNodesReadyForProcessing() if(manualNodeId !=
	 * NULL) return TRUE else return FALSE;
	 */
	public static boolean precondition(PanoramaTV model) {
		int manualNodeId = model.udp.GetManualNodeReadyForProcessing();
		return (manualNodeId != -1);
	}
	
	@Override
	public void startingEvent() {
		manualNodeId = model.udp.GetManualNodeReadyForProcessing();
		model.manualNodes[manualNodeId].setBusy(true);
	}

	@Override
	protected double duration() {
		return model.udp.GetManualNodeProcessTime(manualNodeId);
	}

	@Override
	protected void terminatingEvent() {
		int segmentId = model.udp.GetAssociatedSegmentID(manualNodeId, false);
		int headOfSegment = model.conveyorSegments[segmentId].getCapacity() - 1;
		
		if (manualNodeId == Const.OP10) {
			
			model.conveyorSegments[segmentId].positions[headOfSegment].tvType = model.dvp.uTvType();
			
		} else if (manualNodeId == Const.OP60) {
			
			model.conveyorSegments[segmentId].positions[headOfSegment].tvType = null;
		} else if (manualNodeId == Const.REWORK) {
			
			model.conveyorSegments[segmentId].positions[headOfSegment].finishedProcessing = true;
		}

		model.manualNodes[manualNodeId].setBusy(false);
		model.conveyorSegments[segmentId].positions[headOfSegment].finishedProcessing = true;

	}
}