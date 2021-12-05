package bgu.mics.application.services;

import bgu.mics.Broadcast;
import bgu.mics.MicroService;

/**
 * CPU service is responsible for handling the {@link DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private static int ticks;


    public CPUService(String name) {
        super("Change_This_Name");
        this.ticks = ticks;
        // TODO Implement this
    }

    public static int getTicks() {
        return ticks;
    }
    public void updateTick(Broadcast ticks)
    {
       // this.ticks=ticks.getInt;
    }

    @Override
    protected void initialize() {
        // TODO Implement this

    }

  //  protected int getTick(){
      //  return tick;
  //  } // for each cpu send tick and add to his time
}
