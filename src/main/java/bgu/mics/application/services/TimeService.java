package bgu.mics.application.services;

import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.application.messages.TerminateBroadcast;
import bgu.mics.application.messages.TickBroadCast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using .
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private int time;
	private int speed;
	private int duration;
	private TimerTask timerTask;
	private java.util.Timer timer;


	public TimeService( int speed, int duration) {
		super("TimeService");
		time = 0;
		this.speed=speed;
		this.duration=duration;
		timer = new Timer();
	}
	@Override
	protected void initialize() {
		MessageBusImpl.getInstance().register(this);
//		timerTask = new TimerTask() {
//			@Override
//			public void run() {
//				time++;
//				System.out.println("Current Time: "+time);
//				sendBroadcast(new TickBroadCast(time));
//			}
//		};
//		timer.scheduleAtFixedRate(timerTask , 0 , speed);
//
//		try{
//			Thread.sleep(duration-50);}
//		catch(Exception e){
//			System.out.println("TimerException");
//		}
//
//		timer.cancel();
//		//sendBroadcast(new TerminateBroadcast());
//		System.out.println("Terminate!");
//		terminate();
//
//	}
//
//}
		while(time < duration) {
			time += speed;
			System.out.println("Time is: " + time/speed);
			MessageBusImpl.getInstance().sendBroadcast(new TickBroadCast(time));
			try {
				Thread.sleep(speed);
			}
			catch (InterruptedException e) {
			}
		}
		MessageBusImpl.getInstance().sendBroadcast(new TerminateBroadcast());
		System.out.println("Terminate!");
		terminate();
	}

}