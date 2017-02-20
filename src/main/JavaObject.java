package main;

/**
 * Created by Jacob on 2/20/2017.
 */
public class JavaObject
{
    public enum Access {Public,Private};
    Container container;
    String label;

    public void setContainer(Container c)
    {
        container = c;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String toSet)
    {
        label = toSet;
    }

    public Container getContainer()
    {
        return container;
    }

    public Container getContainersParent()
    {
        return container.getParent();
    }

    public JavaObject getContainersParentObject()
    {
        return getContainersParent().getContains();
    }
}
