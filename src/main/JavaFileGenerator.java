package main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by Jacob on 1/27/2017.
 */
public class JavaFileGenerator {

    MethodGenerator methodGenerator;

    public void generateJavaFile(JavaClass jc)
    {
        methodGenerator = new MethodGenerator();
        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jc.className+".java"), "utf-8")))
        {
            //generate imports

            //generate class declaration
            writer.write("public class "+jc.className+" {\n\n");

            //generate variables
            for(Variable v : jc.classVariables)
            {
                generateVariable(v,writer);
            }
            writer.write("\n");

            //initiate variables

            //generate methods
            for(Method m : jc.methods)
            {
                methodGenerator.generateMethod(m,writer);
            }

            //Close Class
            writer.write("\n}");

        }catch(Exception e){}

    }

    public void generateVariable(Variable v, Writer writer)
    {
        try {
            writer.write("\t");
            writer.write(v.type + " " + v.identifier + ";");
            writer.write("\n");
        }catch(Exception e){}
    }
}
