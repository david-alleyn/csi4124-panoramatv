package simModel;

import simulationModelling.ConditionalActivity;
/**
 * The activity moves pallets from position to position on a conveyor segment at a linear rate. 
 * It checks to see if the next position in a ConveyorSegment is free or that the pallet in that position is in motion, 
 * then schedules a move. If the pallet is at the end of a conveyor segment, 
 * then it checks to see if the pallet is fully processed before evaluating the other conditions and scheduling a move.
 * @author mush
 *
 */
public class MovePallet extends ConditionalActivity {

	private PanoramaTV model;
	private int pallet;
	private int segmentID;
	private int capacity;
	
	public MovePallet(PanoramaTV panoramaTV) {
		model = panoramaTV;
		// TODO Auto-generated constructor stub
	}
	

	public static boolean preconditon(PanoramaTV model)
	{
		int moveablePallet = model.udp.GetPalletReadyForMoving();
		return (moveablePallet != -1);
	}
	
	/**
	 * pallet ← UDP.GetPalletReadyForMoving()
	 * currPosition ← RC.Pallets[pallet].currPosition;
	 * RC.Pallets[pallet].inMotion ← TRUE
	 */
	@Override
	public void startingEvent() {
		pallet = this.model.udp.GetPalletReadyForMoving();
		segmentID = this.model.pallets[pallet].currConveyor;
		capacity = this.model.conveyorSegments[segmentID].getCapacity();
		model.pallets[pallet].inMotion = true;
		
		// TODO Auto-generated method stub

	}
	
	@Override
	protected double duration() {
		return 0.5;
	}
	/**
	 * 
IF(RC.Pallets[pallet].moveRework = TRUE)

     RC.Pallets[pallet].currConveyor.positions[currPosition] ←  NULL
     RQ.ConveyorSegment[CS_REWORK].positions[0] ←  RC.Pallets[pallet]
     RC.Pallets[pallet].currConveyor = CS_REWORK;
     RC.Pallets[pallet].currPosition = 0;
ENDIF

ELSE
RC.Pallets[pallet].currConveyor.positions[currPosition] ←  NULL;
RC.Pallets[pallet].currConveyor ← RC.Pallets[pallet].currConveyor.nextConveyor;
RQ.ConveyorSegment[RC.Pallets[pallet].currConveyor].positions[0] ←  RC.Pallets[pallet];
RC.Pallets[pallet].currPosition = 0;

	 */
	@Override
	protected void terminatingEvent() {
		
		int nextPosition;
		int currConveyorId = model.pallets[pallet].currConveyor;
		int headOfSegment = model.conveyorSegments[currConveyorId].getCapacity() - 1;

		if(model.pallets[pallet].currPosition < headOfSegment)
		{
			nextPosition = model.pallets[pallet].currPosition + 1;

			model.conveyorSegments[currConveyorId].positions[model.pallets[pallet].currPosition] = null;
			model.pallets[pallet].currPosition = nextPosition;
			model.conveyorSegments[currConveyorId].positions[nextPosition] = model.pallets[pallet];
		}
		else
		{
			if(currConveyorId == Const.CS_OP60)
				model.output.numTVAssembled++;
			
			nextPosition = 0;
			
			model.conveyorSegments[currConveyorId].positions[model.pallets[pallet].currPosition] = null;
			model.pallets[pallet].currPosition = nextPosition;
			
			int nextConveyor;
			if(model.pallets[pallet].moveRework)
			{
				nextConveyor = Const.CS_REWORK;
			}
			else{
				nextConveyor = model.conveyorSegments[currConveyorId].nextConveyor;
			}
			
			model.pallets[pallet].currConveyor = nextConveyor;
			model.conveyorSegments[nextConveyor].positions[nextPosition] = model.pallets[pallet];

			if(currConveyorId == Const.CS_RETEST)
				model.pallets[pallet].finishedProcessing = true;
			else		   
				model.pallets[pallet].finishedProcessing = false;
		}
		
		model.pallets[pallet].inMotion = false;
		
//		rcPallet.moving = false;
//		
//		int headOfSegment = capacity - 1;
//		
//		if(this.model.pallets[pallet].currPosition < headOfSegment)
//		{
//			int tempVar =this.model.pallets[pallet].currPosition;
//			this.model.conveyorSegments[segmentID].positions[tempVar] = null;
//			this.model.pallets[pallet].currPosition++;
//			tempVar = this.model.pallets[pallet].currPosition;
//			this.model.conveyorSegments[segmentID].positions[tempVar] = this.model.pallets[pallet];
//		}
//		else if(this.model.pallets[pallet].currPosition == headOfSegment){
//			
//			if(this.model.pallets[pallet].currConveyor == Const.CS_RETEST){
//				this.model.conveyorSegments[segmentID].positions[headOfSegment]= null;
//				int testHeadOfSegment = this.model.conveyorSegments[Const.CS_TEST].getCapacity() - 1;
//				this.model.conveyorSegments[Const.CS_TEST].positions[0] = this.model.pallets[pallet];
//				this.model.pallets[pallet].currConveyor = Const.CS_TEST;
//				this.model.pallets[pallet].currPosition = 0;
//				this.model.pallets[pallet].finishedProcessing = true;
//				
//			}else if(this.model.pallets[pallet].moveRework){
//				this.model.conveyorSegments[segmentID].positions[headOfSegment]= null;
//				this.model.conveyorSegments[Const.CS_REWORK].positions[0] = this.model.pallets[pallet];
//				this.model.pallets[pallet].currConveyor = Const.CS_REWORK;
//				this.model.pallets[pallet].currPosition = 0;
//			}else{
//				this.model.conveyorSegments[segmentID].positions[headOfSegment]= null;
//				int nextSeg = this.model.conveyorSegments[segmentID].nextConveyor;
//				this.model.conveyorSegments[nextSeg].positions[0] = this.model.pallets[pallet];
//				this.model.pallets[pallet].currConveyor = nextSeg;
//				this.model.pallets[pallet].currPosition = 0;
//			}
//		}
//		
//		if(segmentID == Const.OP60){
//			model.output.numTVAssembled++;
//		}
//		
//		this.model.pallets[pallet].inMotion = false;
//		this.model.pallets[pallet].finishedProcessing = false;
	}
	

}
