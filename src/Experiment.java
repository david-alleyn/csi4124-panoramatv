//// File: Experiment.java
//// Description:
//
//import java.util.*;
//import simModel.*;
//import cern.jet.random.engine.*;
//
//
///** Main Method: Experiments
// *  @author mush
// */
//class Experiment
//{
//	/**
//	 * @param args
//	 */
//   public static void main(String[] args)
//   {
//	   
//	   //David's change
//       int i, NUMRUNS = 30; 
//       double startTime=0.0, endTime=660.0;
//       Seeds[] sds = new Seeds[NUMRUNS];
//       PanoramaTV mname;  // Simulation object
//
//       // Lets get a set of uncorrelated seeds
//       RandomSeedGenerator rsg = new RandomSeedGenerator();
//       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
//       
//       //Test Change
//       
//       // Loop for NUMRUN simulation runs for each case
//       // Case 1
//       System.out.println(" Case 1");
//       for(i=0 ; i < NUMRUNS ; i++)
//       {
//          mname = new PanoramaTV(startTime,endTime,sds[i]);
//          mname.runSimulation();
//          // See examples for hints on collecting output
//          // and developping code for analysis
//       }
//   }
//}
