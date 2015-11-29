package simModel;
/**
 * 
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
	/** Used to compare the machine’s current configuration with the one required for 
	 * processing the next TV. Is set to the correct type by SetupEquipment during operation. 
	 * Is set by Initialize action during initialization. */
	private Type lastTVtype;
	/**Time left until next breakdown. This time is re-initialised 
	 * after a repair and decremented after each processing of TV on the automatic node
	 */	
	private double timeUntilFailure;
	/**Time left until next breakdown. This time is re-initialised 
	 * after a repair and decremented after each processing of TV on the automatic node. */
	private double processTimeAfterFailure;
	
	public void setBusy(Boolean busy){
		this.busy = busy;
	}public void setlastTVtype(Type lastTVtype){
		this.lastTVtype = lastTVtype;
	}public void setprocessTimeAfterFailure(double timeUntilFailure) {
		this.timeUntilFailure = timeUntilFailure;
	}public void settimeUntilFailure(double processTimeAfterFailure){
		this.processTimeAfterFailure = processTimeAfterFailure;
	}
	
	public Boolean getBusy(){
		return this.busy;
	}public Type getlastTVtype(){
		return this.lastTVtype;
	}public double getprocessTimeAfterFailure() {
		return this.timeUntilFailure;
	}public double gettimeUntilFailure(){
		return this.processTimeAfterFailure;
	}
	

}
