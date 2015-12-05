package simModel;

import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;

public class UDPs 
{
	PanoramaTV model;  // for accessing the clock
	
	// Constructor
	protected UDPs(PanoramaTV model) { this.model = model; }


	public double GetManualNodeProcessTime(int manualNode){
		if (manualNode == Const.OP10) { return model.rvp.uLoadTv(); }
		else if (manualNode == Const.OP40A || 
				manualNode == Const.OP40B || 
				manualNode == Const.OP40C || 
				manualNode == Const.OP40D || 
				manualNode == Const.OP40E) {  return model.dvp.uElectronicAssemblyTime(); }
		else if (manualNode == Const.REWORK) { return model.rvp.uReworkTime(); }
		else if (manualNode == Const.OP60) { return model.rvp.uUnLoadTv(); }
		
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
	/**
	 * For every RC.AutoNode (nodeID) in the system which is not busy:
	 * segmentID ← GetAssociatedSegmentID(nodeID)
	 * capacity ← RQ.ConveyorSegment[segmentID].capacity
	 * palletID ← RQ.ConveyorSegment[segmentID].positions[capacity - 1]
	 * If (node.timeUntilFailure > RVP.uAutomaticProcessTime()
	 * 
	 * AND RQ.ConveyorSegment[SegmentID].positions[capacity - 1].lastTVType 
	 * 
	 * NOT EQUAL TO RQ.ConveyorSegment[SegmentID].positions[palletID].tvType
	 * 
	 * AND RQ.ConveyorSegment[SegmentID].positions[palletID].inMotion = FALSE
	 * 
	 * AND R.Maintenance.busy = FALSE) 
	 * 
	 * Then @return the nodeID
	 * 
	 * Else @return -1	 
	 * 
	 */
	public int GetAutoNodeRequiringRetooling(){
		for (int index = 0; index < model.AutoNodeArray.length ; index++){
			int segmentID = GetAssociatedSegmentID(index, true);
			ConveyorSegment capacity = model.ConveyorSeg[segmentID];
			Pallet palletID = model.ConveyorSeg[segmentID].get(model.AutoNodeArray.length -1);
			
			if((model.AutoNodeArray[index].gettimeUntilFailure() > model.dvp.uManualProcessTime(index))
					&&(false)
					&&(palletID.inMotion == false)
					&&(false))
				return index;
		}
		return -1;
	}
	/**
	 * For every RC.AutoNode (nodeID) in the system which is busy:
	 * If (node.timeUntilFailure LESS THAN OR EQUAL TO 0 
	 * AND
	 * R.Maintenance.busy = FALSE)
	 * Then return the nodeID
	 * Else Return -1

	 * @return
	 */
	public int GetAutoNodeRequiringRepair(){
		for (int index = 0; index < model.AutoNodeArray.length ; index++){
			if (model.AutoNodeArray[index].getprocessTimeAfterFailure() <= 0 && 
				(false)	)
				return index;
		}
		return -1;
	}
	/**
	 * For every R.ManualNode (nodeID) in the system which is not busy:
	 * segmentID ← GetAssociatedSegmentID(nodeID, isAutoNode)
	 * capacity ← RQ.ConveyorSegment[segmentID].capacity
	 * if(RQ.ConveyorSegment[segmentID].positions[capacity - 1] is NOT NULL
	 * AND
	 * RQ.ConveyorSegment[segmentID].positions[capacity - 1].inMotion = FALSE)
	 * Return nodeID
	 * Else Return -1;
	 * @return
	 */
	public int GetManualNodeReadyForProcessing(){
		for (int index = 0; index < model.ManualNodes.length ; index++){
			if (model.ManualNodes[index].getBusy() == false){
				int segmentID = this.GetAssociatedSegmentID(index, false);
				ConveyorSegment capacity = model.ConveyorSeg[segmentID];
				
				if (model.ConveyorSeg[index].get(model.ManualNodes.length-1) != null) 
						if(model.ConveyorSeg[index].get(model.ManualNodes.length-1).inMotion == false)
							return index;
				
			}
		}
				return -1;
	}
	/**
	 * If (autoNodeID = OP20) THEN Return RVP.uOP20RepairTime()
	 * Else If (autoNodeID = OP30) THEN Return RVP.uOP30RepairTime()
	 * Else If (autoNodeID = OP50) THEN Return RVP.uOP50RepairTime()
	 * Else If (autoNodeID = TEST) THEN Return RVP.uTESTRepairTime()

	 * @param autoNodeID
	 * @return 
	 */
	public double GetNodeRepairTime(int autoNodeID){
		if (autoNodeID == Const.OP20)
			return model.rvp.uOP20RepairTime();		
		else if (autoNodeID == Const.OP30)
			return model.rvp.uO30RepairTime();
		else if (autoNodeID == Const.OP50)
			return model.rvp.uOP50RepairTime();
		else if (autoNodeID == Const.TEST)
			return model.rvp.uTESTRepairTime();
		
		return -1;
	}
	
	public int GetPalletReadyForMoving(){
		return -1;
	}



}

