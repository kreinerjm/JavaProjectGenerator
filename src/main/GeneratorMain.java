package main;

import java.io.IOException;

/**
 * Created by Jacob on 10/2/2016.
 */

class GeneratorMain
{
    private final GeneratorFrame frame;
    private static boolean quit = false;
    private GeneratorMain() throws IOException, CloneNotSupportedException
    {
        frame = new GeneratorFrame();
        while(true && !quit)
        {
            tick();
        }
    }

    private void tick()
    {
        frame.panel.tick();
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException
    {
        new GeneratorMain();
    }

    public static void quit()
    {
        quit = true;
    }
}
