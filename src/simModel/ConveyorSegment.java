package simModel;

import java.util.ArrayList;

/**
 * 
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
	private int nextConveyor = -1;
	private ArrayList<Pallet> position ;
	/** Total number of positions */
	private int Capacity;
	
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
		return this.Capacity;
	}
	public void setCapacity(int capacity){
		this.Capacity = capacity;
	}
}

