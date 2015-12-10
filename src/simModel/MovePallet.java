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
	private int palletId;
	private int segmentID;
	
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
		palletId = this.model.udp.GetPalletReadyForMoving();
		model.pallets[palletId].inMotion = true;
		model.pallets[palletId].finishedProcessing = false;
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
		
		int headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
		
		if(this.model.pallets[palletId].currPosition < headOfSegment)
		{
			int currPosition = this.model.pallets[palletId].currPosition;
			this.model.conveyorSegments[segmentID].positions[currPosition] = null;
			currPosition++;
			this.model.pallets[palletId].currPosition = currPosition;
			this.model.conveyorSegments[segmentID].positions[currPosition] = this.model.pallets[palletId];
			
		} else if(this.model.pallets[palletId].currPosition == headOfSegment){
			
			if(this.model.pallets[palletId].currConveyor == Const.CS_RETEST){
				
				this.model.conveyorSegments[segmentID].positions[headOfSegment]= null;
				int testHeadOfSegment = this.model.conveyorSegments[Const.CS_TEST].getCapacity() - 1;
				this.model.conveyorSegments[Const.CS_TEST].positions[testHeadOfSegment] = this.model.pallets[palletId];
				this.model.pallets[palletId].currConveyor = Const.CS_TEST;
				this.model.pallets[palletId].currPosition = testHeadOfSegment;
				
			}else if(this.model.pallets[palletId].moveRework){
				
				this.model.conveyorSegments[segmentID].positions[headOfSegment]= null;
				this.model.conveyorSegments[Const.CS_REWORK].positions[0] = this.model.pallets[palletId];
				this.model.pallets[palletId].currConveyor = Const.CS_REWORK;
				this.model.pallets[palletId].currPosition = 0;
				this.model.pallets[palletId].moveRework = false;
				
			}else{
				
				this.model.conveyorSegments[segmentID].positions[headOfSegment]= null;
				int nextSeg = this.model.conveyorSegments[segmentID].nextConveyor;
				this.model.conveyorSegments[nextSeg].positions[0] = this.model.pallets[palletId];
				this.model.pallets[palletId].currConveyor = nextSeg;
				this.model.pallets[palletId].currPosition = 0;
			}
		}
		
		this.model.pallets[palletId].inMotion = false;
	}
	

}
