package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import dataModelling.*;


class RVPs extends java.lang.Object
{
	PanoramaTV model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds
	TriangularVariate localTriangularVariate;

	// Constructor
	protected RVPs(PanoramaTV model, Seeds sd) 
	{ 
		this.model = model; 
		// Set up distribution functions
		interArrDist = new Exponential(1.0/WMEAN1,  
				                       new MersenneTwister(sd.seed1));
		localTriangularVariate = new TriangularVariate(10, 30, 10, new MersenneTwister());

	}
	
	/* Random Variate Procedure for Arrivals */
	private Exponential interArrDist;  // Exponential distribution for interarrival times
	private final double WMEAN1=10.0;
	protected double duInput()  // for getting next value of duInput
	{
	    double nxtInterArr;

        nxtInterArr = interArrDist.nextDouble();
	    // Note that interarrival time is added to current
	    // clock value to get the next arrival time.
	    return(nxtInterArr+model.getClock());
	}

	/**
	 * Returns time to load a pallet and mould.
	 * @return NORMAL(MEAN, STDDEV), MEAN: 1.9; STDDEV: 0.19
	 */
	public Normal uLoadTv(){
		
		return new Normal (1.9, 0.19, new MersenneTwister()); // not sure about it.. 
	}
	/**
	public int uTimeUntilFailure(AutoNode OP){
		NEGEXPO(OP);
		if(OP = OP20)  MEAN = 30
		else if (OP = OP30)  MEAN = 450
		else if(OP = OP50) MEAN = 370
		else if (OP = TEST) MEAN = 250
		
	}
	
	I had problem understading.. how do Autonode get hold of OP.. there should be a local copy of it.. I will come back to it.. 
	*/
	/**
	 * 
	 * @return Returns time to rework a TV.
	 */
	public int uReworkTime(){
		/**
		 * NEGEXPO(MEAN), Where MEAN = 35
		 */
		return 35;
	}
	/**
	 * Returns the time to unload a completed TV from a pallet and send it to packaging.
	 * @return NORMAL(MEAN, STDDEV), MEAN: 1.9; STDDEV: 0.19 */
	public Normal uUnLoadTv(){
		
		return new Normal (1.9, 0.19, new MersenneTwister()); // not sure about it.. 
	}
	/**
	 * Returns time to repair the equipment at OP20.
	 * @return TRIANGLE(MIN, MODE, MAX), Where MIN = 5, MODE = 25, MAX = 60	 */
	public int uOP20RepairTime(){
		return -1;
	}
	/**
	 * Returns time to repair the equipment at OP30.
	 * @return ERLANG(MEAN, k), Where MEAN = 35, k = 3 	 */
	public int uO30RepairTime(){
		return -1;
	}
	/**
	 * Returns time to repair the equipment at OP50.
	 * @return TRIANGLE(MIN, MODE, MAX), Where MIN = 10, MODE = 30, MAX = 80 */
	public double uOP50RepairTime(){
		return localTriangularVariate.next(); // its in absmodj... 
	}
	/**
	 * 
	 */
	public void uTESTRepairTime(){
		
	}
	/**
	 * 
	 * @param node
	 * @return Returns time to setup 
	 */
	public int SetupProcedTime(TvType node){
		return -1;
	}

	

	public double uTimeUntilFailure(PanoramaTV local) {
		// TODO Auto-generated method stub
		return -0.0;
	}

	public double uSetupProcedTime(int autoNodeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
