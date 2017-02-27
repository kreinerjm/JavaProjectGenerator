package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Jacob on 1/28/2017.
 */
public class Button
{
    public enum Function {AddPackage,AddClass,AddMethod,AddVariable,GenerateProject,StaticToggle,AbstractToggle,FinalToggle,InterfaceToggle,StringEdit,AccessToggle,ParameterToggle}
    Function function;
    int x, y;
    ImageIcon ii;
    Image img = null;

    public Button()
    {

    }

    public Button(String imgName,int x, int y, Function function)
    {
        ii = new ImageIcon(imgName);
        img = ii.getImage();
        this.function = function;
        this.x = x;
        this.y = y;
    }

    public boolean contains(int x, int y)
    {
        if(x >= this.x && x <= this.x+25 && y >= this.y && y <= this.y+25)
        {
            return true;
        }
        return false;
    }

}
