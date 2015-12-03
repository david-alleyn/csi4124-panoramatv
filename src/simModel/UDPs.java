package simModel;

public class UDPs 
{
	PanoramaTV model;  // for accessing the clock
	
	// Constructor
	protected UDPs(PanoramaTV model) { this.model = model; }


	public int GetManualNodeProcessTime(int manualNode){
		return -1;
	}
	
	public int GetAssociatedSegmentID(int NodeID){
		return -1;
	}
	
	public int GetAutoNodeReadyForProcessing(){
		return -1;
	}
	
	public int GetAutoNodeForPartialProcessing(){
		return -1;
	}
	
	public int GetAutoNodeRequiringRetooling(){
		return -1;
	}
	
	public int GetAutoNodeRequiringRepair(){
		return -1;
	}
	
	public int GetManualNodeReadyForProcessing(){
		return -1;
	}
	
	public int GetNodeRepairTime(int autoNodeID){
		return -1;
	}
	
	public int GetPalletReadyForMoving(){
		return -1;
	}



}

