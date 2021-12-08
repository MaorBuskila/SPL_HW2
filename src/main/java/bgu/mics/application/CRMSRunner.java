package bgu.mics.application;

import bgu.mics.application.objects.Cluster;
import bgu.mics.application.objects.Data;
import bgu.mics.application.objects.GPU;
import bgu.mics.application.objects.Model;

import java.lang.reflect.Type;


/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class  CRMSRunner {


    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            Class<GPU> enumElement = (Class<GPU>) Class.forName("com.bgu.mics.application.objects.enum");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Data data = new Data(, 0  , 10000);
        Model m =new Model(1, data);
        Cluster c = new Cluster();

        GPU gpu = new GPU(,m, c, "Gpu1" );
    }
}
