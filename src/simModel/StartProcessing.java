package simModel;

import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;

public class StartProcessing extends ConditionalActivity {
	PanoramaTV model;
	int node;
	public StartProcessing(PanoramaTV localmodel) {
		// TODO Auto-generated constructor stub
		this.model = localmodel;
	}

	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		return model.autoNodes[node].getTimeUntilFailure();
	}
/**
 * autoNode â†� UDP.GetAutoNodeForPartialProcessing();
 * processTime â†� DVP.uAutomaticProcessTime() - timeUntilFailure
 * RC.AutoNode[autoNode].busy = TRUE;
 */
	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		node =  model.udp.GetAutoNodeForPartialProcessing();
		model.autoNodes[node].setBusy(true);
		model.autoNodes[node].processTime = model.dvp.uAutomaticProcessTime(node) - model.autoNodes[node].getTimeUntilFailure();
		
	}
	/**
	 * imeUntilFailure â†� 0
	 */
	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub
		model.autoNodes[node].setTimeUntilFailure(0);
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