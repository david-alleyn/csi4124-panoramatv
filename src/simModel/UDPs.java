package simModel;
/**
 * @author mush
 */

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
			case Const.OP60:
				return Const.CS_OP60;
			default:
				return -1;
			}
		}
	}
	
	public int GetAssociatedNodeID(int segmentId){
		
		switch(segmentId)
		{
		case Const.CS_OP10:
			return Const.OP10;
		case Const.CS_OP20:
			return Const.OP20;
		case Const.CS_OP30:
			return Const.OP30;
		case Const.CS_OP40A:
			return Const.OP40A;
		case Const.CS_OP40B:
			return Const.OP40B;
		case Const.CS_OP40C:
			return Const.OP40C;
		case Const.CS_OP40D:
			return Const.OP40D;
		case Const.CS_OP40E:
			return Const.OP40E;
		case Const.CS_RETEST:
		case Const.CS_TEST:
			return Const.TEST;
		case Const.CS_REWORK:
			return Const.REWORK;
		case Const.CS_OP50:
			return Const.OP50;
		case Const.CS_OP60:
			return Const.OP60;
			default:
				return -1;
		}
	}
	
	public int GetAutoNodeReadyForProcessing(){		
		
		//Iterate from the end to the beginning of the array.
		for(int autoNodeId = model.autoNodes.length - 1; autoNodeId >= 0; autoNodeId--)
		{
			if(!model.autoNodes[autoNodeId].busy)
			{
				int segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
				int headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
				int palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];
				
				if(palletID != -1)
				{
					if(model.autoNodes[autoNodeId].getTimeUntilFailure() > model.dvp.uAutomaticProcessTime(autoNodeId)
							&& model.autoNodes[autoNodeId].lastTVType == model.pallets[palletID].tvType
							&& !model.pallets[palletID].inMotion
							&& !model.pallets[palletID].finishedProcessing)
					{
						return autoNodeId;
					}
				}
			}
		}
		
		return -1;
	}
	
	public int GetAutoNodeForPartialProcessing(){
		//Iterate from the end to the beginning of the array.
		for(int autoNodeId = model.autoNodes.length - 1; autoNodeId >= 0; autoNodeId--)
		{
			if(!model.autoNodes[autoNodeId].busy)
			{
				int segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
				int headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
				int palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];
				
				if(palletID != -1)
				{
					if(model.autoNodes[autoNodeId].getTimeUntilFailure() < model.dvp.uAutomaticProcessTime(autoNodeId)
						&& model.autoNodes[autoNodeId].lastTVType == model.pallets[palletID].tvType
						&& !model.pallets[palletID].inMotion
							&& !model.pallets[palletID].finishedProcessing)
					{
						return autoNodeId;
					}
				}
			}
		}
		
		return -1;
	}

	public int GetAutoNodeRequiringRetooling(){
		for (int autoNodeId = model.autoNodes.length - 1; autoNodeId >= 0 ; autoNodeId--){
			int segmentID = GetAssociatedSegmentID(autoNodeId, true);
			int headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
			int palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];
			
			if (palletID != -1 && !model.autoNodes[autoNodeId].busy) {
				if(/*(model.autoNodes[autoNodeId].getTimeUntilFailure() > model.dvp.uAutomaticProcessTime(autoNodeId))
						&&*/(model.autoNodes[autoNodeId].lastTVType != model.pallets[palletID].tvType)
						&&(!model.pallets[palletID].inMotion)
						&&(!model.maintenance.busy)
						&& !model.pallets[palletID].finishedProcessing)
				{
					return autoNodeId;
				}
			}
		}
		return -1;
	}

	public int GetAutoNodeRequiringRepair(){
		for (int index = model.autoNodes.length - 1; index >= 0 ; index--){
			if (model.autoNodes[index].getTimeUntilFailure() <= 0 && 
				(!model.maintenance.busy) && model.autoNodes[index].busy)
				return index;
		}
		return -1;
	}

	public int GetManualNodeReadyForProcessing() {
		for (int manualNodeId = model.manualNodes.length - 1; manualNodeId >= 0; manualNodeId--) {

			if (!model.manualNodes[manualNodeId].busy) {

				int segmentID = this.GetAssociatedSegmentID(manualNodeId, false);
				int headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
				int palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];

				if (palletID != -1 &&
						!model.pallets[palletID].inMotion &&
						!model.pallets[palletID].finishedProcessing) {

					return manualNodeId;
				}
			}
		}
		return -1;
	}

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

		int selectedPallet = -1;

		for(int segIter = model.conveyorSegments.length - 1; segIter >= 0; segIter--)
		{
			int headOfSegment = model.conveyorSegments[segIter].getCapacity() - 1;

			for(int posIter = model.conveyorSegments[segIter].palletPositions.length - 1; posIter >=0; posIter--)
			{

				if(model.conveyorSegments[segIter].palletPositions[posIter] != -1)
				{
					int palletID = model.conveyorSegments[segIter].palletPositions[posIter];

					if(model.pallets[palletID].inMotion)
					{
						continue;
					}

					if(posIter < headOfSegment)
					{
						//If the pallet is on CS_TEST in the second last position on the conveyor
						// If there is a pallet waiting to arrive from CS_RETEST, skip the pallet being evaluated.
						// This is to free up the next space to allow reworked TVs to be tested first

						if(segIter == Const.CS_TEST && posIter == headOfSegment - 1)
						{
							int retestConveyorHead = model.conveyorSegments[Const.CS_RETEST].getCapacity() - 1;
							int retestHeadPallet = model.conveyorSegments[Const.CS_RETEST].palletPositions[retestConveyorHead];
							if(retestHeadPallet != -1)
							{
								continue;
							}
						}

						//otherwise proceed if the conditions are right

						int palletAhead = model.conveyorSegments[segIter].palletPositions[posIter + 1];

						if(palletAhead == -1 /*|| model.pallets[palletAhead].inMotion*/)
						{
							selectedPallet = palletID;
							break;
						}
					}
					else if (posIter == headOfSegment && model.pallets[palletID].finishedProcessing)
					{
						//evaluated much deeper in this ugly if tree

						if(segIter == Const.CS_RETEST)
						{
							int headOfTest = model.conveyorSegments[Const.CS_TEST].getCapacity() - 1;
							int testHeadPallet = model.conveyorSegments[Const.CS_TEST].palletPositions[headOfTest];

							//head of segment CS_TEST
							//if there is a pallet and it is in motion
							if(testHeadPallet != -1 && !model.pallets[testHeadPallet].inMotion)
							{
								continue;
							}
							else{
								selectedPallet = palletID;
								break;
							}
						}

						if(segIter == Const.CS_TEST && model.pallets[palletID].moveRework)
						{
							//int headOfRetest = model.conveyorSegments[Const.CS_RETEST].getCapacity() - 1;

							int reworkTailPallet = model.conveyorSegments[Const.CS_REWORK].palletPositions[0];

							if(reworkTailPallet == -1 /*|| ( reworkTailPallet != -1 && !model.pallets[reworkTailPallet].inMotion)*/) {
								selectedPallet = palletID;
								break;
							}
							else
								continue;
						}

						int nextConveyor = model.conveyorSegments[segIter].nextConveyor;
						int nextPalletPosition = model.conveyorSegments[nextConveyor].palletPositions[0];

						//I've had to disable inter-segment transfer returns, where the pallet we're evaluating,
						if(nextPalletPosition == -1 /*||
							(model.conveyorSegments[nextConveyor].palletPositions[0] != -1 && model.pallets[nextPalletPosition].inMotion)*/)
						{
							selectedPallet = palletID;
							break;
						}
					}
				}
			}
		}



