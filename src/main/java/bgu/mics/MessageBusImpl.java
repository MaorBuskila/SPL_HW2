package bgu.mics;

import java.util.LinkedList;
import java.util.Queue;
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
// adir elda
	//You are allowed to add the getInstance public method to the MessageBus impl in order to create the thread safe singleton.
	private static MessageBusImpl msgBus = null;
	public static MessageBusImpl getInstance() {
		if (msgBus == null) {
			msgBus = new MessageBusImpl();
		}

		return msgBus;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {


		Queue<Message> queue=new ConcurrentLinkedQueue<>();
		queueMap.put(m,queue);
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(MicroService m) {
		queueMap.remove(m);
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		synchronized(this.queueMap.get(m)){
			while (queueMap.get(m).isEmpty()){
				this.queueMap.get(m).wait();
			}
		}
		// TODO Auto-generated method stub
		return queueMap.get(m).poll();
	}


	public Queue<Message> getQueueMap(MicroService m1) {
		//private ConcurrentMap<MicroService,Queue<T>> queueMap;
		return queueMap.get(m1);
	}
}
