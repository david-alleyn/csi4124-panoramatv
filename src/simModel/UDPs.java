package simModel;

public class UDPs 
{
	PanoramaTV model;  // for accessing the clock
	
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
	protected void uTvType(DVPs procedure){
		
	}
	protected void ConveyerReady(){
		
	}
	protected void ConveyorSegmentReadyForTV(){
	
	}
	
}
