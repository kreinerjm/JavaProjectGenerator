package main;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jacob on 2/20/2017.
 */
public class Inspector
{
    int x, y, width, height;
    Button sToggle,fToggle,aToggle,iToggle,stringToggle, publicToggle, parameterToggle;
    boolean stringEditing = false;

    public Inspector(int x, int y, int width, int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        fToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.FinalToggle));
        sToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.StaticToggle));
        aToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.AbstractToggle));
        iToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.InterfaceToggle));
        stringToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.StringEdit));
        publicToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.AccessToggle));
        parameterToggle = (new InspectorButton("assets/toggle.png",x + width - 30,0, Button.Function.ParameterToggle));
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
            stringToggle.y = 20+4;
            aToggle.y = -20;
            sToggle.y = -20;
            fToggle.y = -20;
            iToggle.y = -20;
            publicToggle.y = -20;
            parameterToggle.y = -20;
            int fontSize = 20;
            while(b2d.getFontMetrics().stringWidth(focusContainer.getPath()) > width-20)
            {
                fontSize--;
                b2d.setFont(new Font("Serif",Font.BOLD, fontSize));
            }
            if(stringEditing)
            {
                b2d.setColor(Color.red);
            }
            drawRelativeString(focusContainer.getPath(), 10, 40, b2d);
            b2d.setFont(regular);
            b2d.drawImage(stringToggle.img, stringToggle.x, stringToggle.y,20,20,null);
        }
        else if(jo instanceof JavaPackage)
        {
            stringToggle.y = -20;
            aToggle.y = -20;
            sToggle.y = -20;
            fToggle.y = -20;
            iToggle.y = -20;
            publicToggle.y = -20;
            parameterToggle.y = -20;


            int fontSize = 20;
            while(b2d.getFontMetrics().stringWidth(focusContainer.getPath()) > width-20)
            {
                fontSize--;
                b2d.setFont(new Font("Serif",Font.BOLD, fontSize));
            }
            drawRelativeString(focusContainer.getPath(), 10, 40, b2d);

        }
        else if(jo instanceof JavaClass)
        {
            aToggle.y = 40+4;
            iToggle.y = 60+4;
            publicToggle.y = 80+4;
            sToggle.y = -20;
            fToggle.y = -20;
            stringToggle.y = - 20;
            parameterToggle.y = -20;

            JavaClass jc = (JavaClass) jo;
            if(jc.hasClassExtended())
                drawRelativeString("Extends: "+jc.getClassExtended().getLabel(),10,40,b2d);
            else
                drawRelativeString("Extends: None",10,40,b2d);
            drawRelativeString("Abstract : "+jc.isAbstract(), 10, 60, b2d);
            drawRelativeString("isInterface : "+jc.isInterface(),10,80,b2d);
            drawRelativeString("Public : "+(jc.accessModifier== JavaObject.Access.Public),10,100,b2d);


            b2d.drawImage(aToggle.img, aToggle.x, aToggle.y, 20, 20, null);
            b2d.drawImage(iToggle.img, iToggle.x, iToggle.y, 20, 20, null);
            b2d.drawImage(publicToggle.img, publicToggle.x, publicToggle.y,20,20,null);
        }
        else if(jo instanceof Method)
        {
            aToggle.y = 20+4;
            sToggle.y = 40+4;
            fToggle.y = 60+4;
            stringToggle.y = 80+4;
            publicToggle.y = 100+4;
            iToggle.y = -20;
            parameterToggle.y = -20;
            Method m = (Method) jo;
            drawRelativeString("Abstract : "+m.isAbstract(), 10, 40, b2d);
            drawRelativeString("Static : "+m.isStatic(), 10, 60, b2d);
            drawRelativeString("Final : "+m.isFinal(), 10, 80, b2d);
            drawRelativeString("Public : "+(m.accessModifier== JavaObject.Access.Public),10,120,b2d);
            if(stringEditing)
                b2d.setColor(Color.red);
            drawRelativeString("Return Type : "+m.returnType,10,100,b2d);


            b2d.drawImage(aToggle.img, aToggle.x, aToggle.y, 20, 20, null);
            b2d.drawImage(sToggle.img, sToggle.x, sToggle.y, 20, 20, null);
            b2d.drawImage(fToggle.img, fToggle.x, fToggle.y, 20, 20, null);
            b2d.drawImage(stringToggle.img, stringToggle.x, stringToggle.y,20,20,null);
            b2d.drawImage(publicToggle.img, publicToggle.x, publicToggle.y,20,20,null);
        }
        else if(jo instanceof Variable)
        {
            sToggle.y = 20+4;
            fToggle.y = 40+4;
            stringToggle.y = 60+4;
            publicToggle.y = 80+4;
            parameterToggle.y = 100+4;
            iToggle.y = -20;
            aToggle.y = -20;


            Variable v = (Variable) jo;
            drawRelativeString("Static : "+v.isStatic(), 10, 40, b2d);
            drawRelativeString("Final : "+v.isFinal(), 10, 60, b2d);
            drawRelativeString("Public : "+(v.accessModifier== JavaObject.Access.Public),10,100,b2d);
            drawRelativeString("Is Parameter : "+v.isParameter(),10,120,b2d);
            if(stringEditing)
                b2d.setColor(Color.red);
            drawRelativeString("Type : "+v.type,10,80,b2d);

            b2d.drawImage(sToggle.img, sToggle.x, sToggle.y, 20, 20, null);
            b2d.drawImage(fToggle.img, fToggle.x, fToggle.y, 20, 20, null);
            b2d.drawImage(stringToggle.img, stringToggle.x, stringToggle.y,20,20,null);
            b2d.drawImage(publicToggle.img, publicToggle.x, publicToggle.y,20,20,null);
            b2d.drawImage(parameterToggle.img, parameterToggle.x, parameterToggle.y,20,20,null);
        }
    }

    public void drawRelativeString(String toDraw, int x, int y, Graphics2D b2d)
    {
        b2d.drawString(toDraw, this.x+x,this.y+y);
    }

    public boolean buttonContains(int x, int y)
    {
        if(fToggle.contains(x,y) || sToggle.contains(x,y) || aToggle.contains(x,y) || iToggle.contains(x,y) || stringToggle.contains(x,y) || publicToggle.contains(x,y) || parameterToggle.contains(x,y))
        {
            return true;
        }
        return false;
    }

    public Button getButton(int x, int y)
    {
        if(fToggle.contains(x,y))
        {
            return fToggle;
        }
        else if(sToggle.contains(x,y))
        {
            return sToggle;
        }
        else if(aToggle.contains(x,y))
        {
            return aToggle;
        }
        else if(iToggle.contains(x,y))
        {
            return iToggle;
        }
        else if(stringToggle.contains(x,y))
        {
            return stringToggle;
        }
        else if(publicToggle.contains(x,y))
        {
            return publicToggle;
        }
        else if(parameterToggle.contains(x,y))
        {
            return parameterToggle;
        }
        return new Button();
    }

    public boolean contains(int x, int y)
    {
        return (x>this.x && x < this.x+width && y>this.y && y<this.y+height);
    }
}
