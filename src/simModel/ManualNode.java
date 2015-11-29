package simModel;
/**
 * The 8 manual segments in the system.  
 * OP10, Rework, OP60, OP40a, OP40b, OP40c, OP40d, OP40e are the eight identifiers of the segment
 * @author mush
 */

public class ManualNode {
	/**
	 * Set to TRUE when the Operator is processing a TV and FALSE otherwise
	 */
	private Boolean busy;
	
	public void setBusy(Boolean busy){
		this.busy = busy;
	}
	public Boolean getBusy(){
		return this.busy;
	}

}
