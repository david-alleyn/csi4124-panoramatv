package simModel;

import com.sun.javafx.tk.Toolkit.PaintAccessor;
import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;

/**
 * @author: mush
 * @version : 1.0
 * @since : 21 nov
 */
public class ManualProcessing extends ConditionalActivity {

	private PanoramaTV model;
	private static int manualNodeId;

	public ManualProcessing(PanoramaTV panoramaTV) {
		// TODO Auto-generated constructor stub
		model = panoramaTV;
	}

	@Override
	protected double duration() {
		manualNodeId = model.udp.GetManualNodeReadyForProcessing();
		// TODO Auto-generated method stub
		return model.dvp.uManualProcessTime(manualNodeId);
	}

	@Override
	public void startingEvent() {
		manualNodeId = model.udp.GetManualNodeReadyForProcessing();
		model.manualNodes[manualNodeId].setBusy(false);
	}

	/**
	 * Event ManualProcessing
	 * 
	 * @return RC.ManualNode[ID].busy = FALSE
	 */
	@Override
	protected void terminatingEvent() {
		manualNodeId = model.udp.GetManualNodeReadyForProcessing();
		// TODO OP10, OP60 and CS_ID
		int size = model.conveyorSegments[manualNodeId].positions.length;
		
		if (manualNodeId == Const.CS_OP10) {
			
			model.conveyorSegments[Const.CS_OP10].positions[size-1].tvType = model.dvp.uTvType();
		} else if (manualNodeId == Const.CS_OP60) {
			model.conveyorSegments[Const.CS_OP60].positions[size-1].tvType = null;
		}

		model.manualNodes[manualNodeId].setBusy(false);

	}

	private void setManaulNode(PanoramaTV model) {
		manualNodeId = model.udp.GetManualNodeReadyForProcessing();
	}

	/**
	 * manualNodeId â†� UDP.ManualNodesReadyForProcessing() if(manualNodeId !=
	 * NULL) return TRUE else return FALSE;
	 */
	public static boolean precondition(PanoramaTV model) {
		manualNodeId = model.udp.GetManualNodeReadyForProcessing();
		return (manualNodeId != -1);
	}

	public void Duraiton(PanoramaTV model) {
		setManaulNode(model);
	}

	/**
	 * @return
	 */
	public void ManualProcessing(AutoNode ID) {

	}

}