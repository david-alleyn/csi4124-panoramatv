package simModel;

import simulationModelling.ConditionalActivity;

public class StartProcessing extends ConditionalActivity {
	PanoramaTV model;
	int autoNodeId;
	public StartProcessing(PanoramaTV localmodel) {
		// TODO Auto-generated constructor stub
		this.model = localmodel;
	}

	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		return model.autoNodes[autoNodeId].getTimeUntilFailure();
	}
/**
 * autoNode â†� UDP.GetAutoNodeForPartialProcessing();
 * processTime â†� DVP.uAutomaticProcessTime() - timeUntilFailure
 * RC.AutoNode[autoNode].busy = TRUE;
 */
	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		autoNodeId =  model.udp.GetAutoNodeForPartialProcessing();
		model.autoNodes[autoNodeId].setBusy(true);
		model.autoNodes[autoNodeId].processTime = model.dvp.uAutomaticProcessTime(autoNodeId) - model.autoNodes[autoNodeId].getTimeUntilFailure();
		
		if(model.autoNodes[autoNodeId].processTime < 0)
		{
			model.autoNodes[autoNodeId].processTime = 0.00001;
		}
		
	}
	/**
	 * imeUntilFailure â†� 0
	 */
	@Override
	protected void terminatingEvent() {
		model.autoNodes[autoNodeId].setTimeUntilFailure(0);
	}
	/**
	 * autoNode â†� UDP.GetAutoNodeForPartialProcessing()
	 * @return
	 */
	public static boolean precondition(PanoramaTV model)
	{
		int node = model.udp.GetAutoNodeForPartialProcessing();
		return (node != -1);
	 
	}
}