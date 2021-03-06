package main;


import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jacob on 1/27/2017.
 */
public class ProjectGenerator
{
    JavaFileGenerator javaFileGenerator = new JavaFileGenerator();

    Project myProject;
    ArrayList<JavaPackage> packages = new ArrayList<>();
    ArrayList<JavaClass> classes = new ArrayList<>();
    Container overview;
    public ProjectGenerator(Container start)
    {
        overview = start;
        parse(start);
        generateProject();
    }

    public void parse(Container c)
    {
        getInfo(c);
        if(c.getContainers().size() == 0 || c.getContains() instanceof Method || c.getContains() instanceof Variable)
        {

        }
        else
        {
            for(Container cc : c.getContainers())
            {
                parse(cc);
            }
        }
        //System.out.println(c.getLabel());
    }

    public void getInfo(Container c)
    {
        JavaObject needsInfo = c.getContains();
        if(needsInfo instanceof Project)
        {
            myProject = (Project) needsInfo;
        }
        else if(needsInfo instanceof JavaPackage)
        {
            packages.add((JavaPackage)needsInfo);
        }
        else if(needsInfo instanceof JavaClass)
        {
            classes.add((JavaClass)needsInfo);
        }
    }

    public void generatePackages()
    {
        for(JavaPackage jp : packages)
        {
            System.out.println(jp.getContainer().getPath());
            new File(jp.getContainer().getPath()).mkdirs();
        }
        generateClasses();
    }

    public void generateClasses()
    {
        for(JavaClass jc : classes)
        {
            javaFileGenerator.generateJavaFile(jc);
        }
    }

    public void generateProject()
    {
        generatePackages();
    }
}
