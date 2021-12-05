package bgu.mics;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class FutureTest extends TestCase {

    private static Future<String> future;

    @Before
    public void setUp() throws Exception {
        future =new Future<String>();
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testGet() {
        assertFalse(future.isDone());
        future.resolve("test");
        assertTrue(future.isDone());
        assertEquals("test", future.get());
    }
    @Test
    public void testResolve() {
        assertNull(future.get());
        String str = "Somthing";
        future.resolve(str);
        assertEquals(future.get() , str);
    }
    @Test
    public void testIsDone() {
        assertFalse(future.isDone());
        future.resolve("result");
        assertTrue(future.isDone());
    }
    @Test
    public void testGetWithTime() { //Todo: need to change!
        assertNull(future.get(100, TimeUnit.MILLISECONDS));
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(future.isDone());

        assertTrue(future.isDone());
        ;

    }
}