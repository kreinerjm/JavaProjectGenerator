package main;

import containers.Container;

/**
 * Created by Jacob on 1/27/2017.
 */
public class Variable extends Container {


    String type;
    boolean isInitialized;
    String identifier;
    Container parent;

    public Variable(String type, String identifier, boolean isInitialized)
    {
        this.type = type;
        this.identifier = identifier;
        this.isInitialized = isInitialized;
    }

    public Variable(String id)
    {
        identifier = id;
    }
}