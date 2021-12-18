package bgu.mics;

import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
    //queue of messages for every micro service
    private ConcurrentHashMap<MicroService, PriorityBlockingQueue<Message>> queueMap;
    //queue of MicroServices subscribed to a message type for every message type
    private ConcurrentHashMap<Class<? extends Event<?>>, Vector<MicroService>> subscribedEvents;
    private ConcurrentHashMap<Class<? extends Broadcast>, Vector<MicroService>> subscribedBroadcast;
    private ConcurrentHashMap<Event<?>, Future<?>> eventsToFuture;


    //You are allowed to add the getInstance public method to the MessageBus impl in order to create the thread safe singleton.


    public MessageBusImpl() {
        this.queueMap = new ConcurrentHashMap<>();
        this.subscribedEvents = new ConcurrentHashMap<>();
        this.subscribedBroadcast = new ConcurrentHashMap<>();
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
        System.out.println(m.getName() + " is subscribing to "  + type);
        if (subscribedEvents.containsKey(type))
            subscribedEvents.get(type).addElement((m));
        else {
            Vector<MicroService> tmpV = new Vector<>();
            tmpV.add(m);
            subscribedEvents.put(type, tmpV);

        }

        // TODO check synchornized

    }

    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        System.out.println(m.getName() + " is subscribing to "  + type.getSimpleName());
        if (subscribedBroadcast.containsKey(type))
            subscribedBroadcast.get(type).addElement((m));
        else {

            Vector<MicroService> tmpV = new Vector<>();
            tmpV.add(m);
            subscribedBroadcast.put(type, tmpV);

        }
        System.out.println("debug subscribeBroadcast" );
    }

    @Override
    public <T> void complete(Event<T> e, T result) {
        Future f = eventsToFuture.get(e);
        f.resolve(result);
        //eventsToFuture.remove(e);
    }

    @Override
    public void sendBroadcast(Broadcast b) {
        Vector<MicroService> v = subscribedBroadcast.get(b.getClass());
            if (subscribedBroadcast.containsKey(b.getClass())) {
                for (int i = 0; i < subscribedBroadcast.get(b.getClass()).size(); i++) {
                    queueMap.get(subscribedBroadcast.get(b.getClass()).get(i)).put(b);
                }
        }
    }

    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
            if (!subscribedEvents.containsKey(e.getClass())) {
                System.out.println("No one register for TrainModel yet!");
                return null;
            } else {
                System.out.println("someone register for TrainModel!");
                Future<T> future = new Future<>();
                eventsToFuture.put(e, future);
                //TODO: check if we need to change the order
                //the roundrubin work for all MicroSerivec
                MicroService m = subscribedEvents.get(e.getClass()).firstElement();
                queueMap.get(m).put(e);
                subscribedEvents.get(e.getClass()).addElement(m);
                subscribedEvents.get(e.getClass()).remove(0);

                return future;
            }
    }

    @Override
    public synchronized void register(MicroService m) {
        PriorityBlockingQueue<Message> queue = new PriorityBlockingQueue<>(10, new MessageComparatorByPriority());
        queueMap.put(m, queue);
        System.out.println(m.getName() + " Register");
    }

    @Override
    public void unregister(MicroService m) {
      //  queueMap.remove(m);
    }

    @Override
    public Message awaitMessage(MicroService m) throws InterruptedException {
       // System.out.println("debug");
        if(queueMap.containsKey(m))
            return queueMap.get(m).take();
        else
            throw new NullPointerException(m.getName() + " is not registered");

    }


    public Queue<Message> getQueueMap(MicroService m1) {
        //private ConcurrentMap<MicroService,Queue<T>> queueMap;
        return queueMap.get(m1);
    }

}
