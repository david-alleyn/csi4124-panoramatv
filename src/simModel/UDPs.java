package simModel;

public class UDPs 
{
	PanoramaTV model;  // for accessing the clock
	
	// Constructor
	protected UDPs(PanoramaTV model) { this.model = model; }


	public int GetManualNodeProcessTime(int manualNode){
		if (manualNode == Const.OP10) { return model.rvp.uLoadTv(); }
		else if (manualNode == Const.OP40A || 
				manualNode == Const.OP40B || 
				manualNode == Const.OP40C || 
				manualNode == Const.OP40D || 
				manualNode == Const.OP40E) {  return model.rvp.uElectronicAssemblyTime(); }
		else if (manualNode == Const.REWORK) { return model.rvp.uReworkTime(); }
		else if (manualNode == Const.OP60) { return model.dvp.uUnLoadTv(); }
		
		return -1;
	}
	
	public int GetAssociatedSegmentID(int NodeID, boolean isAutoNode){
		
		if(isAutoNode)
		{
			switch(NodeID){
				case Const.OP20:
					return Const.CS_OP20;
				case Const.OP30:
					return Const.CS_OP30;
				case Const.TEST:
					return Const.CS_TEST;
				case Const.OP50:
					return Const.CS_OP50;
				default:
					return -1;
			}
		}
		else
		{
			switch (NodeID) {
			case Const.OP10:
				return Const.CS_OP10;
			case Const.OP40A:
				return Const.CS_OP40A;
			case Const.OP40B:
				return Const.CS_OP40B;
			case Const.OP40C:
				return Const.CS_OP40C;
			case Const.OP40D:
				return Const.CS_OP40D;
			case Const.OP40E:
				return Const.CS_OP40E;
			case Const.REWORK:
				return Const.CS_REWORK;
			default:
				return -1;
			}
		}
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

