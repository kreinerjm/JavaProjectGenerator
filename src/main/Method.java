package main;

import java.util.ArrayList;

/**
 * Created by Jacob on 1/27/2017.
 */
public class Method extends JavaObject {

    String returnType = "";
    Access accessModifier;
    boolean isStatic = false;
    boolean isFinal = false;
    boolean isAbstract = false;
    String identifier;
    ArrayList<Variable> methodParameters = new ArrayList<>();

    public Method(Access accessModifier, boolean isStatic, String returnType,
                  String identifier, ArrayList<Variable> methodParameters)
    {
        this.accessModifier = accessModifier;
        this.isStatic = isStatic;
        this.returnType = returnType;
        this.identifier = identifier;
        this.methodParameters = methodParameters;
    }

    public Method(String id)
    {
        identifier = id;
    }

    public boolean isAbstract()
    {
        return isAbstract;
    }

    public void setAbstract(boolean b)
    {
        isAbstract = b;
        if(isStatic)
            isStatic = false;
    }

    public boolean isFinal()
    {
        return isFinal;
    }

    public void setFinal(boolean b)
    {
        isFinal = b;
    }

    public boolean isStatic()
    {
        return isStatic;
    }

    public void setStatic(boolean b)
    {
        isStatic = b;
        if(isAbstract)
            isAbstract = false;
    }

}
