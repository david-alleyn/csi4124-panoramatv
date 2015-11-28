package simModel;
/**
 * 
 */
import simulationModelling.ScheduledActivity;
import simModel.*;

public class AutoNodeOP extends ScheduledActivity {
	/**
	 * 
	 */
	private PanoramaTV local; 
	public AutoNodeOP(PanoramaTV localTV){
		local = localTV; // a local instance of PanoramaTV.
	}
	@Override
	protected double timeSequence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub

	}

}
