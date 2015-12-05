package simModel;

class DVPs 
{
	PanoramaTV model;  // for accessing the clock
	
	TvType tvType = TvType.Small;
	int numCurrentTVType;
	
	/**
	 * 
	 * @param model : 
	 */
	protected DVPs(PanoramaTV model) { this.model = model; }

	// Translate deterministic value procedures into methods
        /* -------------------------------------------------
	                       Example
	protected double getEmpNum()  // for getting next value of EmpNum(t)
	{
	   double nextTime;
	   if(model.clock == 0.0) nextTime = 90.0;
	   else if(model.clock == 90.0) nextTime = 210.0;
	   else if(model.clock == 210.0) nextTime = 420.0;
	   else if(model.clock == 420.0) nextTime = 540.0;
	   else nextTime = -1.0;  // stop scheduling
	   return(nextTime);
	}
	------------------------------------------------------------*/
	
	public double uManualProcessTime(int index)
	{
		return -0.1;
	}
	public TvType uTvType()
	{
		if(tvType == TvType.Small)
		{
			if(numCurrentTVType <= 20)
			{
				return tvType;
			}
		}
		
		return null;
	}

	public int uAutomaticProcessTime(int autoNodeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double uElectronicAssemblyTime() {
		// TODO Auto-generated method stub
		return 1.5;
	}
}
