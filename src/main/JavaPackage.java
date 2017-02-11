package main;


import java.util.ArrayList;

/**
 * Created by Jacob on 1/28/2017.
 */
public class JavaPackage extends Container{
    ArrayList<Container> containers = new ArrayList<>();
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

    public void addContainer(Container c)
    {
        containers.add(c);
    }

    public void removeContainer(Container c)
    {
        containers.remove(c);
    }
}
