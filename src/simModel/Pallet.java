package simModel;
/**
 * 
 * @author mush
 * This consumer entity structure represents the TV 
 * that arrive in the load segment and leave in the unload segment.
 */
public class Pallet {
	
	Type TuType;
	Boolean inMotion = false;
	Boolean finishedProcessing = false;
	Boolean moveRework = false;
}
