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
	@Override
	protected double duration() {
		// TODO Auto-generated method stub
		return 0.5;
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
		
		// TODO Auto-generated method stub
		if(this.model.pallets[pallet].currPosition == (capacity-1)){
			
			
			if(this.model.pallets[pallet].moveRework){
				this.model.conveyorSegments[segmentID].positions[capacity-1]= null;
				this.model.conveyorSegments[Const.CS_REWORK].positions[0] = this.model.pallets[pallet];
				this.model.pallets[pallet].currConveyor = Const.CS_REWORK;
				this.model.pallets[pallet].currPosition = 0;
			}else{
				this.model.conveyorSegments[segmentID].positions[capacity-1]= null;
				int nextSeg = this.model.conveyorSegments[segmentID].nextConveyor;
				this.model.conveyorSegments[nextSeg].positions[0] = this.model.pallets[pallet];
				this.model.pallets[pallet].currConveyor = nextSeg;
				this.model.pallets[pallet].currPosition = 0;
				
			}
		} else {
			int tempVar =this.model.pallets[pallet].currPosition;
			this.model.conveyorSegments[segmentID].positions[tempVar]=null;
			this.model.pallets[pallet].currPosition++;
			tempVar = this.model.pallets[pallet].currPosition;
			this.model.conveyorSegments[segmentID].positions[tempVar]= 
					this.model.pallets[pallet];
		}

		this.model.pallets[pallet].inMotion = false;
		this.model.pallets[pallet].finishedProcessing = false;
	

	}
	/**
	 * moveablePallet ← UDP.GetPalletReadyForMoving()
	 * TRUE if moveablePallet is NOT -1
	 * FALSE if moveablePallet is -1

	 * @param model
	 */
	public static boolean preconditon(PanoramaTV model)
	{
		int moveablePallet = model.udp.GetPalletReadyForMoving();
		return (moveablePallet != -1);
	}
	

}