//		for(int i = 0; i < model.pallets.length; i++)
//		{
//			if(!model.pallets[i].inMotion)
//			{
//				int currConveyor = model.pallets[i].currConveyor;
//				int currPosition = model.pallets[i].currPosition;
//				int headOfSegment = model.conveyorSegments[currConveyor].getCapacity() - 1;
//				if(currPosition < headOfSegment)
//				{
//					//If the pallet is on CS_TEST in the second last position on the conveyor
//					// If there is a pallet waiting to arrive from CS_RETEST, skip the pallet being evaluated.
//					// This is to free up the next space to allow reworked TVs to be tested first
//
//					if(currConveyor == Const.CS_TEST && currPosition == headOfSegment - 1)
//					{
//						int retestConveyorHead = model.conveyorSegments[Const.CS_RETEST].getCapacity() - 1;
//						if(model.conveyorSegments[Const.CS_RETEST].palletPositions[retestConveyorHead] != null)
//						{
//							continue;
//						}
//					}
//
//					//otherwise proceed
//
//					if(model.conveyorSegments[currConveyor].palletPositions[currPosition + 1] == null
//							|| model.conveyorSegments[currConveyor].palletPositions[currPosition + 1].inMotion)
//					{
//						return i;
//					}
//				}
//				else if (currPosition == headOfSegment && model.pallets[i].finishedProcessing)
//				{
//					//evaluated much deeper in this ugly if tree
//
//					if(model.pallets[i].currConveyor == Const.CS_RETEST)
//					{
//						int headOfTest = model.conveyorSegments[Const.CS_TEST].getCapacity() - 1;
//
//						//head of segment CS_TEST
//						//if there is a pallet and it is in motion
//						if(model.conveyorSegments[Const.CS_TEST].palletPositions[headOfTest] != null && !model.conveyorSegments[Const.CS_TEST].palletPositions[headOfTest].inMotion)
//						{
//							continue;
//						}
//					}
//
//					if(model.pallets[i].currConveyor == Const.CS_TEST && model.pallets[i].moveRework)
//					{
//						int headOfRetest = model.conveyorSegments[Const.CS_RETEST].getCapacity() - 1;
//						if(model.conveyorSegments[Const.CS_REWORK].palletPositions[0] == null || ( model.conveyorSegments[Const.CS_REWORK].palletPositions[0] != null && !model.conveyorSegments[Const.CS_REWORK].palletPositions[0].inMotion)) {
//							return i;
//						}
//					}
//
//					int nextConveyor = model.conveyorSegments[model.pallets[i].currConveyor].nextConveyor;
//
//					//I've had to disable inter-segment transfer returns, where the pallet we're evaluating,
//					if(model.conveyorSegments[nextConveyor].palletPositions[0] == null /*||
//							(model.conveyorSegments[nextConveyor].palletPositions[0] != null && model.conveyorSegments[nextConveyor].palletPositions[0].inMotion)*/)
//					{
//						return i;
//					}
//				}
//			}
//		}
		return selectedPallet;
	}



}

