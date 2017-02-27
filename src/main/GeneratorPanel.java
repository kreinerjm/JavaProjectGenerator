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
    public enum KeyboardContext {Editor,Inspector}
    int lineX,lineY;
    MouseContext mouseContext;
    KeyboardContext keyboardContext = KeyboardContext.Editor;
    View view;
    static int tickNum = 0;
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
        buttons.add(new Button("assets/class.png",1024-25-256-2,512-25-8, Button.Function.GenerateProject));

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
                    if(jc.interfacesImplemented.size()>0)
                    {
                        for(JavaClass toDo : jc.interfacesImplemented)
                        {
                            Container source = jc.getContainer();
                            Container target = toDo.getContainer();
                            b2d.drawLine(source.getX() + source.getWidth() / 2, source.getY(), target.getX() + target.getWidth() / 2, target.getY() + target.getHeight());
                        }
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
                if(c.getContains() instanceof Variable)
                    b2d.setColor(Color.BLACK);

                b2d.fillRect(c.getX(),c.getY(),c.getWidth(),c.getHeight());
                b2d.setColor(Color.BLACK);

                if(c.getContains() instanceof JavaClass)
                {
                    String s = "";
                    JavaClass jc = (JavaClass)c.getContains();
                    if(jc.isInterface())
                    {
                        s += "I";
                    }
                    else if(jc.isAbstract())
                    {
                        s += "A";
                    }
                    b2d.drawString(s,c.getX()+c.getWidth()/2-2,c.getY()+c.getWidth()/2+5);
                }
                if(c.getContains() instanceof Method)
                {
                    String s = "";
                    Method m = (Method)c.getContains();
                    if(m.isStatic())
                    {
                        s += "S";
                    }
                    else if(m.isAbstract())
                    {
                        s += "A";
                    }
                    if(m.isFinal())
                    {
                        s+= "F";
                    }
                    b2d.drawString(s,c.getX()+c.getWidth()/2-4,c.getY()+c.getWidth()/2+5);
                }

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

    public void processEditorEvent(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            focusContainer.setEditing(true);
            System.out.println(focusContainer.isEditing());
        }
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            containers.remove(focusContainer);
        }

        if (focusContainer.isEditing()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    focusContainer.setLabel(focusContainer.getLabel().substring(0, focusContainer.getLabel().length() - 1));
                }
                if (e.getKeyCode() >= 0x41 && e.getKeyCode() <= 0x5A) {
                    int index = e.getKeyCode() - 0x41;
                    focusContainer.setLabel(focusContainer.getLabel() + alphabet[index]);
                }
                if (e.getKeyCode() >= 0x30 && e.getKeyCode() <= 0x39) {
                    focusContainer.setLabel(focusContainer.getLabel() + "" + (e.getKeyCode() - 0x30));
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    focusContainer.setLabel(focusContainer.getLabel() + " ");
                }
            }

        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && focusContainer != null) {
                currentContainer = focusContainer;
                if (currentContainer.getContains() instanceof JavaPackage) {
                    System.out.println("Switching to view package");
                    view = View.Package;
                } else if (currentContainer.getContains() instanceof JavaClass) {
                    view = View.Class;
                } else if (currentContainer.getContains() instanceof Method) {
                    view = View.Method;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && view != View.Overview) {
                currentContainer = currentContainer.getParent();
                if (currentContainer.getContains() instanceof JavaPackage) {
                    System.out.println("Switching to view package");
                    view = View.Package;
                } else if (currentContainer.getContains() instanceof JavaClass) {
                    view = View.Class;
                } else if (currentContainer.getContains() instanceof Method) {
                    view = View.Method;
                }
            }
        }
    }

    public void processInspectorEvent(KeyEvent e) {
        if (inspector.stringEditing) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                inspector.stringEditing = false;
                keyboardContext = KeyboardContext.Editor;
            }

            if (shift) {
                if (e.getKeyCode() >= 0x41 && e.getKeyCode() <= 0x5A) {
                    int index = e.getKeyCode() - 0x41;
                    addString("" + alphabetCaps[index]);
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shift = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    removeString();
                }
                if (e.getKeyCode() >= 0x41 && e.getKeyCode() <= 0x5A) {
                    int index = e.getKeyCode() - 0x41;
                    addString("" + alphabet[index]);
                }
                if (e.getKeyCode() >= 0x30 && e.getKeyCode() <= 0x39) {
                    addString("" + (e.getKeyCode() - 0x30));
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    addString(" ");
                }
            }
        }
    }

    public void addString(String s) {
        if (focusContainer.getContains() instanceof Method) {
            Method m = (Method) focusContainer.getContains();
            m.returnType += s;
        } else if (focusContainer.getContains() instanceof Variable) {
            Variable v = (Variable) focusContainer.getContains();
            v.type += s;
        } else if (focusContainer.getContains() instanceof Project) {
            Project p = (Project) focusContainer.getContains();
            p.path += s;
        }
    }

    public void removeString() {
        if (focusContainer.getContains() instanceof Method) {
            Method m = (Method) focusContainer.getContains();
            m.returnType = m.returnType.substring(0, m.returnType.length() - 1);
        } else if (focusContainer.getContains() instanceof Variable) {
            Variable v = (Variable) focusContainer.getContains();
            v.type = v.type.substring(0, v.type.length() - 1);
        } else if (focusContainer.getContains() instanceof Project) {
            Project p = (Project) focusContainer.getContains();
            p.path = p.path.substring(0, p.path.length() - 1);
        }
    }

    private void checkInspectorButtons ( int x, int y)
    {
        Button b = inspector.getButton(x, y);
        switch (b.function) {
            case StaticToggle: {
                if (focusContainer.getContains() instanceof Method) {
                    Method m = (Method) focusContainer.getContains();
                    m.setStatic(!m.isStatic());
                } else if (focusContainer.getContains() instanceof Variable) {
                    Variable v = (Variable) focusContainer.getContains();
                    v.setStatic(!v.isStatic());
                }
                break;
            }
            case AbstractToggle: {
                if (focusContainer.getContains() instanceof Method) {
                    Method m = (Method) focusContainer.getContains();
                    m.setAbstract(!m.isAbstract());
                } else if (focusContainer.getContains() instanceof JavaClass) {
                    JavaClass jc = (JavaClass) focusContainer.getContains();
                    jc.setAbstract(!jc.isAbstract());
                }
                break;
            }
            case FinalToggle: {
                if (focusContainer.getContains() instanceof Method) {
                    Method m = (Method) focusContainer.getContains();
                    m.setFinal(!m.isFinal());
                } else if (focusContainer.getContains() instanceof Variable) {
                    Variable v = (Variable) focusContainer.getContains();
                    v.setFinal(!v.isFinal());
                }
                break;
            }
            case InterfaceToggle: {
                JavaClass jc = (JavaClass) focusContainer.getContains();
                jc.setInterface(!jc.isInterface);
                if(!jc.isInterface())
                {
                    for(Container c : currentContainer.getContainers())
                    {
                        if(c.getContains() instanceof JavaClass)
                        {
                            JavaClass toRemove = (JavaClass) c.getContains();
                            if(toRemove.interfacesImplemented.contains(jc))
                            {
                                toRemove.interfacesImplemented.remove(jc);
                                if(!toRemove.hasClassExtended())
                                {
                                    toRemove.setClassExtended(jc);
                                }
                            }
                        }
                    }
                }
                break;
            }
            case StringEdit: {
                keyboardContext = KeyboardContext.Inspector;
                inspector.stringEditing = true;
                break;
            }
            case AccessToggle: {
                if (focusContainer.getContains() instanceof Method) {
                    Method m = (Method) focusContainer.getContains();
                    if (m.accessModifier == JavaObject.Access.Public) {
                        m.accessModifier = JavaObject.Access.Private;
                    } else {
                        m.accessModifier = JavaObject.Access.Public;
                    }
                } else if (focusContainer.getContains() instanceof Variable) {
                    Variable v = (Variable) focusContainer.getContains();
                    if (v.accessModifier == JavaObject.Access.Public) {
                        v.accessModifier = JavaObject.Access.Private;
                    } else {
                        v.accessModifier = JavaObject.Access.Public;
                    }
                } else if (focusContainer.getContains() instanceof JavaClass) {
                    JavaClass jc = (JavaClass) focusContainer.getContains();
                    if (jc.accessModifier == JavaObject.Access.Public) {
                        jc.accessModifier = JavaObject.Access.Private;
                    } else {
                        jc.accessModifier = JavaObject.Access.Public;
                    }
                }
                break;
            }
        }
    }

    private void checkEditorButtons(int x, int y) {
        for (Button b : buttons) {
            if (b.contains(x, y)) {
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


    private void handleMouse() {
        if (mouse) {
            int x, y;
            switch (mouseContext) {
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
        if (inspector.buttonContains(e.getX(), e.getY())) {
            checkInspectorButtons(e.getX(), e.getY());
        } else {
            checkEditorButtons(e.getX(), e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean assigned = false;
        for (Container c : currentContainer.getContainers()) {
            if (c.contains(e.getX(), e.getY())) {
                focusContainer = c;
                mouse = true;
                offsetX = e.getX() - c.getX();
                offsetY = e.getY() - c.getY();
                mouseContext = MouseContext.ContainerMove;
                assigned = true;
            } else if (c.getContains() instanceof JavaClass && c.classContainsBottom(e.getX(), e.getY())) {
                focusContainer = c;
                mouse = true;
                offsetX = e.getX() - c.getX();
                offsetY = e.getY() - c.getY();
                mouseContext = MouseContext.ClassExtension;
                assigned = true;
                //System.out.println("CONTAINED");
            }
        }
        if (!assigned && !inspector.contains(e.getX(), e.getY())) {
            focusContainer = currentContainer;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouse && mouseContext == MouseContext.ClassExtension) {
            for (Container c : currentContainer.getContainers()) {
                if (c.classContainsTop(e.getX(), e.getY())) {
                    JavaClass jc = (JavaClass) c.getContains();
                    JavaClass focus = (JavaClass) focusContainer.getContains();
                    if(focus.isInterface())
                    {
                        jc.interfacesImplemented.add(focus);
                    }
                    else
                    {
                        jc.setClassExtended(focus);
                    }

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
        switch (keyboardContext) {
            case Editor: {
                processEditorEvent(e);
                break;
            }
            case Inspector: {
                processInspectorEvent(e);
                break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = false;
        }
    }



}
