package main;

import main.Method;
import main.Variable;

import java.io.Writer;

/**
 * Created by Jacob on 1/27/2017.
 */
public class MethodGenerator
{
    public void generateMethod(Method m,Writer writer)
    {
        try {
            String isStatic = m.isStatic ? " static " : " ";
            writer.write("\t" + m.accessModifier.toString().toLowerCase() + isStatic + m.returnType + " " + m.identifier + "(");
            boolean flag = false;
            if (m.methodParameters != null) {
                for (Variable p : m.methodParameters) {
                    if (!flag) {
                        writer.write(p.type + " " + p.identifier);
                        flag = true;
                    } else {
                        writer.write("," + p.type + " " + p.identifier);
                    }
                }
            }
            writer.write("){\n\n\t}\n");
        }catch(Exception e){}
    }
}
