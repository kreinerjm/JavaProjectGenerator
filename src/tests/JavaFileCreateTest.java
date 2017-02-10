package tests;


import java.io.*;
import java.net.URI;

/**
 * Created by Jacob on 1/27/2017.
 */
public class JavaFileCreateTest {


    public static void main(String[] args)
    {

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Test.java"), "utf-8")))
        {
            //File file = new File("test.txt");
            //file.createNewFile();
            //File to = new File(new URI("test.java"));
            //to.createNewFile();
            writer.write("public class Test{\npublic static void main(String[] args){\nSystem.out.println(\"Hello World from a generated java file!\");\n}\n}");



            //file.renameTo(to);
        }catch(Exception e){}
    }

}
