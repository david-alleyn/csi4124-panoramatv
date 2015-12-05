package simModel;
import simulationModelling.ConditionalActivity;
/**
 * The activity performs the necessary automatic assembly on the TV.
 * @author mush
 */
import simulationModelling.ScheduledActivity;
import simModel.*;

public class AutoProcessing extends ConditionalActivity {
	/**
	 * Autonode OP should be Autonode processing... 
	 */
	private PanoramaTV model; 
	private static int autoNodeId;
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
		// TODO Auto-generated method stub
		autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
		model.autoNodes[autoNodeId].setBusy(true);
		
		if(model.autoNodes[autoNodeId].processTime == 0){			
			model.autoNodes[autoNodeId].processTime = model.dvp.uAutomaticProcessTime(autoNodeId);
		}
	}
	
	public static boolean precondition(PanoramaTV model){
		autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
		return autoNodeId != -1;
	}

	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub
		model.autoNodes[autoNodeId].setBusy(false);
		segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
		model.autoNodes[autoNodeId].setTimeUntilFailure(model.autoNodes[autoNodeId].getTimeUntilFailure() - model.autoNodes[autoNodeId].processTime);
		model.autoNodes[autoNodeId].processTime = 0;
		model.conveyorSegments[segmentID].getPosition().get(model.conveyorSegments[segmentID].getCapacity() - 1).finishedProcessing = true;
		
		
	}
	
}
