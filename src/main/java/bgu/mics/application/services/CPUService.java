package bgu.mics.application.services;

import bgu.mics.Broadcast;
import bgu.mics.MicroService;
import bgu.mics.application.objects.CPU;

import java.util.Iterator;
import java.util.Vector;

/**
 * CPU service is responsible for handling the {@link DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    protected static int ticks;
    protected Vector<CPU> CPUs;


    public CPUService(String name,Vector<CPU> CPUs) {
        super("Change_This_Name");
        this.ticks = 0;
        this.CPUs = CPUs;
        // TODO Implement this
    }
    public void updateTick(Broadcast ticks)
    {
       // this.ticks=ticks.getInt;
    }

    @Override
    protected void initialize() {
        Iterator<CPU> vi= CPUs.iterator();
        while(vi.hasNext())
        {

        }

    }

  //  protected int getTick(){
      //  return tick;
  //  } // for each cpu send tick and add to his time
}
