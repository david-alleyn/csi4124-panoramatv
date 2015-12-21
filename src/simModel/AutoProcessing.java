package simModel;
import simulationModelling.ConditionalActivity;
/**
 * The activity performs the necessary automatic assembly on the TV.
 * @author mush
 */


public class AutoProcessing extends ConditionalActivity {
	/**
	 * Autonode OP should be Autonode processing... 
	 */
	private PanoramaTV model; 
	private int autoNodeId;
	private int segmentID;
	private int headOfSegment;
	private int palletID;
	
	public AutoProcessing(PanoramaTV localTV){
		model = localTV; // a local instance of PanoramaTV.
	}


	@Override
	protected double duration() {
		return model.autoNodes[autoNodeId].processTime;
		
	}

	@Override
	public void startingEvent() {
		autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
		segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
		headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
		palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];

		model.autoNodes[autoNodeId].busy = true;
		model.pallets[palletID].finishedProcessing = false;

		if(model.autoNodes[autoNodeId].processTime == 0){
			model.autoNodes[autoNodeId].processTime = model.dvp.uAutomaticProcessTime(autoNodeId);
		}
	}
	
	public static boolean precondition(PanoramaTV model){
		int autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
		return autoNodeId != -1;
	}

	@Override
	protected void terminatingEvent() {


		if(autoNodeId == Const.TEST)
		{
			model.pallets[palletID].moveRework = model.rvp.uPassTvTesting();
		}
		
		model.autoNodes[autoNodeId].busy = false;
		model.autoNodes[autoNodeId].setTimeUntilFailure(model.autoNodes[autoNodeId].getTimeUntilFailure() - model.autoNodes[autoNodeId].processTime);
		model.autoNodes[autoNodeId].processTime = 0;
		model.pallets[palletID].finishedProcessing = true;
		
		
	}
	
}
