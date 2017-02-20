package main;

import java.util.ArrayList;

/**
 * Created by Jacob on 1/27/2017.
 */
public class JavaClass extends JavaObject {

    String path;
    ArrayList<Method> methods = new ArrayList<>();
    ArrayList<Variable> classVariables = new ArrayList<>();
    JavaClass classExtended;
    ArrayList<String> interfacesImpemented = new ArrayList<>();
    String className;
    Package parent;

    public JavaClass(String className, ArrayList<Method> methods, ArrayList<Variable> classVariables) {
        this.methods = methods;
        this.classVariables = classVariables;
        this.className = className;
    }

    public JavaClass(String className) {
        this.className = className;
    }

    public void setClassExtended(JavaClass jc)
    {
        classExtended = jc;
    }

    public JavaClass getClassExtended()
    {
        return classExtended;
    }

    public boolean hasClassExtended()
    {
        if(classExtended == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setPath(String s)
    {
        path = s;
    }

    public String getPath()
    {
        return path;
    }

}
