package simModel;

import simulationModelling.ScheduledActivity;

public class SetupEquipment extends ScheduledActivity {

    private PanoramaTV model; //This represents the entire system
    private static int autoNodeId;
    private int segmentID;

    public static boolean preconditions(PanoramaTV model) {

        autoNodeId = model.udp.GetAutoNodeRequiringRetooling();
        return (autoNodeId != -1);

    }

    @Override
    protected double timeSequence() {
        // TODO Auto-generated method stub
        return 0;
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
        segmentID = model.udp.GetAssociatedSegmentID(autoNodeId);
        int capacity = model.ConveyorSeg[segmentID].capacity;
        model.AutoNodeArray[autoNodeId].setBusy(true);

    }

    @Override
    protected void terminatingEvent() {
        // TODO Auto-generated method stub
        model.AutoNodeArray[autoNodeId].lastTVType
                = model.ConveyorSeg[segmentID].positions[capacity - 1].tvType;
        model.AutoNodeArray[autoNodeId].setBusy(false);
    }

}
