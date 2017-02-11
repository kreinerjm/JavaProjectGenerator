package main;

import java.util.ArrayList;

/**
 * Created by Jacob on 1/27/2017.
 */
public class Method extends Container {

    public static enum Access {Public,Private};

    String returnType;
    Access accessModifier;
    boolean isStatic;
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



}
