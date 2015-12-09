package simModel;
/**
 * @author mush
 */
import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.Distributions;

import dataModelling.*;


class RVPs extends java.lang.Object
{
	private PanoramaTV model; // for accessing the clock
	private Seeds localSeeds;
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds
	TriangularVariate localTriangularVariate;
	Const localConstantClass;
	private BimodalEmpDist localbinomialEmpDist = new BimodalEmpDist(this.histogram);
	private double[] histogram = {0, 10, 20, 30, 40, 50, 60, 70};
	private Exponential Mean20 = new Exponential( 20, new MersenneTwister() );
	private Exponential Mean30 = new Exponential( 450, new MersenneTwister() );
	private Exponential Mean50 = new Exponential( 370, new MersenneTwister() );
	private Exponential MeanTEST = new Exponential( 250, new MersenneTwister() );
	private Normal localNorm = new Normal (1.9, 0.19, new MersenneTwister());
	
	
	
	
	public RVPs(PanoramaTV panoramaTV, Seeds sd) {
		// TODO Auto-generated constructor stub
		this.model = panoramaTV;
		this.localSeeds = sd;
	}
	/**
	 * Returns time to load a pallet and mould.
	 * @return NORMAL(MEAN, STDDEV), MEAN: 1.9; STDDEV: 0.19
	 */
	public double uLoadTv(){
		
		return this.localNorm.nextDouble(); // not sure about it.. 
	}
	
	/**
	 * public int uTimeUntilFailure(AutoNode OP){ NEGEXPO(OP); if(OP = OP20)
	 * MEAN = 30 else if (OP = OP30) MEAN = 450 else if(OP = OP50) MEAN = 370
	 * else if (OP = TEST) MEAN = 250 } I had problem understading.. how do
	 * Autonode get hold of OP.. there should be a local copy of it.. I will
	 * come back to it..
	 */
	/**
	 * 
	 * @return Returns time to rework a TV.
	 */
	public double uReworkTime(){
		/**
		 * NEGEXPO(MEAN), Where MEAN = 35
		 */
		Exponential reworkExpo = new Exponential( 35, new MersenneTwister() );
		return reworkExpo.nextDouble();
	}
	/**
	 * Returns the time to unload a completed TV from a pallet and send it to packaging.
	 * @return NORMAL(MEAN, STDDEV), MEAN: 1.9; STDDEV: 0.19 */
	public double uUnLoadTv(){
		
		return this.localNorm.nextDouble(); // not sure about it.. 
	}
	/**
	 * Returns time to repair the equipment at OP20.
	 * @return TRIANGLE(MIN, MODE, MAX), Where MIN = 5, MODE = 25, MAX = 60	 */
	public double uOP20RepairTime(){
		localTriangularVariate = new TriangularVariate(5, 25, 60, new MersenneTwister());

		return localTriangularVariate.next();
	}
	/**
	 * Returns time to repair the equipment at OP30.
	 * @return ERLANG(MEAN, k), Where MEAN = 35, k = 3 	 */
	public double uO30RepairTime(){
		
		return  Distributions.nextErlang(35, 3, new MersenneTwister());
	}
	/**
	 * Returns time to repair the equipment at OP50.
	 * @return TRIANGLE(MIN, MODE, MAX), Where MIN = 10, MODE = 30, MAX = 80 */
	public double uOP50RepairTime(){
		localTriangularVariate = new TriangularVariate(10, 30, 10, new MersenneTwister());
		return localTriangularVariate.next(); 
	}
	/**
	 * 
	 * @return Returns time to repair the equipment at OPTEST.
	 */
	public double uTESTRepairTime(){
		
		
		return localbinomialEmpDist.getNext();
	}
	/**
	 * 
	 * @param node
	 * @return Returns time to setup 
	 */
	public int SetupProcedTime(TvType node){
		return -1;
	}

	
	/**
	 * 
	 * @param local <-- panaroma tv
	 * @return  Returns time until failure of specified automatic node.
	 */
	public double uTimeUntilFailure(int OperationNode) {
		// TODO Auto-generated method stub
		
	
		if (OperationNode == Const.OP20)
			return Mean20.nextDouble();
		else if (OperationNode == Const.OP30)
			return Mean30.nextDouble();
		else if (OperationNode == Const.OP50)
			return Mean50.nextDouble();
		else if (OperationNode == Const.TEST)
			return MeanTEST.nextDouble();
		return -1;
	}

	public double uSetupProcedTime(int OperationNode) {
		// TODO Auto-generated method stub
		if (OperationNode == Const.OP20)
			return new Normal (5, 0.5, new MersenneTwister()).nextDouble();		
		else if (OperationNode == Const.OP30)
			return new Normal (5, 0.5, new MersenneTwister()).nextDouble();
		else if (OperationNode == Const.OP50)
			return new Normal (5, 0.5, new MersenneTwister()).nextDouble();
		else if (OperationNode == Const.TEST)
			return new Normal (3, 0.3, new MersenneTwister()).nextDouble();
		return -1;
	}

	
}
