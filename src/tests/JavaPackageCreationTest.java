package tests;

import main.JavaClass;
import main.JavaFileGenerator;
import main.Method;
import main.Variable;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jacob on 1/28/2017.
 */
public class JavaPackageCreationTest {

    public static void main(String[] args)
    {
        ArrayList<Variable> classVariables = new ArrayList<>();
        classVariables.add(new Variable("int","n",false));
        classVariables.add(new Variable("String","testString",false));
        ArrayList<Variable> methodParameters = new ArrayList<>();
        ArrayList<Method> methods = new ArrayList<>();
        methodParameters.add(new Variable("String[]","args",false));
        Method method = new Method(Method.Access.Public,true,"void","main",methodParameters);
        Method method2 = new Method(Method.Access.Private,false,"int","getN",null);
        methods.add(method);
        methods.add(method2);
        JavaClass jc = new JavaClass("TestClass",methods,classVariables);
        JavaFileGenerator javaFileGenerator = new JavaFileGenerator();
        javaFileGenerator.generateJavaFile(jc);


        new File("C:/Users/Jacob/Desktop/test/").mkdir();

    }

}
