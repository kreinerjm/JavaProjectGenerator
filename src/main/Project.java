package main;



/**
 * Created by Jacob on 1/28/2017.
 */
public class Project extends Container{

    public static String path;
    String projectName;

    public Project(String projectName, String path)
    {
        this.projectName = projectName;
        this.path = path;
    }

    public String getName()
    {
        return projectName;
    }
}
