package containers;

import main.JavaClass;
import main.JavaObject;
import main.JavaPackage;
import main.Project;

import java.util.ArrayList;

/**
 * Created by Jacob on 1/28/2017.
 */
public class Container
{
    String label;
    int width;
    int height;
    int x;
    int y;
    Container contains;
    ArrayList<Container> containers = new ArrayList<>();
    Container parent;

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public void setParent(Container c)
    {
        parent = c;
    }

    public Container getParent()
    {
        return parent;
    }

    public ArrayList<Container> getContainers()
    {
        return containers;
    }

    public Container getContains() { return contains; }

    boolean editing = false;

    public Container(String label, int x, int y, int width, int height)
    {
        this.label = label;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Container()
    {
        label = "";
        x = 0;
        y = 0;
        width = 10;
        height = 10;
    }

    public void setContains(Container c)
    {
        contains = c;
    }

    public void addContainer(Container c)
    {
        containers.add(c);
    }

    public boolean contains(int x, int y)
    {
        if(x >= this.x && x <= this.x+width && y >= this.y && y <= this.y+height)
        {
            return true;
        }
        return false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
