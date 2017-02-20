package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jacob on 10/2/2016.
 */

class GeneratorPanel extends JPanel implements MouseListener, KeyListener {

    ProjectGenerator generator;
    public enum View {Overview,Package,Class,Method}
    public enum MouseContext {ContainerMove,ClassExtension}
    int lineX,lineY;
    MouseContext mouseContext;
    View view;
    int tickNum = 0;
    private final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final char[] alphabetCaps = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    int offsetX, offsetY;
    boolean mouse = false, shift = false, cursorOn = false;
    private final BufferedImage buffer;
    ArrayList<Container> containers = new ArrayList<>();
    Container currentContainer;
    Container focusContainer;
    ArrayList<Button> buttons = new ArrayList<>();
    Project project;
    Container overview;
    Inspector inspector;

    public GeneratorPanel() throws IOException, CloneNotSupportedException {
        inspector = new Inspector(1024-256,0,256-1,512-8);
        project = new Project("project overview","C:/Users/Jacob/Desktop/TestProject/");
        overview = new Container("project overview",0,0,0,0);
        overview.setContains(project);
        currentContainer = overview;
        focusContainer = overview;
        buttons.add(new Button("assets/variable.png",512+25,512-25-8, Button.Function.AddVariable));
        buttons.add(new Button("assets/method.png",512,512-25-8, Button.Function.AddMethod));
        buttons.add(new Button("assets/package.png",512-50,512-25-8, Button.Function.AddPackage));
        buttons.add(new Button("assets/class.png",512-25,512-25-8, Button.Function.AddClass));
        buttons.add(new Button("assets/class.png",1024-25,512-25-8, Button.Function.GenerateProject));

        view = View.Overview;

        buffer = new BufferedImage(1024+16,512+32,BufferedImage.TYPE_INT_ARGB);

        this.setPreferredSize(new Dimension(1024 + 16, 512 + 32));
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        setOpaque(true);
    }

