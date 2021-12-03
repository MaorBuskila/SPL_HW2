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
            m1 = new CPUService("CPU");
            m1 = new GPUService("GPU");
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
            //msgbus.register(m1);
            m1.subscribeEvent(ExampleEvent.class, message -> {});
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
    }

    @Test
    void sendEvent() {
//        ExampleBroadcast exampleBroadcast = new ExampleBroadcast("0");
//        msgbus.register(m1);
//        msgbus.sendEvent(exampleBroadcast);

    }

    @Test
    void register() {
    }

    @Test
    void unregister() {
    }

    @Test
    void awaitMessage() {
    }

    @Test
    void subscribeBroadcast() {
    }
}