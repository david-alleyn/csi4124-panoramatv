package simModel;

import java.util.ArrayList;

/**
 * 
 * @author mush
 *
 */
public class Conveyor {
/**
 * 
numPallet The number of Pallet’s on the conveyor segment
positions :: The array of Pallet entities on the conveyor segment
capacity :: Total number of positions

 */
	private int numPallets;
	private ArrayList<Pallet> position ;
	private int Capacity;
	
	public Conveyor()
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
}

