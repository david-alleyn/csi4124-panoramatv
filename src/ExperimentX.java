
// File: Experiment.java
// Description:

import cern.jet.random.engine.*;
import simModel.PanoramaTV;
import simModel.Seeds;

import java.io.*;

// Main Method: Experiments
public class ExperimentX
{
	static boolean debug = false;
	static boolean writeToCSV = false;

	
	public static void main(String[] args)
	{
		int numPallets = 40;
		int [] segmentCapacities = {5,5,5,5,5,5,5,5,5,5,5,5,5};
		int numRuns = 400;
		int numDays = 90;

		double startTime=0.0;
		double endTime = 1440 * numDays + 1; /* +1 for throwaway day */
		PanoramaTV mname;

		RandomSeedGenerator rsg = new RandomSeedGenerator();
		Seeds [] sds = new Seeds[numRuns];

		int i;
		for (i = 0; i < numRuns; i++){
			sds[i] = new Seeds(rsg);
				/*rsg.nextSeed(),
				rsg.nextSeed(),
				rsg.nextSeed(),
				rsg.nextSeed(),
				rsg.nextSeed(),
				rsg.nextSeed(),
				rsg.nextSeed(),
				rsg.nextSeed());*/
		}

		/* Table Header */
		System.out.println("\t30days:"+numPallets+"pallets"
			+ "\t60days:"+numPallets+"pallets"
			+ "\t90days:"+numPallets+"pallets");

		FileWriter fstream;
		BufferedWriter out = null;
		if (writeToCSV){
			try {
				fstream = new FileWriter(numPallets + "Pallets.txt");
				out = new BufferedWriter(fstream);
			} catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}
		}

		for (i = 0; i < numRuns; i++){
			System.out.print(i+1 + "\t");
			mname = new PanoramaTV(startTime, endTime, numPallets, segmentCapacities, sds[i]);

			if (writeToCSV)
				mname.getOutput().out = out;

			mname.runSimulation();

			if (writeToCSV)
				mname.getOutput().flushCSVline();

			System.out.println();
		}
		try { out.close(); } catch (Exception e){ }
	}
}
