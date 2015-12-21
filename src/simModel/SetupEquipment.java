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
    private int headOfSegment;
    private int palletID;
    
    public SetupEquipment(PanoramaTV modelTV){
    	model = modelTV;
    }
    public static boolean precondition(PanoramaTV model) {

        int autoNodeId = model.udp.GetAutoNodeRequiringRetooling();
        return (autoNodeId != -1);

    }

    @Override
    public void startingEvent() {
        autoNodeId = model.udp.GetAutoNodeRequiringRetooling();
        segmentID = model.udp.GetAssociatedSegmentID(autoNodeId, true);
        headOfSegment = model.conveyorSegments[segmentID].getCapacity() - 1;
        palletID = model.conveyorSegments[segmentID].palletPositions[headOfSegment];

        model.autoNodes[autoNodeId].busy = true;
        model.maintenance.busy = true;

    }

    @Override
    protected double duration() {
        return model.rvp.uSetupProcedTime(autoNodeId);
    }


    @Override
    protected void terminatingEvent() {

        model.autoNodes[autoNodeId].lastTVType = model.pallets[palletID].tvType;
        model.autoNodes[autoNodeId].busy = false;
        model.maintenance.busy = false;
    }

}