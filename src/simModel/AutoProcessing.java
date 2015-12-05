package simModel;
import simulationModelling.ConditionalActivity;
/**
 * 
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
		return model.AutoNodeArray[autoNodeId].processTime;
		
	}

	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
		model.AutoNodeArray[autoNodeId].setBusy(true);
		
		if(model.AutoNodeArray[autoNodeId].processTime == 0){			
			model.AutoNodeArray[autoNodeId].processTime = model.dvp.uAutomaticProcessTime(autoNodeId);
		}
	}
	
	public static boolean preconditions(PanoramaTV model){
		autoNodeId = model.udp.GetAutoNodeReadyForProcessing();
		return autoNodeId != -1;
	}

	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub
		model.AutoNodeArray[autoNodeId].setBusy(false);
		segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
		model.AutoNodeArray[autoNodeId].setTimeUntilFailure(model.AutoNodeArray[autoNodeId].getTimeUntilFailure() - model.AutoNodeArray[autoNodeId].processTime);
		model.AutoNodeArray[autoNodeId].processTime = 0;
		model.ConveyorSeg[segmentID].getPosition().get(model.ConveyorSeg[segmentID].getCapacity() - 1).finishedProcessing = true;
		
		
	}
	
}
