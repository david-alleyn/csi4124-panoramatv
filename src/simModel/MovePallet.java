package simModel;

import simulationModelling.ConditionalActivity;
/**
 * The activity moves pallets from position to position on a conveyor segment at a linear rate. 
 * It checks to see if the next position in a ConveyorSegment is free or that the palletID in that position is in motion,
 * then schedules a move. If the palletID is at the end of a conveyor segment,
 * then it checks to see if the palletID is fully processed before evaluating the other conditions and scheduling a move.
 * @author mush
 *
 */
public class MovePallet extends ConditionalActivity {

	private PanoramaTV model;
	private int palletID;
	private int segmentID;
	private int headOfSegment;
	private int position;

	public MovePallet(PanoramaTV panoramaTV) {
		model = panoramaTV;
	}
	

	public static boolean preconditon(PanoramaTV model)
	{
		int moveablePallet = model.udp.GetPalletReadyForMoving();
		return (moveablePallet != -1);
	}
	
	/**
	 * palletID ← UDP.GetPalletReadyForMoving()
	 * currPosition ← RC.Pallets[palletID].currPosition;
	 * RC.Pallets[palletID].inMotion ← TRUE
	 */
	@Override
	public void startingEvent() {
		palletID = model.udp.GetPalletReadyForMoving();
		segmentID = model.pallets[palletID].currConveyor;
		headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
		position = model.pallets[palletID].currPosition;

		//For debugging purposes
		if(palletID != model.conveyorSegments[segmentID].palletPositions[position])
		{
			System.out.println("ERROR: MovePallet startingEvent: \nPallet " + palletID + "'s currPosition not equal to actual position");
			try {
				Thread.sleep(2000);
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}



		int nextPosition = 0;

		if(position < headOfSegment)
		{
			model.conveyorSegments[segmentID].palletPositions[position] = -1;
			model.pallets[palletID].currPosition++;
			model.conveyorSegments[segmentID].palletPositions[position + 1] = palletID;
			if(segmentID != Const.CS_RETEST){
				model.pallets[palletID].finishedProcessing = false;
			}
		}
		else
		{
			if(segmentID == Const.CS_OP60) {
				model.output.numTVAssembled++;
			}


			int nextConveyor;
			if(model.pallets[palletID].moveRework)
			{
				nextConveyor = Const.CS_REWORK;
				model.pallets[palletID].moveRework = false;
			}
			else{
				nextConveyor = model.conveyorSegments[segmentID].nextConveyor;
			}

			if( nextConveyor == Const.CS_TEST  && segmentID == Const.CS_RETEST)
			{
				int testHead = model.conveyorSegments[Const.CS_TEST].getCapacity() - 1;
				nextPosition = testHead;
			}

			//For debugging purposes
			if(model.conveyorSegments[nextConveyor].palletPositions[nextPosition] != -1)
			{
				System.out.println("ERROR: MovePallet terminatingEvent: \nPallet " + palletID + " is being moved to an occupied position on the next segment!");
				try {
					Thread.sleep(2000);
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}

			model.pallets[palletID].currConveyor = nextConveyor;
			model.pallets[palletID].currPosition = nextPosition;

			model.conveyorSegments[segmentID].palletPositions[position] = -1;
			model.conveyorSegments[nextConveyor].palletPositions[nextPosition] = palletID;

			if(nextConveyor == Const.CS_RETEST)
			{
				model.pallets[palletID].finishedProcessing = true;
			}
			else
			{
				model.pallets[palletID].finishedProcessing = false;
			}

		}



		model.pallets[palletID].inMotion = true;
	}
	
	@Override
	protected double duration() {
		return 0.5;
	}

	@Override
	protected void terminatingEvent() {
		//For debugging purposes
//		if(palletID != model.conveyorSegments[segmentID].palletPositions[position])
//		{
//			System.out.println("ERROR: MovePallet terminatingEvent: \nPallet " + palletID + " has been moved from it's position illegally");
//			System.out.println("Original Position: " + model.pallets[palletID].currPosition);
//			try {
//				Thread.sleep(2000);
//			}
//			catch( Exception e )
//			{
//				e.printStackTrace();
//			}
//		}

		//BEGIN HERE

//		int nextPosition = 0;
//
//		if(position < headOfSegment)
//		{
//			model.conveyorSegments[segmentID].palletPositions[position] = -1;
//			model.pallets[palletID].currPosition++;
//			model.conveyorSegments[segmentID].palletPositions[position + 1] = palletID;
//			if(segmentID != Const.CS_RETEST){
//				model.pallets[palletID].finishedProcessing = false;
//			}
//		}
//		else
//		{
//			if(segmentID == Const.CS_OP60) {
//				model.output.numTVAssembled++;
//			}
//
//
//			int nextConveyor;
//			if(model.pallets[palletID].moveRework)
//			{
//				nextConveyor = Const.CS_REWORK;
//				model.pallets[palletID].moveRework = false;
//			}
//			else{
//				nextConveyor = model.conveyorSegments[segmentID].nextConveyor;
//			}
//
//			if( nextConveyor == Const.CS_TEST  && segmentID == Const.CS_RETEST)
//			{
//				int testHead = model.conveyorSegments[Const.CS_TEST].getCapacity() - 1;
//				nextPosition = testHead;
//			}
//
//			//For debugging purposes
//			if(model.conveyorSegments[nextConveyor].palletPositions[nextPosition] != -1)
//			{
//				System.out.println("ERROR: MovePallet terminatingEvent: \nPallet " + palletID + " is being moved to an occupied position on the next segment!");
//				try {
//					Thread.sleep(2000);
//				}
//				catch( Exception e )
//				{
//					e.printStackTrace();
//				}
//			}
//
//			model.pallets[palletID].currConveyor = nextConveyor;
//			model.pallets[palletID].currPosition = nextPosition;
//
//			model.conveyorSegments[segmentID].palletPositions[position] = -1;
//			model.conveyorSegments[nextConveyor].palletPositions[nextPosition] = palletID;
//
//			if(nextConveyor == Const.CS_RETEST)
//			{
//				model.pallets[palletID].finishedProcessing = true;
//			}
//			else
//			{
//				model.pallets[palletID].finishedProcessing = false;
//			}
//
//		}
		
		model.pallets[palletID].inMotion = false;
	}
	

}
