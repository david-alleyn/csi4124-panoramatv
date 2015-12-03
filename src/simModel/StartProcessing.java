package simModel;

import com.sun.javafx.tk.Toolkit.PaintAccessor;
import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import simulationModelling.ScheduledActivity;

public class StartProcessing extends ScheduledActivity {
    
    
    private PanoramaTV model; //This represents the entire system
	private static int autoNodeId;
         private double timeUntilFailure;
         private double processTime;
         
                 public static boolean preconditions(PanoramaTV model){
            
//            autoNodeId = model.udp.GetAutoNodeForPartialProcessing();
//            return (autoNodeId != -1);
                     return false;
        }
   
	@Override
	protected double timeSequence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double duration() {
            
        return timeUntilFailure; //This is just returning the time until auto node segment fails
	
	}

	@Override
	public void startingEvent() {
		
//            autoNodeId = model.udp.GetAutoNodeForPartialProcessing();
//            processTime = DVPs.uAutomaticProcessTime() - timeUntilFailure;
//            model.AutoNode[autoNodeId].setBusy(true);
	}

	@Override
	protected void terminatingEvent() {
		// TODO Auto-generated method stub
            timeUntilFailure = 0; //
	}

}
