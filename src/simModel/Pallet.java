package simModel;
/**
 * 
 * @author mush
 * This consumer entity structure represents the TV 
 * that arrive in the load segment and leave in the unload segment.
 */
public class Pallet {
	
	TvType TvType;
	Boolean inMotion = false;
	Boolean finishedProcessing = false;
	Boolean moveRework = false;
	/** The current position of the pallet on the conveyor segment which it occupies. currentPosition = 0 */
	int currPosition = 0; 
	/** The current conveyor that the pallet is occupying */
	int currConveyor = -1;
}
