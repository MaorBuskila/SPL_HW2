package bgu.mics.application.objects;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {

    private int ticksFromService;
    private Vector<Model> successfulModels;
    private String name;
    private int setDate;

    //TODO Need Events Or NOT ??????????
    public ConfrenceInformation(String name, int setDate) {
        this.name = name;
        this.setDate = setDate;
        ticksFromService=0;

    }

    public void addModel(Model model) // TODO Check when to add model
    {
        if(model.isGood())
            successfulModels.addElement(model);
    }

    public Vector<Model> getSuccessfulModels() {
        return successfulModels;
    }

    public void updateTick(int tick) {
        this.ticksFromService = tick;
    }

    public boolean needToPublish()
    {
        return this.setDate==ticksFromService;
    }
}
