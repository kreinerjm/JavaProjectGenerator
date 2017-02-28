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

            writer.write(m.getDeclaration());

        }catch(Exception e){}
    }
}
