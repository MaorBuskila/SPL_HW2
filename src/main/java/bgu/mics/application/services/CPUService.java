package bgu.mics.application.services;

import bgu.mics.Broadcast;
import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TerminateBroadcast;
import bgu.mics.application.messages.TickBroadCast;
import bgu.mics.application.objects.CPU;
import bgu.mics.application.objects.Cluster;
import bgu.mics.application.objects.DataBatch;

import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

/**
 * CPU service is resp
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private int ticks;
    private CPU cpu;
    private boolean DoneSub=false;


    public CPUService(String name, CPU cpu) {
        super(name);
        this.cpu = cpu;
        ticks = 0;

        // TODO Implement this
    }


    @Override
    protected void initialize() {
        MessageBusImpl.getInstance().register(this);
        subscribeBroadcast(TickBroadCast.class, (TickBroadCast tickBroadCast) -> {
            updateTick(tickBroadCast);
        });
        subscribeBroadcast(TerminateBroadcast.class, (TerminateBroadcast terminateBroadcast) -> {
        //    Cluster.getInstance().addCpuTimeUnitUsed(cpu.getTotalTicks());
            this.terminate();
        });
        DoneSub=true;
        System.out.println( cpu.getName() + " Service Running");
        while(true)
        {
        while (!cpu.checkIfBusy()) {
          //  DataBatch tmpDataBatch = null;
                DataBatch tmpDataBatch=cpu.process();
//            try {
                //TODO : check sync
                   // tmpDataBatch = cpu.getCluster().getUnProcessedQueue(cpu).take();
                    //cpu.process(tmpDataBatch);
                    if(tmpDataBatch!=null)
                    {    cpu.sendToCluster(tmpDataBatch);
                        this.cpu.clearDataBatch(); }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }

    }
    }

    public void updateTick(TickBroadCast t) {
        cpu.updateTick(t.getTick());
        //ticks++;
    }
    public boolean DoneSubscribe()
    {
        return DoneSub;
    }

//
//    protected int getTick() {
//        return ticks;
//    } // for each cpu send tick and add to his time
}