    public void tick()
    {
        if(tickNum % 5000000 == 0)
            cursorOn = !cursorOn;
        handleMouse();
        tickNum++;
        repaint();
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D b2d = buffer.createGraphics();
        b2d.setColor(Color.WHITE);
        b2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

        if(view == View.Overview)
        {

            for(Container c : overview.getContainers())
            {
                if(!c.equals(focusContainer) && c.isEditing())
                    c.setEditing(false);

                if(c.getContains() instanceof JavaPackage)
                    b2d.setColor(Color.blue);
                if(c.getContains() instanceof JavaClass)
                    b2d.setColor(Color.red);
                if(c.getContains() instanceof Method)
                    b2d.setColor(Color.green);
                if(c.getContains() instanceof Variable)
                    b2d.setColor(Color.black);

                b2d.fillRect(c.getX(),c.getY(),c.getWidth(),c.getHeight());
                b2d.setColor(Color.BLACK);
                int offset = (int)((1.0/2.0)*b2d.getFontMetrics().stringWidth(c.getLabel()));
                int stringX = c.getX()+(int)((1.0/2.0)*c.getWidth())-offset;
                int stringY = c.getY() - 10;


                if(c.equals(focusContainer)) {
                    if(c.isEditing()) {
                        b2d.setColor(Color.RED);
                        b2d.drawString(c.getLabel(), stringX, stringY);
                        if (cursorOn)
                            b2d.fillRect(stringX + 2 * offset + 1, stringY - 12, 2, 12);
                    }
                    else
                    {
                        b2d.setColor(Color.BLACK);
                        b2d.drawString(c.getLabel(), stringX, stringY);
                    }
                    b2d.setColor(Color.MAGENTA);
                    b2d.drawRect(c.getX() - 1, c.getY() - 1, c.getWidth() + 1, c.getHeight() + 1);
                    b2d.drawRect(c.getX() - 2, c.getY() - 2, c.getWidth() + 3, c.getHeight() + 3);
                }
                else{
                    b2d.setColor(Color.BLACK);
                    b2d.drawString(c.getLabel(), stringX, stringY);
                }
            }
        }
        else
        {

            for(Container c : currentContainer.getContainers())
            {
                if(!c.equals(focusContainer) && c.isEditing())
                    c.setEditing(false);

                if(c.getContains() instanceof JavaPackage)
                    b2d.setColor(Color.blue);
                if(c.getContains() instanceof JavaClass)
                {
                    b2d.setColor(Color.black);
                    JavaClass jc = (JavaClass) c.getContains();
                    if(jc.hasClassExtended())
                    {
                        Container source = jc.getContainer();
                        Container target = jc.getClassExtended().getContainer();
                        b2d.drawLine(source.getX()+source.getWidth()/2,source.getY(),target.getX()+target.getWidth()/2,target.getY()+target.getHeight());
                    }
                    b2d.setColor(Color.red);
                    int topEdge = c.getY();
                    int bottomEdge = topEdge + c.getWidth();
                    int xPos = c.getX() + c.getWidth()/2;
                    int offset = 5;
                    b2d.fillOval(xPos-offset,topEdge-offset,10,10);
                    b2d.fillOval(xPos-offset,bottomEdge-offset,10,10);
                }
                if(c.getContains() instanceof Method)
                    b2d.setColor(Color.green);

                b2d.fillRect(c.getX(),c.getY(),c.getWidth(),c.getHeight());
                b2d.setColor(Color.BLACK);
                int offset = (int)((1.0/2.0)*b2d.getFontMetrics().stringWidth(c.getLabel()));
                int stringX = c.getX()+(int)((1.0/2.0)*c.getWidth())-offset;
                int stringY = c.getY() - 10;


                if(c.equals(focusContainer)) {
                    if(c.isEditing()) {
                        b2d.setColor(Color.RED);
                        b2d.drawString(c.getLabel(), stringX, stringY);
                        if (cursorOn)
                            b2d.fillRect(stringX + 2 * offset + 1, stringY - 12, 2, 12);
                    }
                    else
                    {
                        b2d.setColor(Color.BLACK);
                        b2d.drawString(c.getLabel(), stringX, stringY);
                    }
                    b2d.setColor(Color.MAGENTA);
                    b2d.drawRect(c.getX() - 1, c.getY() - 1, c.getWidth() + 1, c.getHeight() + 1);
                    b2d.drawRect(c.getX() - 2, c.getY() - 2, c.getWidth() + 3, c.getHeight() + 3);
                }
                else{
                    b2d.setColor(Color.BLACK);
                    b2d.drawString(c.getLabel(), stringX, stringY);
                }
            }
        }
        for(Button b : buttons)
        {
            b2d.drawImage(b.img, b.x, b.y, 25, 25, null);
        }



        b2d.setColor(Color.BLACK);
        Font bold = new Font("Serif",Font.BOLD, 36);
        b2d.setFont(bold);

        int offset = (int)((1.0/2.0)*b2d.getFontMetrics().stringWidth(currentContainer.getLabel()));
        int stringX = (int)((1.0/2.0)*(1024+16))-offset;
        int stringY = 30;

        if(focusContainer.equals(currentContainer) && currentContainer.isEditing())
        {
            b2d.setColor(Color.red);
            b2d.drawString(currentContainer.getLabel(),stringX,stringY);
            if(cursorOn)
                b2d.fillRect(stringX + 2 * offset + 1, stringY - 27, 4, 28);
        }
        else
        {
            b2d.drawString(currentContainer.getLabel(),stringX,stringY);
        }

        b2d.setColor(Color.black);

        if(mouse && mouseContext == MouseContext.ClassExtension)
        {
            b2d.drawLine(focusContainer.getBottomMidpoint()[0],focusContainer.getBottomMidpoint()[1],lineX,lineY);
        }

        inspector.drawInspector(focusContainer,b2d);

        g2d.drawImage(buffer,0,0,buffer.getWidth(),buffer.getHeight(),null);
    }




