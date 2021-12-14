package bgu.mics.application.services;

import bgu.mics.Broadcast;
import bgu.mics.MicroService;
import bgu.mics.application.objects.CPU;

import java.util.Iterator;
import java.util.Vector;

/**
 * CPU service is responsible for handling the {@link DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    protected static int ticks;
    private CPU cpu;


    public CPUService(String name, CPU cpu) {
        super(name);
        this.cpu = cpu;
        //this.ticks = 0;

        // TODO Implement this
    }


    @Override
    protected void initialize() {
        while (!cpu.checkIfBusy()) {
            cpu.sendToCluster(cpu.process(cpu.getUnprocessed()));
        }
    }
//    public void updateTick(Broadcast ticks)
//    {
//        // this.ticks=ticks.getInt;
//    }
    //  protected int getTick(){
    //  return tick;
    //  } // for each cpu send tick and add to his time
}
