package main;


import java.util.ArrayList;

/**
 * Created by Jacob on 1/28/2017.
 */
public class JavaPackage extends Container{
    String identifier;
    JavaPackage parent;

    public JavaPackage(String identifier)
    {
        this.identifier = identifier;
    }

    public ArrayList<Container> getContainers()
    {
        return containers;
    }

}
