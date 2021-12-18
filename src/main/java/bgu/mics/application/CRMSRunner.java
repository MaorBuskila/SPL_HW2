package bgu.mics.application;

import bgu.mics.Callback;
import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.Parser;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.*;
import bgu.mics.application.services.*;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.List;


/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class  CRMSRunner {


    public static void main(String[] args) {
            Parser reader = new Parser();
            reader.readInputFile("example_input2.json");  //the input path is starting from the folder of the project!

            /**
             * reading the input file
             */
            Student[] studentArray = reader.getStudents();
            GPU[] gpuArray = reader.getGPUArray();
            CPU[] cpuArray = reader.getCPUArray();
            ConfrenceInformation[] conferenceArray = reader.getConfrenceInformations();
            int TickTime = reader.getTickTime();
            int Duration = reader.getDuration();

            /**
             * instantiating the cluster and initializing it
             */




            /**
             * instantiating the threads empty arrays
             */
            Thread[] studentServices = new Thread[studentArray.length];
            Thread[] CPUServices = new Thread[cpuArray.length];
            Thread[] GPUServices = new Thread[gpuArray.length];
            Thread[] confrencesServices = new Thread[conferenceArray.length];

            /**
             * instantiating the micro - services and register them.
             */

            MicroService timer = new TimeService(TickTime , Duration);
            for (int i=0 ;i< studentServices.length ; i++){
                MicroService tmpservice = new StudentService(studentArray[i].getName() , studentArray[i]);
                studentServices[i] = new Thread(tmpservice);

            }
            for (int i=0 ; i < CPUServices.length; i++){
                MicroService tmpservice =new CPUService("CPU" + i, cpuArray[i]);
                CPUServices[i] = new Thread(tmpservice);
            }
            for (int i=0 ; i < GPUServices.length; i++){
                MicroService tmpservice = new GPUService("GPU" + i, gpuArray[i]);
                GPUServices[i] = new Thread(tmpservice);
            }
            for (int i=0 ; i < confrencesServices.length; i++){
                MicroService tmpservice = new ConferenceService(conferenceArray[i].getName() , conferenceArray[i]);
                confrencesServices[i] = new Thread(tmpservice);
            }

            /**
             * running the micro-services one after another
             */
            Thread clock = new Thread(timer);

        for (Thread studentService : studentServices) {
            studentService.start();
        }
        for (Thread cpuService : CPUServices) {
            cpuService.start();
        }
        for (Thread gpuService : GPUServices) {
            gpuService.start();
        }

//        for (Thread confrencesService : confrencesServices) {
//            confrencesService.start();
//        }
        clock.start();
        }
    }









































//        System.out.println("Hello World!");
//        //Testing fileReader
//        Parser reader = new Parser();
//        //the input path is starting from the folder of the project!
//        reader.readInputFile("example_input.json");
//        Student[] s = reader.getStudents();
//        GPU[] g = reader.getGPUArray();
//        CPU[] c = reader.getCPUArray();
//        List<Model[]> m = reader.getListOfArraysOfModels();
//
//
//        MessageBusImpl msgBus = MessageBusImpl.getInstance();

     //   Cluster cluster = Cluster.getInstance();
//        Student student = s[0];
//        CPU cpu = c[0];
//        GPU gpu = g[0];
//        Model model = m.get(0)[0];
//        TrainModelEvent ev = new TrainModelEvent(model,student.getName());
//        MicroService m1 =  new GPUService("GPU1" , gpu );
    //    cpu.setCluster(cluster);
    //    msgBus.register(student);
      //  msgBus.register(cpu);
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

