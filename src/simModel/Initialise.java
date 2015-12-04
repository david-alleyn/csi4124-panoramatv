package simModel;

import simulationModelling.ScheduledAction;

/**
 * action event...
 *  
 * @author mush
 *
 */
class Initialise extends ScheduledAction
{
	PanoramaTV model;
	
	// Constructor
	protected Initialise(PanoramaTV model) { this.model = model; }

	double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
	int tsix = 0;  // set index to first entry.
	protected double timeSequence() 
	{
		return ts[tsix++];  // only invoked at t=0
	}

	protected void actionEvent() 
	{
		// System Initialisation
                // Add initilisation instructions 
		
		for(int i = 0; i < model.AutoNodeArray.length; i++)
		{
			model.AutoNodeArray[i].setBusy(false);
			model.AutoNodeArray[i].setlastTVtype(TvType.Small);
			model.AutoNodeArray[i].setprocessTime(0.0);
			
		}
		for(int i = 0; i < model.ManualNodes.length; i++)
		{
			model.ManualNodes[i].setBusy(false);
		}
		for(int i = 0; i < model.pallet.length; i++)
		{
			model.pallet[i].setTVtype(TvType.Small);
			model.pallet[i].moveRework(false);
		}
		
		model.maintenance.setBusy(false);
		
		model.ConveyorSeg[Const.OP10_ID].nextConveyor = Const.OP20_ID;
		model.ConveyorSeg[Const.OP20_ID].nextConveyor = Const.OP30_ID;
		model.ConveyorSeg[Const.OP30_ID].nextConveyor = Const.OP40A_ID;
		model.ConveyorSeg[Const.OP40A_ID].nextConveyor = Const.OP40B_ID;
		model.ConveyorSeg[Const.OP40B_ID].nextConveyor = Const.OP40C_ID;
		model.ConveyorSeg[Const.OP40C_ID].nextConveyor = Const.OP40D_ID;
		model.ConveyorSeg[Const.OP40D_ID].nextConveyor = Const.OP40E_ID;
		model.ConveyorSeg[Const.OP40E_ID].nextConveyor = Const.TEST_ID;
		model.ConveyorSeg[Const.TEST_ID].nextConveyor = Const.OP50_ID;
		model.ConveyorSeg[Const.REWORK_ID].nextConveyor = Const.RETEST_ID;
		model.ConveyorSeg[Const.RETEST_ID].nextConveyor = Const.TEST_ID;
		model.ConveyorSeg[Const.OP50_ID].nextConveyor = Const.OP60_ID;
		model.ConveyorSeg[Const.OP60_ID].nextConveyor = Const.OP10_ID;
		
	}
	

}
