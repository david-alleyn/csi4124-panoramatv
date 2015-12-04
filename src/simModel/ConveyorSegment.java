package simModel;

import java.util.ArrayList;

/**
 * The 13 conveyor segments in the system. CS_OP10, CS_OP20, CS_OP30, CS_OP40a, CS_OP40b, CS_OP40c, CS_OP40d,
 *  CS_OP40e, CS_OP50, CS_TEST, CS_REWORK, CS_OP60, CS_RETEST are the nine identifiers of the segment

 * @author mush
 *
 */
public class ConveyorSegment {
/**
 * 
numPallet The number of Pallet�s on the conveyor segment
positions :: The array of Pallet entities on the conveyor segment
capacity :: Total number of positions

 */
	/** The number of Pallet�s on the conveyor segment
	 */
	private int numPallets;
	/** The index of the next logical default segment which follows from this one.
	 */
	public int nextConveyor = -1;
	private ArrayList<Pallet> position ;
	/** Total number of positions */
	private int capacity;
	
	public ConveyorSegment()
	{
		position = new ArrayList<Pallet>() ;
	}
	public int size()
	{
		return position.size();
	}
	public void set(Pallet local)
	{
		position.add(local);
	}
	public Pallet get(int index)
	{
		return position.get(index);
	}
	public Pallet last()
	{
		return get((size()-1));
	}
	public int getnextConveyor(){
		return nextConveyor;
	}
	public void setnextConveyor(int nextConveyor){
		this.nextConveyor = nextConveyor;
	}
	
	public int getnextnumPallets(){
		return numPallets;
	}
	public void setnumPallets(int numPallets){
		this.numPallets = numPallets;
	}
	public int getCapacity() {
		// TODO Auto-generated method stub
		return this.capacity;
	}
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	public ArrayList<Pallet> getPosition() {
		return position;
	}
}

