package simModel;

import simulationModelling.ConditionalActivity;
import simulationModelling.ScheduledActivity;

public class SetupEquipment extends ConditionalActivity {

    private PanoramaTV model; //This represents the entire system
    private static int autoNodeId;
    private int segmentID;
    private int localcapacity;

    public static boolean preconditions(PanoramaTV model) {

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
                = model.conveyorSegments[segmentID].get(localcapacity - 1).TvType;
        model.autoNodes[autoNodeId].setBusy(false);
    }

}