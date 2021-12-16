package bgu.mics.application.objects;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {

    private int ticksFromService;
    private Vector<String> successfulEvents;
    private String name;
    private int date;

    public ConfrenceInformation(String name, int date) {
        this.name = name;
        this.date = date;
        ticksFromService=0;
    }
    public void updateTick(int tick) {
        this.ticksFromService = tick;
    }
}
