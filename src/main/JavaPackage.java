package main;


import java.util.ArrayList;

/**
 * Created by Jacob on 1/28/2017.
 */
public class JavaPackage extends JavaObject{

    String path;
    String identifier;

    public JavaPackage(String identifier)
    {
        this.identifier = identifier;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }

}
