package main;

import java.awt.*;

/**
 * Created by Jacob on 2/20/2017.
 */
public class Inspector
{
    int x, y, width, height;
    public Inspector(int x, int y, int width, int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public void drawInspector(Container focusContainer, Graphics2D b2d)
    {
        JavaObject jo = focusContainer.getContains();
        b2d.setColor(Color.green);
        b2d.fillRect(x,y,width,height);
        b2d.setColor(Color.black);
        b2d.drawRect(x,y,width,height);
        Font regular = new Font("Serif",Font.BOLD, 20);
        b2d.setFont(regular);
        drawRelativeString(focusContainer.getLabel(), 10, 20, b2d);
        if(jo instanceof Project)
        {
            int fontSize = 20;
            while(b2d.getFontMetrics().stringWidth(focusContainer.getPath()) > width-20)
            {
                fontSize--;
                b2d.setFont(new Font("Serif",Font.BOLD, fontSize));
            }
            drawRelativeString(focusContainer.getPath(), 10, 40, b2d);
        }
        else if(jo instanceof JavaPackage)
        {

        }
        else if(jo instanceof JavaClass)
        {
            JavaClass jc = (JavaClass) jo;
            if(jc.hasClassExtended())
                drawRelativeString("Extends: "+jc.getClassExtended().getLabel(),10,40,b2d);
            else
                drawRelativeString("Extends: None",10,40,b2d);
        }
        else if(jo instanceof Method)
        {

        }
        else if(jo instanceof Variable)
        {

        }
    }

    public void drawRelativeString(String toDraw, int x, int y, Graphics2D b2d)
    {
        b2d.drawString(toDraw, this.x+x,this.y+y);
    }

    public boolean contains(int x, int y)
    {
        return (x>this.x && x < this.x+width && y>this.y && y<this.y+height);
    }
}
