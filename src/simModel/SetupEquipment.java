package simModel;
/**
 * The activity changes the equipment to match the type of TV being worked on.
 * @author mush
 */
import simulationModelling.ConditionalActivity;

public class SetupEquipment extends ConditionalActivity {

    private PanoramaTV model; //This represents the entire system
    private int autoNodeId;
    private int segmentID;
    
    public SetupEquipment(PanoramaTV modelTV){
    	model = modelTV;
    }
    public static boolean precondition(PanoramaTV model) {

        int autoNodeId = model.udp.GetAutoNodeRequiringRetooling();
        return (autoNodeId != -1);

    }


    @Override
    protected double duration() {
        // TODO Auto-generated method stub
        return model.rvp.uSetupProcedTime(autoNodeId);
    }

    @Override
    public void startingEvent() {
        // TODO Auto-generated method stub
        autoNodeId = model.udp.GetAutoNodeRequiringRetooling();
        segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
        model.autoNodes[autoNodeId].setBusy(true);
        model.maintenance.busy = true;

    }

    @Override
    protected void terminatingEvent() {
        // TODO Auto-generated method stub
        int headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
        model.autoNodes[autoNodeId].lastTVType
                = model.conveyorSegments[segmentID].positions[headOfSegment].tvType;
        model.autoNodes[autoNodeId].setBusy(false);
        model.maintenance.busy = false;
    }

}