    private void handleMouse()
    {
        if(mouse) {
            int x,y;
            switch(mouseContext)
            {
                case ClassExtension:
                    x = (int) getMousePosition().getX();
                    y = (int) getMousePosition().getY();
                    lineX = x;
                    lineY = y;
                    //System.out.println("CLASS EXTENSION");
                    break;
                case ContainerMove:
                    x = (int) getMousePosition().getX();
                    y = (int) getMousePosition().getY();
                    focusContainer.setX(x - offsetX);
                    focusContainer.setY(y - offsetY);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Button b : buttons) {
            if (b.contains(e.getX(), e.getY())) {
                switch (b.function) {
                    case AddPackage: {
                        if (view == View.Package || view == View.Overview) {
                            JavaPackage newPackage = new JavaPackage("");
                            Container newContainer = new Container("", 0, 0, 50, 50);
                            newPackage.setContainer(newContainer);
                            newContainer.setContains(newPackage);
                            currentContainer.addContainer(newContainer);
                            newContainer.setParent(currentContainer);
                            focusContainer = newContainer;
                        }
                        break;
                    }
                    case AddClass: {
                        if (view == View.Package) {
                            JavaClass newClass = new JavaClass("");
                            Container newContainer = new Container("", 0, 0, 50, 50);
                            newClass.setContainer(newContainer);
                            newContainer.setContains(newClass);
                            currentContainer.addContainer(newContainer);
                            newContainer.setParent(currentContainer);
                            focusContainer = newContainer;
                        }
                        break;
                    }
                    case AddMethod: {
                        if (view == View.Class) {
                            System.out.println("Method?");
                            Method newMethod = new Method("");
                            Container newContainer = new Container("", 0, 0, 50, 50);
                            newContainer.setContains(newMethod);
                            currentContainer.addContainer(newContainer);
                            newContainer.setParent(currentContainer);
                            focusContainer = newContainer;
                        }
                        break;
                    }
                    case AddVariable: {
                        if (view == View.Class || view == View.Method) {
                            Variable newVariable = new Variable("");
                            Container newContainer = new Container("", 0, 0, 50, 50);
                            newContainer.setContains(newVariable);
                            currentContainer.addContainer(newContainer);
                            newContainer.setParent(currentContainer);
                            focusContainer = newContainer;
                        }
                        break;
                    }
                    case GenerateProject: {
                        generator = new ProjectGenerator(overview);
                        break;
                    }

                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean assigned = false;
        for(Container c : currentContainer.getContainers())
        {
            if(c.contains(e.getX(),e.getY()))
            {
                focusContainer = c;
                mouse = true;
                offsetX = e.getX() - c.getX();
                offsetY = e.getY() - c.getY();
                mouseContext = MouseContext.ContainerMove;
                assigned = true;
            }
            else if(c.getContains() instanceof JavaClass && c.classContainsBottom(e.getX(), e.getY()))
            {
                focusContainer = c;
                mouse = true;
                offsetX = e.getX() - c.getX();
                offsetY = e.getY() - c.getY();
                mouseContext = MouseContext.ClassExtension;
                assigned = true;
                //System.out.println("CONTAINED");
            }
        }
        if(!assigned)
        {
            focusContainer = currentContainer;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(mouse && mouseContext == MouseContext.ClassExtension)
        {
            for(Container c : currentContainer.getContainers())
            {
                if(c.classContainsTop(e.getX(),e.getY()))
                {
                    JavaClass jc = (JavaClass) c.getContains();
                    jc.setClassExtended((JavaClass) focusContainer.getContains());
                    c.setContains(jc);
                }
            }
        }
        mouse = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            for(Container c : containers)
            {

            }
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
        {

            focusContainer.setEditing(true);
            System.out.println(focusContainer.isEditing());
        }
        if(e.getKeyCode() == KeyEvent.VK_DELETE)
        {
            containers.remove(focusContainer);
        }

        if(focusContainer.isEditing()) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                focusContainer.setEditing(false);
                focusContainer.getContains().setLabel(focusContainer.getLabel());
            }
            if (shift) {
                if (e.getKeyCode() >= 0x41 && e.getKeyCode() <= 0x5A) {
                    int index = e.getKeyCode() - 0x41;
                    focusContainer.setLabel(focusContainer.getLabel() + alphabetCaps[index]);
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shift = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    focusContainer.setLabel(focusContainer.getLabel().substring(0,focusContainer.getLabel().length() - 1));
                }
                if (e.getKeyCode() >= 0x41 && e.getKeyCode() <= 0x5A) {
                    int index = e.getKeyCode() - 0x41;
                    focusContainer.setLabel(focusContainer.getLabel() + alphabet[index]);
                }
                if (e.getKeyCode() >= 0x30 && e.getKeyCode() <= 0x39) {
                    focusContainer.setLabel(focusContainer.getLabel() + "" + (e.getKeyCode() - 0x30));
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    focusContainer.setLabel(focusContainer.getLabel() + " ");
                }
            }

        }
        else{
            if(e.getKeyCode() == KeyEvent.VK_ENTER && focusContainer != null)
            {
                currentContainer = focusContainer;
                if(currentContainer.getContains() instanceof JavaPackage)
                {
                    System.out.println("Switching to view package");
                    view = View.Package;
                }
                else if (currentContainer.getContains() instanceof JavaClass)
                {
                    view = View.Class;
                }
                else if(currentContainer.getContains() instanceof Method)
                {
                    view = View.Method;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE && view != View.Overview)
            {
                currentContainer = currentContainer.getParent();
                if(currentContainer.getContains() instanceof JavaPackage)
                {
                    System.out.println("Switching to view package");
                    view = View.Package;
                }
                else if (currentContainer.getContains() instanceof JavaClass)
                {
                    view = View.Class;
                }
                else if(currentContainer.getContains() instanceof Method)
                {
                    view = View.Method;
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT)
        {
            shift = false;
        }
    }
}
