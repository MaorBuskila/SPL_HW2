package bgu.mics.application;

import bgu.mics.Callback;
import bgu.mics.MessageBusImpl;
import bgu.mics.MicroService;
import bgu.mics.Parser;
import bgu.mics.application.messages.TrainModelEvent;
import bgu.mics.application.objects.*;
import bgu.mics.application.services.GPUService;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.io.File;
import java.io.FileWriter;
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

        /*
        Outputs ::
         */


        File output = new File("output.txt");//
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(output);

            //writing the students into the output output
            myWriter.write("{\n\t\"students\": [");
            for (int i = 0; i < reader.getStudents().length; i++) {
                myWriter.write("\n\t\t{\n\t\t");
                myWriter.write(reader.getStudents().get(i).toString());
                myWriter.write("\n\t\t");
                if ( i < reader.getStudents().size() - 1 )
                    myWriter.write(",");
                myWriter.write("\n");
            }
            myWriter.write("\n\t],\n");

            //writing the conferences into the output output
            myWriter.write("\t\"conferences\": [\n");
            for (int i = 0; i < reader.getConfrenceInformations().length(); i++){
                myWriter.write("\t{\n\t\t");
                myWriter.write(reader.getConfrenceInformations().get(i).toString());
                myWriter.write("\n\t}");
                if ( i < reader.getConfrenceInformations().size() - 1 )
                    myWriter.write(",");
                myWriter.write("\n");
            }
            myWriter.write("\t],\n");

            //writing the entire data
            myWriter.write("\t\"cpuTimeUsed\": ");
            myWriter.write(Integer.toString(cpuTimeUsed));
            myWriter.write(",\n");

            myWriter.write("\t\"gpuTimeUsed\": ");
            myWriter.write(Integer.toString(gpuTimeUsed));
            myWriter.write(",\n");

            myWriter.write("\t\"batchesProcessed\": ");
            myWriter.write(Integer.toString(batchesProcessed));
            myWriter.write(",\n");

            myWriter.write("}");

            myWriter.flush();
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
}
