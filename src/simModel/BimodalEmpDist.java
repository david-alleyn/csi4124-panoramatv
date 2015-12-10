package simModel;
/**
 * @author mush
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import cern.jet.random.Empirical;
import cern.jet.random.engine.MersenneTwister;

/**
 *
 * @author ODUNADE SEGUN
 */
public class BimodalEmpDist {
	double[] histogram;
	double scaleFactor;
	double xMax;
	Empirical empDM;
	
	public BimodalEmpDist(double[] histogram, double scaleFactor, double xMax)
	{
		this.histogram = histogram;
		this.scaleFactor = scaleFactor; // Width of the histogram
		this.xMax = xMax; // Maximum data value
		
		// Create Empirical Object
		empDM = new Empirical(histogram, Empirical.LINEAR_INTERPOLATION, new MersenneTwister());
	}
	public BimodalEmpDist(double[] histogram)
	{
		this.histogram = histogram;
		this.scaleFactor = 10; // Width of the histogram
		this.xMax = 70; // Maximum data value
		
		// Create Empirical Object
		empDM = new Empirical(histogram, Empirical.LINEAR_INTERPOLATION, new MersenneTwister());
	}
	public double getNext()
	{
		return xMax * empDM.nextDouble();
	}

	// Used for testing the Bimodal Empirical Distribution
    public static void main(String[] args) {
        double randomValue;
        double[] histogram = {
           10, 25, 20, 7, 5, 17, 15
        };
        double scaleFactor = 10.0; // Width of the histogram bin
        double xMax = 70.0; // maximum data value
// Create Empirical Object
        Empirical empDM = new Empirical(histogram, Empirical.LINEAR_INTERPOLATION, new MersenneTwister());
// Lets get defining points on the CDF from empDM
        for (int i = 0; i <= histogram.length; i++) {
            System.out.println(i + ", " + (i * scaleFactor) + ", " + empDM.cdf(i));
        }
// Get empDM to generate 10 random numbers
        for (int i = 0; i < 20; i++) {
            randomValue = xMax * empDM.nextDouble();
            System.out.println(randomValue);
        }
    }
}
