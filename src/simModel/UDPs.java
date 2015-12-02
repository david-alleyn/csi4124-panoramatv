package simModel;

public class UDPs 
{
	PanoramaTV model;  // for accessing the clock
	Boolean palletAvailable = true;
	Pallet type ;
	AutoNode localNode;
	
	// Constructor
	protected UDPs(PanoramaTV model) { this.model = model; }

	// Translate User Defined Procedures into methods
    /*-------------------------------------------------
	                       Example
	    protected int ClerkReadyToCheckOut()
        {
        	int num = 0;
        	Clerk checker;
        	while(num < model.NumClerks)
        	{
        		checker = model.Clerks[num];
        		if((checker.currentstatus == Clerk.status.READYCHECKOUT)  && checker.list.size() != 0)
        		{return num;}
        		num +=1;
        	}
        	return -1;
        }
	------------------------------------------------------------*/
	/**
	 * Returns the type of TV mould to load when OP10 queries this procedure.
	 * The type returned follows the production schedule. 
	 * The type will go to the next type when OP10 releases the last of the series of the current type. 
	 * When the last of the series of the last type is released from OP10, 
	 * the cycle starts at the beginning of the production schedule again.
	 * @param procedure
	 */
	protected Pallet uTvType(DVPs procedure){
		return null;
	}
	/**
	 * 
	 */
	protected void ConveyerReady(){
		if (!RepairCheck() && !SetUp() && !Busy())
		{
			// do conveyer activity.
		}
	}
	/**
	 * 
	 */
	protected void ConveyorSegmentReadyForTV(){
	
	}
	/**
	 * 
	 * @return
	 */
	public Boolean  RepairCheck( ){
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public Boolean  SetUp( ){
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public Boolean  Busy( ){
		return false;
	}
	/**
	 * 
	 * @return
	 */
	
	public int ManualNodesReadyForProcessing(){
		return -1;
	}
	/**
	 * 
	 * @return
	 */
	public double uAutomaticProcessTime( ){
		if (localNode.equals("OP20")) return 2.1;
		else if (localNode.equals("OP30")) return 2.0;
		else if (localNode.equals("OP40")) return 1.5;
		else if (localNode.equals("OP50")) return 2.1;
		else return 1.5;
	}

	public AutoNode GetAutoNodeForPartialProcessing() {
		// TODO Auto-generated method stub
		return null;
	}
	

}

