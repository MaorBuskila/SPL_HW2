package bgu.mics.application.services;

import bgu.mics.Broadcast;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TickBroadCast;
import bgu.mics.application.objects.CPU;
import bgu.mics.application.objects.DataBatch;

import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

/**
 * CPU service is responsible for handling the {@link DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    protected int ticks;
    private CPU cpu;


    public CPUService(String name, CPU cpu) {
        super(name);
        this.cpu = cpu;
        ticks = 0;

        // TODO Implement this
    }


    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadCast.class , (TickBroadCast tickBroadCast) -> {
            updateTick();
        });
        System.out.println("CPU service running");
        while (!cpu.checkIfBusy()) {
            DataBatch tmpDataBatch = null;

            try {
                //TODO : check sync
                synchronized (this) {
                    Object q = cpu.getCluster().getUnProcessedQueue(cpu);
                    tmpDataBatch = cpu.getCluster().getUnProcessedQueue(cpu).take();
                    cpu.process(tmpDataBatch);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cpu.sendToCluster(cpu.process(tmpDataBatch));
        }

    }

    public void updateTick() {
        cpu.updateTick();
    ticks++;
    }


//    protected int getTick() {
//        return ticks;
//    } // for each cpu send tick and add to his time
}
