package simModel;
/**
 * The 4 automatic segments in the system.  OP20, OP30, OPTest, OP50 are the four identifiers of the segment

 * @author mush
 *
busy
lastTVtype
timeUntilFailure
processTimeAfterFailure

 */
public class AutoNode {
	/**	Set to TRUE when the Automatic Segment begins one of the following activities: CRTAssembly, 
	CoilAssembly, Test and BackAssembly: otherwise set to FALSE.*/
	private Boolean busy;
	/** Used to compare the machine�s current configuration with the one required for 
	 * processing the next TV. Is set to the correct type by SetupEquipment during operation. 
	 * Is set by Initialize action during initialization. */
	public TvType lastTVType;
	/**Time left until next breakdown. This time is re-initialised 
	 * after a repair and decremented after each processing of TV on the automatic node
	 */	
	private double timeUntilFailure;
	public double processTime;
	
	public void setBusy(Boolean busy){
		this.busy = busy;
	}
	
	public boolean getBusy(){
		return this.busy;
	}
	
	public double getTimeUntilFailure() {
		return this.timeUntilFailure;
	}
	
	public void setTimeUntilFailure(double d) {
		timeUntilFailure = d;
	}
	
	

}
