package bgu.mics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private ConcurrentHashMap<MicroService, Queue<Message>> queueMap;
	private ConcurrentHashMap<Class<? extends Event<?>> , Vector<MicroService>> interestedEvents;
	private ConcurrentHashMap<Class<? extends Broadcast> , Vector<MicroService>> interestedBroadcast;
	private ConcurrentHashMap<Event<?>,Future<?>> eventsToFuture;


	//You are allowed to add the getInstance public method to the MessageBus impl in order to create the thread safe singleton.


	public MessageBusImpl() {
		this.queueMap = new ConcurrentHashMap<>();
		this.interestedEvents = new ConcurrentHashMap<>();
		this.interestedBroadcast = new ConcurrentHashMap<>();
		this.eventsToFuture = new ConcurrentHashMap<>();
	}
	private static MessageBusImpl msgBus = null;
	public static MessageBusImpl getInstance() {
		if (msgBus == null) {
			msgBus = new MessageBusImpl();
		}

		return msgBus;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) { // ? is sog shel event kolsheu
		if(interestedEvents.containsKey(type))
			interestedEvents.get(type).addElement((m));
		else {

			Vector<MicroService> tmpV=new Vector<>();
			tmpV.add(m);
			interestedEvents.put(type,tmpV);

		}

		// TODO check synchornized

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if(interestedBroadcast.containsKey(type))
			interestedBroadcast.get(type).addElement((m));
		else {

			Vector<MicroService> tmpV=new Vector<>();
			tmpV.add(m);
			interestedBroadcast.put(type,tmpV);

		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future f = eventsToFuture.get(e);
		f.resolve(result);
		eventsToFuture.remove(e);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		synchronized (interestedBroadcast.get(b.getClass())) {
			if (!interestedBroadcast.get(b.getClass()).isEmpty()) {
				Iterator<MicroService> vi = interestedBroadcast.get(b.getClass()).iterator();
				while (vi.hasNext()) {
					synchronized (queueMap.get(vi)) {

						queueMap.get(vi.next()).add(b);
					}
				}
			}
		}
	}
	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if (interestedEvents.get(e.getClass()).isEmpty())
			return null;
		Future<T> future = new Future<>();
		eventsToFuture.put(e,future);
		synchronized (interestedEvents.get(e.getClass())){
			MicroService m = interestedEvents.get(e.getClass()).firstElement();
			queueMap.get(m).add(e);
			interestedEvents.get(e.getClass()).remove(0);
			interestedEvents.get(e.getClass()).addElement(m);
		}
		return future;
	}

	@Override
	public void register(MicroService m) {
		Queue<Message> queue=new ConcurrentLinkedQueue<>();
		queueMap.put(m,queue);
	}

	@Override
	public void unregister(MicroService m) {
		queueMap.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		synchronized(this.queueMap.get(m)){
			while (queueMap.get(m).isEmpty()){
				this.queueMap.get(m).wait();
			}
		}
		return queueMap.get(m).poll();
	}


	public Queue<Message> getQueueMap(MicroService m1) {
		//private ConcurrentMap<MicroService,Queue<T>> queueMap;
		return queueMap.get(m1);
	}
}
