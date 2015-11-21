package simModel;
import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;
/*
 * @author: mush
 * @version : 1.0
 * @since : 21 nov
 */
public class ManualProcessing extends ConditionalActivity {

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
	public void PreCondition()
	{
		
	}
	public void Duraiton (){
		
	}
	/**
	 * 
	Event ManualProcessing
	RC.ManualNode[ID].busy ←TRUE
	IF(ID = OP10)
    	RC.ConveyorSeg[CS_ID][LAST].type = uTvType()
	ENDIF

	 * @return
	 */
	public void ManualProcessing(AutoNode ID){
		
	}
	/**
	 * Event ManualProcessing
	 * @return RC.ManualNode[ID].busy = FALSE
	 */
	public boolean ManualProcessing(){
		return false;
	}

}