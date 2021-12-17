package bgu.mics.application.services;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TickBroadCast;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private int time;
	private int speed;
	private int duration;


	public TimeService(String name, int speed, int duration) {
		super(name);
		time = 0;
		this.speed=speed;
		this.duration=duration;
	}
	@Override
	protected void initialize() {
		while(time < duration) {
			time++;
			MessageBusImpl.getInstance().sendBroadcast(new TickBroadCast(time));
			try {
				wait(speed);
			} catch (InterruptedException e) {
			}
		}
		MessageBusImpl.getInstance().sendBroadcast(new TerminateBroadcast());
		terminate();
	}

}