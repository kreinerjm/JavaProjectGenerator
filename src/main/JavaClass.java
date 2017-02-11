package main;

import java.util.ArrayList;

/**
 * Created by Jacob on 1/27/2017.
 */
public class JavaClass extends Container
{

    ArrayList<Method> methods = new ArrayList<>();
    ArrayList<Variable> classVariables = new ArrayList<>();
    String className;
    Package parent;

    public JavaClass(String className,ArrayList<Method> methods, ArrayList<Variable> classVariables)
    {
        this.methods = methods;
        this.classVariables = classVariables;
        this.className = className;
    }

    public JavaClass(String className)
    {
        this.className = className;
    }

}
