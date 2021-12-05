package bgu.mics;
import bgu.mics.application.objects.DataBatch;
import bgu.mics.application.objects.GPU;
import bgu.mics.application.services.CPUService;
import bgu.mics.application.services.GPUService;
import bgu.mics.example.messages.ExampleBroadcast;
import bgu.mics.example.messages.ExampleEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

        private MessageBusImpl msgbus;
        private MicroService m1;
        private MicroService m2;


        @BeforeEach
        public void setUp() {
            msgbus = MessageBusImpl.getInstance();
            m1 = new CPUService( "CPU");
            m2 = new GPUService("GPU");
            msgbus.register(m1);
            msgbus.register(m2);
        }

        @After
        public void tearDown() {
            msgbus.unregister(m1);
            msgbus.unregister(m2);



        }


    @Test
    void subscribeEvent() {
            ExampleEvent exampleEvent = new ExampleEvent("0");
            m1.subscribeEvent(ExampleEvent.class, message -> {});
            msgbus.sendEvent(exampleEvent);
            try{
                ExampleEvent exp1 = (ExampleEvent) msgbus.awaitMessage(m1);
                assertEquals(exp1, exampleEvent);

            }
            catch (Exception w){
                System.out.println("problem in awaitMessage from testsubscribeEvent");
            }
    }

    @Test
    void testsubscribeBroadcast() {
        ExampleBroadcast exampleBroadcast = new ExampleBroadcast("0");
        m1.subscribeBroadcast(ExampleBroadcast.class, exmBroad -> {});
        msgbus.sendBroadcast(exampleBroadcast);
        try {
            ExampleBroadcast exp1 = (ExampleBroadcast) msgbus.awaitMessage(m1);
            assertEquals(exp1, exampleBroadcast);
        } catch (Exception w) {
            System.out.println("problem in awaitMessage from testsubscribeEvent");
        }
    }

    @Test
    void complete() {
        ExampleEvent exampleEvent = new ExampleEvent("exm");
        msgbus.subscribeEvent(ExampleEvent.class , m1);
        Future<String> f = m1.sendEvent(exampleEvent);
        m1.complete(exampleEvent,"Complete");
        assertEquals(f.get(), "Complete");

    }

    @Test
    void sendBroadcast() {
        ExampleBroadcast exampleBroadcast = new ExampleBroadcast("0");
        m1.subscribeBroadcast(ExampleBroadcast.class, exmBroad -> {});
        m2.subscribeBroadcast(ExampleBroadcast.class, exmBroad -> {});
        msgbus.sendBroadcast(exampleBroadcast);
        try {
            ExampleBroadcast exp1 = (ExampleBroadcast) msgbus.awaitMessage(m1);
            ExampleBroadcast exp2 = (ExampleBroadcast) msgbus.awaitMessage(m2);
            assertEquals(exp1, exampleBroadcast);
            assertEquals(exp2, exampleBroadcast);
        } catch (Exception w) {
            System.out.println("problem in awaitMessage from testsubscribeEvent");
        }
    }


    @Test
    void sendEvent() {
        ExampleEvent exampleEvent = new ExampleEvent("exm");
        m1.subscribeEvent(ExampleEvent.class, message -> {});
        msgbus.sendEvent(exampleEvent);
        try{
            ExampleEvent exp1 = (ExampleEvent) msgbus.awaitMessage(m1);
            assertEquals(exp1, exampleEvent);

        }
        catch (Exception w){
            System.out.println("problem in awaitMessage from testsubscribeEvent");
        }

    }

    @Test
    void register() {
            assertNotNull(msgbus.getQueueMap(m1));
    }

    @Test
    void unregister() {
        assertNotNull(msgbus.getQueueMap(m1));
        msgbus.unregister(m1);
        assertNull(msgbus.getQueueMap(m1));
    }

    @Test
    void awaitMessage()  {

        ExampleEvent exampleEvent = new ExampleEvent("exm");
        m1.subscribeEvent(ExampleEvent.class, message -> {});
        msgbus.sendEvent(exampleEvent);
        Message mess= null;
        try {
            mess = msgbus.awaitMessage(m1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotNull(mess);

    }

}