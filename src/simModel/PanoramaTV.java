package simModel;
/**
 * @author mush
 */
import java.util.Arrays;
import java.util.PriorityQueue;

import simulationModelling.SBNotice;
import simulationModelling.ScheduledActivity;
import simulationModelling.SequelActivity;
import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
//
// The Simulation model Class
public class PanoramaTV extends AOSimulationModel
{
	
	// Constants available from Constants class
	/* Parameter */
        // Define the parameters

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various 
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialise Action

	/* Input Variables */
	// Define any Independent Input Varaibles here
	
	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to rvp object - object created in constructor
	protected DVPs dvp;  // Reference to dvp object
	protected UDPs udp;
	public Pallet[] pallets;
	public ManualNode [] manualNodes;
	public ConveyorSegment [] conveyorSegments;
	public AutoNode [] autoNodes;
	public Maintenance maintenance;
	// Output object
	public Output output;
	
	public int [] segmentCapacities;
	
	// Output values - define the public methods that return values
	// required for experimentation.


	// Constructor
	public PanoramaTV(double t0time, double tftime, int numPallets, int [] segmentCapacities, Seeds sd)
	{
		// A few fixed things
		int numManualNodes = 8;
		int numAutoNodes = 4;
		int numConveyorSegments = 13;
		
		this.segmentCapacities = segmentCapacities;
		
		// Initialise parameters here
		
		// Create RVP object with given seed
		rvp = new RVPs(this,sd);
		dvp = new DVPs(this);  // Reference to dvp object
		udp = new UDPs(this);
		
		output = new Output(this);
		
		//Initialize pallets
		pallets = new Pallet[numPallets];
		for(int i = 0; i < numPallets; i++)
		{
			pallets[i] = new Pallet();
		}
		
		//Initialize manualNodes
		manualNodes = new ManualNode[numManualNodes];
		for(int i = 0; i < numManualNodes; i++)
		{
			manualNodes[i] = new ManualNode();
		}
		
		//Initialize autoNodes
		autoNodes = new AutoNode[numAutoNodes];
		for(int i = 0; i < numAutoNodes; i++)
		{
			autoNodes[i] = new AutoNode();
		}
		
		//Initialize conveyorSegments
		conveyorSegments = new ConveyorSegment[numConveyorSegments];
		for(int i = 0; i < numConveyorSegments; i++)
		{
			if(i != 0) //NOT Segment OP10
			{
				conveyorSegments[i] = new ConveyorSegment(segmentCapacities[i]);
			}
			else // Segment OP10
			{
				conveyorSegments[i] = new ConveyorSegment(numPallets); //Set conveyor segment to number of pallets
			}
		}
		
		//Install the pallets onto Segment OP10
		for(int i = 0; i < numPallets; i++)
		{
			conveyorSegments[Const.CS_OP10].positions[i] = pallets[i];
			pallets[i].currConveyor = Const.CS_OP10;
			pallets[i].currPosition = i;
		}
		
		//Initialize Maintenance
		
		maintenance = new Maintenance();
		
		
		
		// rgCounter and qCustLine objects created in Initalise Action
		
		// Initialise the simulation model
		initAOSimulModel(t0time,tftime);   

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		scheduleAction(init);  // Should always be first one scheduled.
		// Schedule other scheduled actions and acitvities here
	}

	/************  Implementation of Data Modules***********/	
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		// Check preconditions of Conditional Activities


		while (scanPreconditions() == true) /* repeat */;
		int breakpoint;

	}

	private boolean scanPreconditions()
	{
		boolean statusChanged = false;
		// Check preconditions of Conditional Activities
		if (AutoProcessing.precondition(this)){
			AutoProcessing act = new AutoProcessing(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		if (StartProcessing.precondition(this)){
			StartProcessing act = new StartProcessing(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		if (RepairEquipment.precondition(this)){
			RepairEquipment act = new RepairEquipment(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		if (SetupEquipment.precondition(this)){
			SetupEquipment act = new SetupEquipment(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		if (ManualProcessing.precondition(this)){
			ManualProcessing act = new ManualProcessing(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}

		if (MovePallet.preconditon(this)){
			MovePallet act = new MovePallet(this);
			act.startingEvent();
			scheduleActivity(act);
			statusChanged = true;
		}
		return (statusChanged);
	}

	boolean traceflag = true;
	
	
	int days = 0;
	int nextDay = 1440;
	private boolean writeToCSV = true;
	private boolean debug = true;
	
	public void eventOccured()
	{
		if ((int)getClock() == nextDay){
			if (nextDay == 1440)
				output.numTVAssembled = 0;
			else {
				System.out.print(output.numTVAssembled / days + "\t\t\t");
				if (this.writeToCSV)
					output.printCSV(days);
			}
			days += 30;
			nextDay = days * 1440;
		}

		if (this.debug)
			output.printConveyors(this.getClock());
		
		if(traceflag)
		{
			// Can add other trace/log code to monitor the status of the system
			// See examples for suggestions on setup logging
			this.explicitShowSBL(this.getCopySBL());
		    // PriorityQueue<SBNotice> sbl = this.getCopySBL();
			// explicitShowSBL(sbl);

		}

		// Setup an updateTrjSequences() method in the Output class
		// and call here if you have Trajectory Sets
		// updateTrjSequences() 
	}

	// The following method duplicates the function of the private
	// method showSBL.  Can be used to modify logging of the
	// SBL to filter out some of the events or entries on 
	// the SBL.
	protected void explicitShowSBL(PriorityQueue<SBNotice> sbl)
	{
		int ix;
		SBNotice notice;
		System.out.println("------------SBL----------");
		Object[] sbList = sbl.toArray();
		Arrays.sort(sbList); // Sorts the array
		int movePalletCount = 0;
		for (ix = 0; ix < sbList.length; ix++)
		{
			notice = (SBNotice) sbList[ix];
			if (notice.behaviourInstance != null)
			{
				if(!notice.behaviourInstance.getClass().getName().contains("MovePallet")) {
					System.out.print("TimeStamp:" + notice.timeStamp);
					System.out.print(" Activity/Action: "
							+ notice.behaviourInstance.getClass().getName());

					if(notice.behaviourInstance.name != null) {
						System.out.println("(" + notice.behaviourInstance.name + ")");
					}
					System.out.println();
				}
				else {
					movePalletCount++;
				}


			}
			else System.out.print(" Stop Notification ");
			if(ScheduledActivity.class.isInstance(notice.behaviourInstance))
			{
				ScheduledActivity schAct = (ScheduledActivity) notice.behaviourInstance;
				if(schAct.eventSched == ScheduledActivity.EventScheduled.STARTING_EVENT)
				    System.out.print("   (starting event scheduled)");
				else
				    System.out.print("   (terminating event scheduled)");

				System.out.println();
			}
			//System.out.println();
		}
		System.out.println("There are " + movePalletCount + " MovePallet SBL list entries.");
		System.out.println("----------------------");
	}
	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}	
	public double getClock() {return Math.abs( super.getClock());}

	public void setOutput(Output output) {
		this.output = output;
	}
	
}


