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
		model.autoNodes[autoNodeId].setBusy(true);
		
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
			model.conveyorSegments[segmentID].positions[headOfSegment].moveRework = model.rvp.uPassTvTesting();
		}
		
		model.autoNodes[autoNodeId].setBusy(false);
		model.autoNodes[autoNodeId].setTimeUntilFailure(model.autoNodes[autoNodeId].getTimeUntilFailure() - model.autoNodes[autoNodeId].processTime);
		model.autoNodes[autoNodeId].processTime = 0;
		model.conveyorSegments[segmentID].positions[headOfSegment].finishedProcessing = true;
		
		
	}
	
}
