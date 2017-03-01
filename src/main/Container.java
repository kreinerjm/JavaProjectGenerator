package main;

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
    JavaObject contains;
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

    public JavaObject getContains() { return contains; }

    boolean editing = false;

    public Container(String label, int x, int y, int width, int height)
    {
        this.label = label;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getPath()
    {
        String toRet = "";
        Container current = this;
        while(current.hasParent())
        {
            toRet = current.getLabel() +"/"+ toRet;
            current = current.getParent();
        }
        toRet = Project.path + toRet;
        return toRet;
    }

    public boolean hasParent()
    {
        if(getContains() instanceof Project)
            return false;
        return true;
    }

    public void setContains(JavaObject c)
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

    public boolean classContainsBottom(int x, int y)
    {
        int circleY = this.y + height;
        int a = Math.abs(x-getBottomMidpoint()[0]);
        int b = Math.abs(y-getBottomMidpoint()[1]);
        int distance = (int)Math.sqrt(a*a+b*b);
        //System.out.println(distance);
        if(distance <= 5 && y > circleY){
            return true;
        }
        return false;
    }

    public boolean classContainsTop(int x, int y)
    {
        int circleY = this.y;
        int a = Math.abs(x-getBottomMidpoint()[0]);
        int b = Math.abs(y-this.y);
        int distance = (int)Math.sqrt(a*a+b*b);
        System.out.println(distance);
        if(distance <= 5 && y < circleY){
            return true;
        }
        return false;
    }

    public int[] getBottomMidpoint()
    {
        return new int[]{x + width/2,y + height};
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

}
