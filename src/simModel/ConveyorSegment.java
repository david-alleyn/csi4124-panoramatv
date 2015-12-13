package simModel;

/**
 * The 13 conveyor segments in the system. CS_OP10, CS_OP20, CS_OP30, CS_OP40a, CS_OP40b, CS_OP40c, CS_OP40d,
 *  CS_OP40e, CS_OP50, CS_TEST, CS_REWORK, CS_OP60, CS_RETEST are the nine identifiers of the segment

 * @author mush
 *
 */
public class ConveyorSegment {
/**
 * 
numPallet The number of Pallets on the conveyor segment
positions :: The array of Pallet entities on the conveyor segment
capacity :: Total number of positions

 */
	/** The index of the next logical default segment which follows from this one.
	 */
	public int nextConveyor;
	public Pallet[] positions;
	/** Total number of positions */
	private int capacity;
	
	public ConveyorSegment(int capacity)
	{
		this.capacity = capacity;
		positions = new Pallet[capacity];
	}
	public int size()
	{
		return positions.length;
	}
	public int getnextConveyor(){
		return nextConveyor;
	}
	public void setnextConveyor(int nextConveyor){
		this.nextConveyor = nextConveyor;
	}

	public int getCapacity() {
		// TODO Auto-generated method stub
		return this.capacity;
	}
	public void setCapacity(int capacity){
		this.capacity = capacity;
		
		//RESETS THE CONVEYORSEGMENT
		positions = new Pallet[capacity];
	}
}

