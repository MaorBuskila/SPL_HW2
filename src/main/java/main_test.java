import bgu.mics.*;
import bgu.mics.application.objects.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class main_test {
    public static void main(String []args) throws InterruptedException {
        //******************************************************************
         ExecutorService ex = Executors.newFixedThreadPool(4);
         Callable<Integer>[] vec = new Callable[7];
         Future<Integer>[] futures = new Future[7];
         for (int i=1 ; i <= 6 ; i++) {
         int finalI = i;
         vec[i] = () ->{
         System.out.println
         ("this is job number "+ finalI +", "+Thread.currentThread().getName()+" runs me");
         return finalI;
         };
         }
         for (int i=1 ; i <= 6 ; i++) {
         try{
         futures[i]= (Future<Integer>) ex.submit(vec[i]);
         }catch (Exception e){
         System.out.println(e);
         }
         }
         ex.shutdown();
       // *************************** Future test ***************************************
//
//        Future<Integer> future = new Future<>();
//        Thread t1 = new Thread(()->{
//            System.out.println(Thread.currentThread().getName()+" is going to sleep...");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            future.resolve(1);
//        });
//        Thread t3 = new Thread(()-> {
//            System.out.println(Thread.currentThread().getName()+" is working...");
//            System.out.println("before get: "+future.isDone());
//            future.get(3, TimeUnit.SECONDS);
////                future.get();
//            System.out.println("after get: "+future.isDone());
//
//        });
//        t1.start();
//        t3.start();
//        //*************************** TrainModelEvent Test ***************************************
//        MessageBusImpl msgbus = MessageBusImpl.getInstance();
//        Cluster cluster = Cluster.getInstance();
//        Data data = new Data("Images",20000);
//        Student student = new Student("1", "bgu" , "PhD");
//        GPU gpu = new GPU("RTX3090");
//        CPU cpu = new CPU(32);
//        msgbus.register(gpu);
//        msgbus.register(cpu);
//        Model model = new Model("name", data,student);
       // Event<Model> trainModelEvent = new TrainModelEvent(model,"maor");


//******************************************************************
//        Callable<Integer> call = ()->
//                                    {
//                                        System.out.println(1);
//                                        return 1;};
//        Runnable run = ()->{};
//        Thread thread = new Thread((Runnable) call);
////        Future<Integer> future =
    }
}