package simModel;
/**
 * The activity changes the equipment to match the type of TV being worked on.
 * @author mush
 */
import simulationModelling.ConditionalActivity;

public class SetupEquipment extends ConditionalActivity {

    private PanoramaTV model; //This represents the entire system
    private static int autoNodeId;
    private int segmentID;
    private int localcapacity;
    
    public SetupEquipment(PanoramaTV modelTV){
    	model = modelTV;
    }
    public static boolean precondition(PanoramaTV model) {

        autoNodeId = model.udp.GetAutoNodeRequiringRetooling();
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
        localcapacity = model.conveyorSegments[segmentID].getCapacity();
        model.autoNodes[autoNodeId].setBusy(true);

    }

    @Override
    protected void terminatingEvent() {
        // TODO Auto-generated method stub
        model.autoNodes[autoNodeId].lastTVType
                = model.conveyorSegments[segmentID].positions[localcapacity - 1].tvType;
        model.autoNodes[autoNodeId].setBusy(false);
    }

}