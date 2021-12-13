package bgu.mics.application.objects;

import bgu.mics.MicroService;
import bgu.mics.application.services.StudentService;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student extends StudentService {

    public Student(String name,String department, Degree status,int publications,int papersRead) {
        super(name);
        this.department=department;
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

    private int name;
    private String department;
    private Degree status;
    private int publications;
    private int papersRead;



}
