package bgu.mics.application.objects;

import bgu.mics.Future;
import bgu.mics.MicroService;
import bgu.mics.application.services.StudentService;

import java.util.Vector;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {

    private String name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;
    private Model[] models;
    private Future<Model> future;

    public String getStatus() {
        if (status ==  Degree.MSc)
                return "MSc";
        else
            return "PhD";
    }


    // Enum representing the Degree the student is studying for.
    enum Degree {
        MSc, PhD
    }

    //Constructor
    public Student(String name, String department, String sStatus, Model[] models) {
        this.name=name;
        this.department = department;
        if (sStatus.equals("MSc"))
            this.status = Degree.MSc;
        if (sStatus.equals("PhD"))
            this.status = Degree.PhD;
        this.publications = 0;
        this.papersRead = 0;
        this.models=models;
    }
    public Model[] getModels()
    {
        return models;
    }
    public String getName()
    {
        return name;
    }


    public Future<Model> getFuture() {
        return future;
    }

    public void setFuture(Future<Model> future) {
        this.future = future;
    }
}
