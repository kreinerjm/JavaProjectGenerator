package main;


/**
 * Created by Jacob on 1/27/2017.
 */
public class Variable extends JavaObject {

    Access accessModifier = Access.Public;
    String type = "";
    boolean isFinal = false;
    boolean isStatic = false;
    boolean isParameter = false;
    String identifier;

    public Variable(String type, String identifier)
    {
        this.type = type;
        this.identifier = identifier;
    }

    public Variable(String id)
    {
        identifier = id;
    }

    public void setStatic(boolean b)
    {
        isStatic = b;
    }

    public boolean isStatic()
    {
        return isStatic;
    }

    public void setFinal(boolean b)
    {
        isFinal = b;
    }

    public boolean isFinal()
    {
        return isFinal;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public void setParameter(boolean parameter) {
        isParameter = parameter;
    }
}
