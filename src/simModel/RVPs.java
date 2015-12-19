package simModel;
/**
 * @author mush
 */
import cern.jet.random.Exponential;
import cern.jet.random.Normal;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.Distributions;

import dataModelling.*;


class RVPs
{
    // Data Models - i.e. random veriate generators for distributions
	// are created using Colt classes, define 
	// reference variables here and create the objects in the
	// constructor with seeds

	//OP10 Distributions
	private Normal op10ProcessingTime;

	//OP20 Distributions
	private Exponential op20timeBetweenFailures;
	private TriangularVariate op20RepairTime;
	private Normal op20SetupTime;

	//OP30 Distributions
	private Exponential op30timeBetweenFailures;
	private MersenneTwister op30RNG;
	private Normal op30SetupTime;

	//OP50 Distributions
	private Exponential op50timeBetweenFailures;
	private TriangularVariate op50RepairTime;
	private Normal op50SetupTime;

	//OP60 Distributions
	private Normal op60ProcessingTime;

	//OP TEST Distributions
	private Exponential opTESTtimeBetweenFailures;
	private double[] histogram = {0, 10, 20, 30, 40, 50, 60, 70};
	private BimodalEmpDist opTESTRepairTime = new BimodalEmpDist(this.histogram);
	private Normal opTESTSetupTime;
	private Uniform oneToHundredDist;

	//OP REWORK Distributions
	private Exponential opREWORKProcessingTime;
	
	
	public RVPs(PanoramaTV panoramaTV, Seeds sd) {

		//OP10 Distributions
		op10ProcessingTime = new Normal(1.9, 0.9, new MersenneTwister(sd.seed1));

		//OP20 Distributions
		op20timeBetweenFailures = new Exponential( 1.0/30, new MersenneTwister(sd.seed2) );
		op20RepairTime = new TriangularVariate(5, 25, 60, new MersenneTwister(sd.seed3) );
		op20SetupTime = new Normal(5, 0.5, new MersenneTwister(sd.seed4));

		//OP30 Distributions
		op30timeBetweenFailures = new Exponential( 1.0/450, new MersenneTwister(sd.seed5) );
		op30RNG = new MersenneTwister(sd.seed5);
		op30SetupTime = new Normal(5, 0.5, new MersenneTwister(sd.seed6));

		//OP50 Distributions
		op50timeBetweenFailures = new Exponential( 1.0/370, new MersenneTwister(sd.seed7) );
		op50RepairTime = new TriangularVariate(10,30,80, new MersenneTwister(sd.seed8));
		op50SetupTime = new Normal(5, 0.5, new MersenneTwister(sd.seed9));

		//OP60 Distributions
		op60ProcessingTime = new Normal(1.9,0.19, new MersenneTwister(sd.seed10));

		//OP TEST Distributions
		opTESTtimeBetweenFailures = new Exponential( 1.0/250, new MersenneTwister(sd.seed11) );
		//Emperical Distribution is initialized in the field area above
		opTESTSetupTime = new Normal(3, 0.3, new MersenneTwister(sd.seed12));
		oneToHundredDist = new Uniform(new MersenneTwister(sd.seed13));

		//OP REWORK Distributions
		opREWORKProcessingTime = new Exponential(1.0/35, new MersenneTwister(sd.seed14));


	}
	/**
	 * Returns time to load a pallet and mould.
	 * @return NORMAL(MEAN, STDDEV), MEAN: 1.9; STDDEV: 0.19
	 */
	public double uLoadTv(){
		
		return this.op10ProcessingTime.nextDouble(); // not sure about it..
	}
	
	public boolean uPassTvTesting()
	{
		double randomData = oneToHundredDist.nextDoubleFromTo(1, 100);
		return randomData <= 5;
	}
	
	/**
	 * 
	 * @return Returns time to rework a TV.
	 */
	public double uReworkTime(){
		/**
		 * NEGEXPO(MEAN), Where MEAN = 35
		 */
		return opREWORKProcessingTime.nextDouble();
	}
	/**
	 * Returns the time to unload a completed TV from a pallet and send it to packaging.
	 * @return NORMAL(MEAN, STDDEV), MEAN: 1.9; STDDEV: 0.19 */
	public double uUnLoadTv(){
		
		return op10ProcessingTime.nextDouble(); // not sure about it..
	}
	/**
	 * Returns time to repair the equipment at OP20.
	 * @return TRIANGLE(MIN, MODE, MAX), Where MIN = 5, MODE = 25, MAX = 60	 */
	public double uOP20RepairTime(){

		return op20RepairTime.next();
	}
	/**
	 * Returns time to repair the equipment at OP30.
	 * @return ERLANG(MEAN, k), Where MEAN = 35, k = 3 	 */
	public double uO30RepairTime(){
		
		return  Distributions.nextErlang(35, 3, op30RNG);
	}
	/**
	 * Returns time to repair the equipment at OP50.
	 * @return TRIANGLE(MIN, MODE, MAX), Where MIN = 10, MODE = 30, MAX = 80 */
	public double uOP50RepairTime(){

		return op50RepairTime.next();
	}
	/**
	 * 
	 * @return Returns time to repair the equipment at OPTEST.
	 */
	public double uTESTRepairTime(){

		return opTESTRepairTime.getNext();
	}

	public double uTimeUntilFailure(int OperationNode) {
		if (OperationNode == Const.OP20)
			return op20timeBetweenFailures.nextDouble();
		else if (OperationNode == Const.OP30)
			return op30timeBetweenFailures.nextDouble();
		else if (OperationNode == Const.OP50)
			return op50timeBetweenFailures.nextDouble();
		else if (OperationNode == Const.TEST)
			return opTESTtimeBetweenFailures.nextDouble();
		return -1;
	}

	public double uSetupProcedTime(int OperationNode) {
		// TODO Auto-generated method stub
		if (OperationNode == Const.OP20)
			return op20SetupTime.nextDouble();
		else if (OperationNode == Const.OP30)
			return op30SetupTime.nextDouble();
		else if (OperationNode == Const.OP50)
			return op50SetupTime.nextDouble();
		else if (OperationNode == Const.TEST)
			return opTESTSetupTime.nextDouble();
		return -1;
	}

	/**
	 * If (autoNodeID = OP20) THEN Return RVP.uOP20RepairTime()
	 * Else If (autoNodeID = OP30) THEN Return RVP.uOP30RepairTime()
	 * Else If (autoNodeID = OP50) THEN Return RVP.uOP50RepairTime()
	 * Else If (autoNodeID = TEST) THEN Return RVP.uTESTRepairTime()

	 * @param autoNodeID
	 * @return
	 */
	public double GetNodeRepairTime(int autoNodeID){
		if (autoNodeID == Const.OP20)
			return uOP20RepairTime();
		else if (autoNodeID == Const.OP30)
			return uO30RepairTime();
		else if (autoNodeID == Const.OP50)
			return uOP50RepairTime();
		else if (autoNodeID == Const.TEST)
			return uTESTRepairTime();

		return -1;
	}

	
}
