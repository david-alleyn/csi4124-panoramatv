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
	private int segmentId;
	private int headOfSegment;
	private int palletID;

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
		segmentId = model.udp.GetAssociatedSegmentID(manualNodeId, false);
		headOfSegment = model.conveyorSegments[segmentId].getCapacity() - 1;
		palletID = model.conveyorSegments[segmentId].palletPositions[headOfSegment];

		model.manualNodes[manualNodeId].busy = true;
	}

	@Override
	protected double duration() {

		return model.udp.GetManualNodeProcessTime(manualNodeId);
	}

	@Override
	protected void terminatingEvent() {
		if (manualNodeId == Const.OP10) {
			
			model.pallets[palletID].tvType = model.dvp.uTvType();

		} else if (manualNodeId == Const.OP60) {

			model.pallets[palletID].tvType = TvType.None;
		} 

		model.manualNodes[manualNodeId].busy = false;
		model.pallets[palletID].finishedProcessing = true;

	}
}