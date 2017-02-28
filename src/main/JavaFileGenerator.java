package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

/**
 * Created by Jacob on 1/27/2017.
 */
public class JavaFileGenerator {

    MethodGenerator methodGenerator;

    public void generateJavaFile(JavaClass jc)
    {
        System.out.println("generating java file "+jc.getLabel()+"\n Path is "+jc.getPath());
        String path = jc.getPath().substring(0,jc.getPath().length()-1);
        System.out.println("augmented path is : "+path);
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append(".java");
        System.out.println("sb : "+sb.toString());
        if(path.charAt(path.length()-1) == '/')
        {
            System.out.println("before:"+path);
            path = path.substring(0,path.length()-1);
            System.out.println("after:"+path);
        }
        methodGenerator = new MethodGenerator();
        System.out.println("sb2 : "+sb.toString());
        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sb.toString()), "utf-8")))
        {
            //generate imports
            String header = "public ";
            //generate class declaration

            if(jc.isAbstract())
            {
                header+="abstract class ";
            }
            else if(jc.isInterface())
            {
                header+="interface ";
            }
            else
            {
                header+="class ";
            }
            writer.write(header+jc.getLabel() + " ");

            if(jc.hasClassExtended())
            {
                writer.write("extends "+jc.getClassExtended().getLabel()+" ");
            }
            if(jc.interfacesImplemented.size() > 0)
            {
                int i = 0;
                writer.write("implements ");
                for(JavaClass j : jc.interfacesImplemented)
                {
                    if(i<jc.interfacesImplemented.size()-1)
                        writer.write(j.getLabel()+ ",");
                    else
                        writer.write(j.getLabel());
                    i++;

                }

            }

            writer.write(" \n{\n\n");
            for(Container c : jc.getContainer().getContainers())
            {
                if(c.getContains() instanceof Variable)
                {
                    jc.classVariables.add((Variable)c.getContains());
                }
            }
            //generate variables
            for(Variable v : jc.classVariables)
            {
                generateVariable(v,writer);
            }
            writer.write("\n");

            //initiate variables

            //generate methods
            for(Container c : jc.getContainer().getContainers())
            {
                if(c.getContains() instanceof Method)
                {
                    jc.methods.add((Method)c.getContains());
                }
            }

            for(Method m : jc.methods)
            {
                for(Container c : m.getContainer().getContainers())
                {
                    if(c.getContains() instanceof Variable && ((Variable) c.getContains()).isParameter())
                    {
                        m.methodParameters.add((Variable)c.getContains());
                    }
                }
                methodGenerator.generateMethod(m,writer);
                writer.write("\n");
            }

            //Close Class
            writer.write("\n}");

        }catch(Exception e){System.out.println("error on path : "+sb.toString());
        System.out.println(e.getClass().toString());}

    }

    public void generateVariable(Variable v, Writer writer)
    {
        try {
            writer.write("\t");
            writer.write(v.type + " " + v.getLabel() + ";");
            writer.write("\n");
        }catch(Exception e){}
    }
}
