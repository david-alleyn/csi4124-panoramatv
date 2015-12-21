package simModel;

import simulationModelling.ConditionalActivity;

public class StartProcessing extends ConditionalActivity {
	PanoramaTV model;
	private int autoNodeId;
	private int segmentID;
	private int headOfSegment;
	private int palletID;

	public StartProcessing(PanoramaTV localmodel) {
		this.model = localmodel;
	}

	public static boolean precondition(PanoramaTV model)
	{
		int node = model.udp.GetAutoNodeForPartialProcessing();
		return (node != -1);
	}

	@Override
	public void startingEvent() {
		autoNodeId =  model.udp.GetAutoNodeForPartialProcessing();
		segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
		headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
		palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];

		model.autoNodes[autoNodeId].busy = true;
	}

	@Override
	protected double duration() {
		double timeUntilFailure = model.autoNodes[autoNodeId].getTimeUntilFailure();
		model.autoNodes[autoNodeId].processTime = model.dvp.uAutomaticProcessTime(autoNodeId) - timeUntilFailure;

		//This is to stave off a theoretical anomaly that is driven by the double querying of
		//the DVP.uAutomaticProcessTime(autoNodeId) during the startingEvent and duration calls.
		//This cannot change as it is used to facilitate the behaviors AutoProcessing and StartProcessing
		if(model.autoNodes[autoNodeId].processTime < 0)
		{
			model.autoNodes[autoNodeId].processTime = 0.001;
		}
		return timeUntilFailure;
	}

	@Override
	protected void terminatingEvent() {
		model.autoNodes[autoNodeId].setTimeUntilFailure(0);
	}
	
}