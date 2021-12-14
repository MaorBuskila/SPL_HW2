package bgu.mics.application.objects;

import bgu.mics.MicroService;
import bgu.mics.application.services.StudentService;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student extends StudentService {

    private int name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;

    public Student(String name,String department, String sStatus ,int publications,int papersRead) {
        super(name);
        this.department=department;
        if (sStatus.equals("MSc"))
            this.status = Degree.MSc;
        if (sStatus.equals("PhD"))
            this.status = Degree.PhD;
        this.status=status;
        this.publications=publications;
        this.papersRead=papersRead;
    }

    /**
     * Enum representing the Degree the student is studying for.
     */
    enum Degree {
        MSc, PhD
    }





}
