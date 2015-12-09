package simModel;
/**
 * @author mush
 */
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
		
		//Iterate from the end to the beginning of the array.
		for(int i = model.autoNodes.length - 1; i >= 0; i--)
		{
			if(!model.autoNodes[i].getBusy())
			{
				int segmentID = model.udp.GetAssociatedSegmentID(i, true);
				int capacity = model.conveyorSegments[segmentID].getCapacity();
				int palletID = i;
				
				if(model.autoNodes[palletID].getTimeUntilFailure() > model.dvp.uAutomaticProcessTime(palletID)
					&& model.autoNodes[i].getlastTVType() == model.conveyorSegments[segmentID].getPosition().get(palletID).tvType
					&& model.conveyorSegments[segmentID].getPosition().get(palletID).inMotion == false)
				{
					return i;
				}
			}
		}
		
		return -1;
	}
	
	public int GetAutoNodeForPartialProcessing(){
		//Iterate from the end to the beginning of the array.
		for(int i = model.autoNodes.length - 1; i >= 0; i--)
		{
			if(!model.autoNodes[i].getBusy())
			{
				int segmentID = model.udp.GetAssociatedSegmentID(i, true);
				int capacity = model.conveyorSegments[segmentID].getCapacity();
				int palletID = i;
				
				if(model.autoNodes[palletID].getTimeUntilFailure() < model.dvp.uAutomaticProcessTime(palletID)
					&& model.autoNodes[i].getlastTVType() == model.conveyorSegments[segmentID].getPosition().get(palletID).tvType
					&& model.conveyorSegments[segmentID].getPosition().get(palletID).inMotion == false)
				{
					return i;
				}
			}
		}
		
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
		for (int index = model.autoNodes.length - 1; index >= 0 ; index--){
			int segmentID = GetAssociatedSegmentID(index, true);
			int capacity = model.conveyorSegments[segmentID].getCapacity();
			
			if((model.autoNodes[index].getTimeUntilFailure() > model.dvp.uAutomaticProcessTime(index))
					&&(model.autoNodes[index].getlastTVType() != model.conveyorSegments[segmentID].getPosition().get(capacity - 1).tvType)
					&&(model.conveyorSegments[segmentID].getPosition().get(capacity - 1).inMotion == false)
					&&(model.maintenance.busy == false))
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
		for (int index = model.autoNodes.length - 1; index >= 0 ; index--){
			if (model.autoNodes[index].getTimeUntilFailure() <= 0 && 
				(model.maintenance.busy))
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
		for (int index = model.manualNodes.length - 1; index >= 0 ; index--){
			if (model.manualNodes[index].getBusy() == false){
				int segmentID = this.GetAssociatedSegmentID(index, false);
				ConveyorSegment capacity = model.conveyorSegments[segmentID];
				
				if (model.conveyorSegments[index].get(model.manualNodes.length-1) != null) 
						if(model.conveyorSegments[index].get(model.manualNodes.length-1).inMotion == false)
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
	/**
	 * 
	 * @return
	 */
	public int GetPalletReadyForMoving(){
		for(int i = model.pallets.length - 1; i >= 0; i--)
		{
			if(!model.pallets[i].inMotion)
			{
				int currConveyor = model.pallets[i].currConveyor;
				int currPosition = model.pallets[i].currPosition;
				if(currPosition < model.conveyorSegments[currConveyor].getCapacity() - 1)
				{
					if(model.conveyorSegments[currConveyor].getPosition().get(currPosition + 1) == null
							|| model.conveyorSegments[currConveyor].getPosition().get(currPosition + 1).inMotion == true)
					{
						return i;
					}
				}
				else if (currPosition == model.conveyorSegments[currConveyor].getCapacity() - 1
						&& model.pallets[i].finishedProcessing == true)
				{
					//evaluated much deeper in this ugly if tree
					int nextconveyor = model.conveyorSegments[model.pallets[i].currConveyor].getnextConveyor();
					int nextcapacity = model.conveyorSegments[nextconveyor].getCapacity();
					
					if(model.pallets[i].currConveyor == Const.CS_RETEST)
					{
						int testcapacity = model.conveyorSegments[Const.CS_TEST].getCapacity();
						if(model.conveyorSegments[Const.CS_TEST].get(testcapacity - 1) == null
								|| model.conveyorSegments[Const.CS_TEST].get(testcapacity - 1).inMotion == true)
						{
							return i;
						}
					}
					else if(model.pallets[i].moveRework)
					{
						int reworkcapacity = model.conveyorSegments[Const.CS_REWORK].getCapacity();
						if(model.conveyorSegments[Const.CS_REWORK].getPosition().get(reworkcapacity - 1) == null
								|| model.conveyorSegments[Const.CS_REWORK].getPosition().get(reworkcapacity - 1).inMotion == true)
						{
							return i;
						}
					}
					else if(model.conveyorSegments[nextconveyor].getPosition().get(nextcapacity - 1) == null
							|| model.conveyorSegments[nextconveyor].getPosition().get(nextcapacity - 1).inMotion == true)
					{
						return i;
					}
				}
			}
		}
		return -1;
	}



}

