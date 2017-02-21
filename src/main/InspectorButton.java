package main;

/**
 * Created by Jacob on 2/21/2017.
 */
public class InspectorButton extends Button
{

    public InspectorButton(String imgName,int x, int y, Function function)
    {
        super(imgName,x,y,function);
    }

    public boolean contains(int x, int y)
    {
        if(x >= this.x && x <= this.x+20 && y >= this.y && y <= this.y+20)
        {
            return true;
        }
        return false;
    }
}
