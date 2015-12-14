package simModel;

import java.util.Queue;


/**
 * @author mush
 */
public class Maintenance {
	
	private Queue<AutoNode>   Repair;
	private Queue<AutoNode>   Setup;
	public boolean busy = false;
	
	public void addRepair(AutoNode current){
		Repair.add(current);
	}
	public void addSetup(AutoNode current){
		Setup.add(current);
	}
	public AutoNode getRepair(){
		return Repair.remove();
	}
	public AutoNode getSetup(){
		return Setup.remove();
	}
	

	public void setBusy(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public boolean getBusy() {
		// TODO Auto-generated method stub
		return busy;
	}

}
