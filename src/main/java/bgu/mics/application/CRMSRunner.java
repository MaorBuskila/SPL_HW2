package bgu.mics.application;

import bgu.mics.Callback;
import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.Parser;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.*;
import bgu.mics.application.services.GPUService;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.List;


/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class  CRMSRunner {


    public static void main(String[] args) {
        System.out.println("Hello World!");
        //Testing fileReader
        Parser reader = new Parser();
        //the input path is starting from the folder of the project!
        reader.readInputFile("example_input.json");
        Student[] s = reader.getStudents();
        GPU[] g = reader.getGPUArray();
        CPU[] c = reader.getCPUArray();
        List<Model[]> m = reader.getListOfArraysOfModels();


        MessageBusImpl msgBus = MessageBusImpl.getInstance();

     //   Cluster cluster = Cluster.getInstance();
        Student student = s[0];
        CPU cpu = c[0];
        GPU gpu = g[0];
        Model model = m.get(0)[0];
        TrainModelEvent ev = new TrainModelEvent(model,student.getName());
        MicroService m1 =  new GPUService("GPU1" , gpu );
    //    cpu.setCluster(cluster);
        msgBus.register(student);
        msgBus.register(cpu);
//        Callback<TrainModelEvent> callback = new Callback<TrainModelEvent>() {
//            @Override
//            public void call(TrainModelEvent c) {
//            msgBus.sendEvent(ev);
//            }
//        };

       // cpu.subscribeEvent(TrainModelEvent.class,callback);




        //Test
//        for(GPU x : g){
//            System.out.println(x.getType());
//        }
//        for(CPU y : c){
//            System.out.println(y.getNumberOfCores());
//        }
//        for(Student z : s){
//            System.out.println(z.getName());
//        }
//        for(Model z1[] : m){
//            for (Model z2 : z1){
//                System.out.println(z2.getData().getSize());
//            }
//        }
    }
}
