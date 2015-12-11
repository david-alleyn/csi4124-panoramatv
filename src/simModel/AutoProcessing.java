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
	AutoNode node;
	private int segmentID;
	//  private double timeUntilFailure;
	
	public AutoProcessing(PanoramaTV localTV){
		model = localTV; // a local instance of PanoramaTV.
	}
	

	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		return model.autoNodes[autoNodeId].processTime;
		
	}

	@Override
	public void startingEvent() {
		autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
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
		// TODO Auto-generated method stub
		
		if(autoNodeId == Const.CS_TEST)
		{
			int headOfSegment = model.conveyorSegments[autoNodeId].getCapacity() - 1;
			model.pallets[headOfSegment].moveRework = model.rvp.uPassTvTesting();
		}
		
		model.autoNodes[autoNodeId].setBusy(false);
		segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
		model.autoNodes[autoNodeId].setTimeUntilFailure(model.autoNodes[autoNodeId].getTimeUntilFailure() - model.autoNodes[autoNodeId].processTime);
		model.autoNodes[autoNodeId].processTime = 0;
		int capacity = model.conveyorSegments[segmentID].getCapacity();
		model.conveyorSegments[segmentID].positions[capacity - 1].finishedProcessing = true;
		
		
	}
	
}
