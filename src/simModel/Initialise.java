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
	
	@Override
	protected double timeSequence() 
	{
		return ts[tsix++];  // only invoked at t=0
	}

	@Override
	protected void actionEvent() 
	{
		// System Initialisation
                // Add initilisation instructions 
		
		for(int i = 0; i < model.autoNodes.length; i++)
		{
			model.autoNodes[i].setBusy(false);
			model.autoNodes[i].setLastTVType(TvType.Small);
			model.autoNodes[i].processTime = 0.0;
			model.autoNodes[i].setTimeUntilFailure(model.rvp.uTimeUntilFailure(i));
			
		}
		for(int i = 0; i < model.manualNodes.length; i++)
		{
			model.manualNodes[i].setBusy(false);
		}
		for(int i = 0; i < model.pallets.length; i++)
		{
			model.pallets[i].setTVtype(TvType.None);
			model.pallets[i].moveRework(false);
			model.pallets[i].finishedProcessing = false;
			model.pallets[i].inMotion = false;
		}
		
		model.maintenance.setBusy(false);
		
		model.conveyorSegments[Const.CS_OP10].nextConveyor = Const.CS_OP20;
		model.conveyorSegments[Const.CS_OP20].nextConveyor = Const.CS_OP30;
		model.conveyorSegments[Const.CS_OP30].nextConveyor = Const.CS_OP40A;
		model.conveyorSegments[Const.CS_OP40A].nextConveyor = Const.CS_OP40B;
		model.conveyorSegments[Const.CS_OP40B].nextConveyor = Const.CS_OP40C;
		model.conveyorSegments[Const.CS_OP40C].nextConveyor = Const.CS_OP40D;
		model.conveyorSegments[Const.CS_OP40D].nextConveyor = Const.CS_OP40E;
		model.conveyorSegments[Const.CS_OP40E].nextConveyor = Const.CS_TEST;
		model.conveyorSegments[Const.CS_TEST].nextConveyor = Const.CS_OP50;
		model.conveyorSegments[Const.CS_REWORK].nextConveyor = Const.CS_RETEST;
		model.conveyorSegments[Const.CS_RETEST].nextConveyor = Const.CS_TEST;
		model.conveyorSegments[Const.CS_OP50].nextConveyor = Const.CS_OP60;
		model.conveyorSegments[Const.CS_OP60].nextConveyor = Const.CS_OP10;
		
	}
	

}
