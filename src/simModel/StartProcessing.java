package simModel;

import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;

public class StartProcessing extends ConditionalActivity {
	PanoramaTV localmodel;
	public StartProcessing(PanoramaTV localmodel) {
		// TODO Auto-generated constructor stub
		this.localmodel = localmodel;
	}

	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		AutoNode local =  (AutoNode) localmodel.udp.GetAutoNodeForPartialProcessing();
		return local.getprocessTimeAfterFailure();
	}
/**
 * autoNode ← UDP.GetAutoNodeForPartialProcessing();
 * processTime ← DVP.uAutomaticProcessTime() - timeUntilFailure
 * RC.AutoNode[autoNode].busy = TRUE;
 */
	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		AutoNode local =  (AutoNode) localmodel.udp.GetAutoNodeForPartialProcessing();
		double processTime = this.localmodel.udp.uAutomaticProcessTime() - local.gettimeUntilFailure();
		
	}
	/**
	 * imeUntilFailure ← 0
	 */
	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub
		AutoNode local =  (AutoNode) localmodel.udp.GetAutoNodeForPartialProcessing();
		local.setprocessTimeAfterFailure(0.0);
		this.localmodel.AutoNodeArray[-1].setBusy(true); // -1 cause the funciton is yet to be set.. 
	}
	/**
	 * autoNode ← UDP.GetAutoNodeForPartialProcessing()
	 * @return
	 */
	public boolean PreCondiiton(PanoramaTV localmodel)
	{
		return (localmodel.udp.GetAutoNodeForPartialProcessing() != null) ;
	 
	}
}
