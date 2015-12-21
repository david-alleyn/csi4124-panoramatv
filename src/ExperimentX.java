
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
	static boolean traceflag = false;

	
	public static void main(String[] args)
	{
		int numPallets = 20;

		//ORIGINAL CAPACITIES
		//First segment value is ignored. It will be forced to the numPallets quantity
		int [] segmentCapacities = {40,5,5,5,1,1,1,1,5,10,5,5,5};

		//MAX CAPACITIES
		//First segment value is ignored. It will be forced to the numPallets quantity
		//int [] segmentCapacities = {40,15,15,15,1,1,1,1,15,30,15,15,15};

		//CHERRY PICKED CAPACITIES
		//First segment value is ignored. It will be forced to the numPallets quantity
		//int [] segmentCapacities = {40,2,2,2,1,1,1,1,2,4,2,2,2};

		int numRuns = 20;
		int numDays = 90;

		double startTime=0.0;
		double endTime = 1440 * numDays + 1; /* +1 for throwaway day */
		PanoramaTV pTVModel;

		RandomSeedGenerator rsg = new RandomSeedGenerator();

		Seeds [] sds = new Seeds[numRuns];

		int i;
		for (i = 0; i < numRuns; i++){
			sds[i] = new Seeds(rsg);
		}

		// This was for tracking down a FRAMEWORK bug.
		// Given a particular seed value, the last scheduled terminating event exceeded the STOP NOTICE and no eventOccurred() would be called.
		// Conditions: sds[4], using "NORMAL" capacities instead of "MAX". The bug manifests itself on Day 5 @ 90 days
		/*for (i = 0; i < numRuns; i++){
			sds[i] = sds[4];
		}*/

		/* Table Header */
		System.out.println("\t30days: "+numPallets+" pallets"
			+ "\t60days: "+numPallets+" pallets"
			+ "\t90days: "+numPallets+" pallets");

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
			pTVModel = new PanoramaTV(startTime, endTime, numPallets, segmentCapacities, sds[i], traceflag, debug, writeToCSV);

			if (writeToCSV)
				pTVModel.output.out = out;

			try {
				pTVModel.runSimulation();

				int breakpoint = 3;
			}
			catch (Exception e) {
				e.printStackTrace();
			}


			if (writeToCSV)
				pTVModel.output.flushCSVline();

			System.out.println();
		}
		try { out.close(); } catch (Exception e){ }
	}
}
