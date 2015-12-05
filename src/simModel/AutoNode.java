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
	private TvType lastTVtype;
	/**Time left until next breakdown. This time is re-initialised 
	 * after a repair and decremented after each processing of TV on the automatic node
	 */	
	private double timeUntilFailure;
	/**Time left until next breakdown. This time is re-initialised 
	 * after a repair and decremented after each processing of TV on the automatic node.	 */
	private double processTimeAfterFailure;
	public TvType lastTVType;
	public int processTime;
	public boolean inMotion;
	
	public void setBusy(Boolean busy){
		this.busy = busy;
	}public void setlastTVtype(TvType lastTVtype){
		this.lastTVtype = lastTVtype;
	}public void setprocessTimeAfterFailure(double timeUntilFailure) {
		this.timeUntilFailure = timeUntilFailure;
	}public void settimeUntilFailure(double object){
		this.processTimeAfterFailure = object;
	}public void setlastTVType(TvType tvtype){
		this.lastTVType = tvtype;
	}
	
	
	
	public TvType getlastTVType(){
		return this.lastTVType;
	}public Boolean getBusy(){
		return this.busy;
	}public TvType getlastTVtype(){
		return this.lastTVtype;
	}public double getprocessTimeAfterFailure() {
		return this.timeUntilFailure;
	}public double gettimeUntilFailure(){
		return this.processTimeAfterFailure;
	}
	public double getTimeUntilFailure() {
		// TODO Auto-generated method stub
		return timeUntilFailure;
	}
	public void setTimeUntilFailure(double d) {
		// TODO Auto-generated method stub
		timeUntilFailure = d;
		
	}
	
	

}
