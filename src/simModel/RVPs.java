package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

class RVPs 
{
	PanoramaTV model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds


	// Constructor
	protected RVPs(PanoramaTV model, Seeds sd) 
	{ 
		this.model = model; 
		// Set up distribution functions
		interArrDist = new Exponential(1.0/WMEAN1,  
				                       new MersenneTwister(sd.seed1));
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
}
