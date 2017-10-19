// These import statements could be greatly reduced in number by using,
// for example, import java.awt.*;, but in this form we can clearly see
// all the classes imported
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.print.PrinterException ;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Document;
import java.lang.Integer;
/**
 * Class ShopWindow is a front-end for the reservation system.
 * It concentrates the GUI aspects in one place and relies on the
 * Shop class to provide the shop model functionality.
 *
 * The window has an area for displaying output, for example the output
 * from System.out.println(), and a menu bar.
 *
 * @author D.E.Newton
 * @version Part 4 Step 5
 */
public class ShopWindow extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 42L; // needed because JFrame is serializable, otherwise generates compiler warning
    private static int WIDTH = 800, HEIGHT = 600; // for the areaScrollPane, which will dictate the overall window size

    private Shop shop;
    private Container contentPane;
    private JTextArea outputArea;
    private HashSet<String> associatedTextSet; // for menus and menu items
    private JMenu shopMenu, editMenu, itemMenu, customerMenu, reservationMenu, helpMenu, outputAreaBar;

    /**
     * Constructor for objects of class ReservationSystemWindow
     */
    public ShopWindow()
    {
        associatedTextSet = new HashSet<String>();
        initialiseWindow();
    }

    private void initialiseWindow()
    {
        setTitle("Shop: None Loaded");
        setLocation(50, 50);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setBackground(Color.magenta);  // magenta so it is obvious if can see the background when "layout" not worked correctly !
        contentPane.setLayout( new BoxLayout(contentPane, BoxLayout.Y_AXIS) );

        // this code, involving window adapter and window listener, is to ensure that if the
        // window is closed by using the "Close" icon at the top right hand corner of the
        // window then the "Exit" command is executed so that the system is closed down properly
        WindowAdapter windowListener = new WindowAdapter(){
                // override windowClosing()
                public void windowClosing(WindowEvent e)
                {
                    ActionEvent action = new ActionEvent(this, 0, "Exit");
                    actionPerformed(action);
                } };
        addWindowListener(windowListener);

        setupMenusAndActions();
        setUpOutputArea();

        setVisible(true);
    }

    private void setUpOutputArea()
    {
        // some basic ideas taken from
        //    http://www.jcreator.com/forums/index.php?showtopic=773
        //    http://javacook.darwinsys.com/new_recipes/14.9betterTextToTextArea.jsp
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier", Font.PLAIN, 12));
        outputArea.setEditable(true);
        outputArea.setBackground(Color.white);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setMargin(new Insets( 5, 10, 5, 10));
        outputArea.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        triggerPopMenu(e);
                    }
                }
            });

        JScrollPane areaScrollPane = new JScrollPane(outputArea);
        areaScrollPane.setPreferredSize( new Dimension(WIDTH, HEIGHT) );
        Border b = BorderFactory.createLoweredBevelBorder();
        areaScrollPane.setViewportBorder(BorderFactory.createTitledBorder(b, "Output View"));

        contentPane.add(areaScrollPane);
        pack();

        TextAreaOutputStream textOutput = new TextAreaOutputStream(outputArea);
        PrintStream capturedOutput = new PrintStream(textOutput);
        System.setOut(new PrintStream(capturedOutput));  // divert StandardOutput to capturedOutput
    }

    private Integer getKeyEventMnemonic(char letter)
    {

        if( !Character.isLetter(letter) )
        {
            // for non-letters, need to use KeyEvent.VK_XXX to associate letter
            System.out.println("\n*** Character <" + letter + "> is not a letter. ");
            System.out.println("*** Letters only are accepted for mnemonic association");
            System.exit(1); // none letter auto-detected, this can only occur during development so ok to halt
        }

        if( Character.isLowerCase(letter) )
        {
            System.out.println("\n*** Warning ");
            System.out.println("*** Mnemonic letter '" + letter + "' converted to upper case in getKeyEventMnemonic() ");
            System.out.println("*** When using the letter with the Alt key it is not case sensitive ");
            letter = Character.toUpperCase(letter);
        }

        // letters A-Z have simple associations
        int key = letter; // char assigned to int
        return key;
    }

    private JMenu setupMenu(JMenuBar menuBar, String menuText, char letter)
    {
        if( associatedTextSet.contains(menuText) )
        {
            // the text is used to identify the menu item and it should be unique to avoid confusion
            System.out.println("\n*** Aborting program");
            System.out.println("*** Attempt to define a menu with duplicate text \"" + menuText + "\" in setupMenu()");
            System.exit(2);  // duplicate auto-detected, this can only occur during development so ok to halt
        }
        int key = getKeyEventMnemonic(letter);
        JMenu menu =  new JMenu(menuText);
        menu.setMnemonic(key);
        menuBar.add(menu);
        return menu;
    }

    private void setupMenuItem(JMenu menu, String menuItemText, String menuItemTip,
    char letter, boolean enabled)
    {
        setupMenuItem(menu, menuItemText, menuItemTip, letter, enabled, null);
    }

    private void setupMenuItem(JMenu menu, String menuItemText, String menuItemTip,
    char letter, boolean enabled, KeyStroke keyStroke)
    {
        if( associatedTextSet.contains(menuItemText) )
        {
            // the text is used to identify the menu item and link it to an action so it must be unique
            System.out.println("\n*** Aborting program");
            System.out.println("*** Attempt to define a menu item with duplicate text \"" + menuItemText + "\" in setupMenuItem()");
            System.exit(3);  // duplicate auto-detected, this can only occur during development so ok to halt
        }
        associatedTextSet.add(menuItemText);

        int key = getKeyEventMnemonic(letter);
        JMenuItem menuItem = new JMenuItem(menuItemText);
        menuItem.addActionListener(this);
        menuItem.setEnabled(enabled);
        menuItem.setMnemonic(key);
        menuItem.setToolTipText(menuItemTip);
        if( keyStroke!=null )
        {
            menuItem.setAccelerator(keyStroke);
        }
        menu.add(menuItem);
    }

    private void setupMenusAndActions()
    {

        JMenuBar menuBar = new JMenuBar();

        // shop menu
        shopMenu = setupMenu(menuBar, "Shop", 'S');
        setupMenuItem(shopMenu, "New shop...", "Create a shop", 'N', true);
        setupMenuItem(shopMenu, "Open shop...", "Open a shop", 'O', true);
        setupMenuItem(shopMenu, "Close shop", "Close the shop", 'C', false);
        setupMenuItem(shopMenu, "Exit", "Close down and exit the model", 'X', true);

        // edit menu
        editMenu = setupMenu(menuBar, "Edit", 'E');
        setupMenuItem(editMenu, "Copy", "Copy selected text from Output area to clipboard", 'C', true, KeyStroke.getKeyStroke("ctrl C"));
        setupMenuItem(editMenu, "Clear", "Clear Output area", 'L', true, KeyStroke.getKeyStroke("ctrl L"));
        setupMenuItem(editMenu, "Print", "Print text in the output area", 'P', true);
        setupMenuItem(editMenu, "Cut", "Cut text in the output area", 'X', true);
        setupMenuItem(editMenu, "Paste", "Paste text to the output area", 'V', true);
        setupMenuItem(editMenu, "Save Output", "Save contents of the view pane to a file", 'S', true);
        setupMenuItem(editMenu, "Find", "Search for item", 'F', true);
        setupMenuItem(editMenu, "Find again", "Search item again", 'F', true);

        // item menu
        itemMenu = setupMenu(menuBar, "Shop item", 'I');
        setupMenuItem(itemMenu, "Print shop item...", "Display item with given code", 'V', false);
        setupMenuItem(itemMenu, "Print all shop items", "Display all items", 'D', false);

        // customer menu
        customerMenu = setupMenu(menuBar, "Customer", 'C');
        setupMenuItem(customerMenu, "Print customer...", "Display customer with given id", 'C', false);
        setupMenuItem(customerMenu, "Print all customers", "Display all customers in the shop", 'D', false);
        setupMenuItem(customerMenu, "Load customers...", "Read customer data from text file", 'L', false);

        // reservation menu
        reservationMenu = setupMenu(menuBar, "Reservation", 'R');
        setupMenuItem(reservationMenu, "Make reservation...", "Make a reservation of a shop item for a customer", 'M', false);
        setupMenuItem(reservationMenu, "Print all reservations", "Display all reservations in the model", 'D', false);
        setupMenuItem(reservationMenu, "Print all reservations between", "Display all reservations in the model between particular dates", 'D', false);
        setupMenuItem(reservationMenu, "Delete Reservation", "Delete reserved item from model", 'R', false);

        // help menu
        helpMenu = setupMenu(menuBar, "Help", 'H');
        setupMenuItem(helpMenu, "Help contents", "Launch html documentation", 'C', true);
        setupMenuItem(helpMenu, "About", "About the shop", 'A', true);

        setJMenuBar(menuBar);
    }

    //inner class extends DefaultHighlighter.DefaultHighlightPainter
    //Use to implement highlight of text when found in a search
    //@param highLightColor is a Color Object 
    class HighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
    {
        public HighlightPainter(Color highLightColor)
        {
            super(highLightColor);
        }
    }

    Highlighter.HighlightPainter highLighterPaint = new HighlightPainter(Color.yellow);
    /**
     * findText() method is called by the event handler for the find command 
     * This then shows an input dialogbox where user is to input "Text" to find 
     * 
     * Text is hightlighted in yellow 
     * 
     * @param component is a JTextComponent object, variable to accept the JTextArea for input text
     * @param s is a String object for text that is input to find
     */
    public void findText(JTextComponent component, String s)
    {
        deselectText(component);
        try{
            Highlighter highLight = component.getHighlighter();
            Document document = component.getDocument();
            String string = document.getText(0, document.getLength());
            int position =0;
            while((position=string.toLowerCase().indexOf(s.toLowerCase(),position)) >= 0)
            {
                highLight.addHighlight(position,position+s.length(), highLighterPaint);
                position+=s.length();
            }
            if((position=string.toLowerCase().indexOf(s.toLowerCase(),position)) < 0)
            {
                JOptionPane.showMessageDialog(this,"No text found","Find",JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    //The method deselectText() deselects previously searched text and only highlights the text that is inserted at that time 
    //this method is called in the findText() method 
    //@param component is a JTextComponent 
    private void deselectText(JTextComponent component)
    {
        Highlighter highLight = component.getHighlighter();
        Highlighter.Highlight[] highLighted = highLight.getHighlights();
        for(int i=0; i<highLighted.length; i++)
        {
            if(highLighted[i].getPainter() instanceof HighlightPainter)
            {
                highLight.removeHighlight(highLighted[i]);
            }
        }
    }

    /**
     * method actionPerformed
     *
     *    Action events are generated by components such as buttons and menus in response
     *    to mouse clicks, keystrokes etc.  These events as passed to all registered
     *    EventListener objects to deal with as they see fit. ShopWindow objects
     *    will respond to action events generated by menu items because the menu items have
     *    registered "this" as a listener using the addActionListener() method.
     *
     * @param e, an ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        //
        // System menu
        //
        if( action.equals("New shop...") || action.equals("Open shop...") )
        {
            // these two actions grouped together to avoid duplicate code
            String prefix;
            if( action.equals("New shop...") )
            {
                prefix = "creating";
            }
            else
            {
                prefix = "re-loading";
            }

            String inputMessage = "Please input the name of the shop";
            String name = JOptionPane.showInputDialog(inputMessage);
            if( name!=null )
            {
                if( name.length()==0 )
                    name = "Not named";
                System.out.println("\n " +  prefix + " shop \"" + name + "\"");
                shop = new Shop(name);
                if( action.equals("New shop...") )
                {
                    //shop.reloadShop();
                }
                // add name of the shop to the window title
                String oldTitle = getTitle();
                int posnColon = oldTitle.indexOf(":");
                setTitle(oldTitle.substring(0, posnColon+2) + name);

                checkEnableStatusOfCommands();
            }
        }
        else if( action.equals("Close shop"))
        {
            shop.closeDownSystem(); // save data so can restart
            shop = null;
            outputArea.selectAll();
            outputArea.setText("");

            checkEnableStatusOfCommands();
        }
        else if( action.equals("Exit") )
        {
            if( shop!=null )
            // a shop has been created or opened so save data so can restart
                shop.closeDownSystem();
            System.exit(0);  // close down the application
        }

        //
        // Edit menu
        //
        else if( action.equals("Copy") )
        {
            outputArea.copy();
        }
        else if( action.equals("Clear") )
        {
            outputArea.selectAll();
            outputArea.setText("");
        }
        else if( action.equals("Print") )
        {
            try
            {
                boolean complete = outputArea.print();
                if(complete)
                {
                    JOptionPane.showMessageDialog(this,"Printing Done","Information",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Printing....","Printer",JOptionPane.ERROR_MESSAGE);
                }
            }catch(PrinterException args)
            {
                JOptionPane.showMessageDialog(this, args);
            }
        }
        else if(action.equals("Cut"))
        {
            outputArea.cut();
        }
        else if(action.equals("Paste")) 
        {
            outputArea.paste();
        }
        else if(action.equals("Save Output") ) 
        {
            try{
                String inputID = "Please input name of file";
                String name = JOptionPane.showInputDialog(inputID);
                FileWriter fWriter = new FileWriter("\\Final1 ToolHireSystem Part4 Step2i\\"+name);
                BufferedWriter bWriter = new BufferedWriter(fWriter);
                outputArea.write(bWriter);
                bWriter.close();
                outputArea.setText("");
            }catch(Exception a)
            {
                JOptionPane.showMessageDialog(this, a);
            }
        }
        else if(action.equals("Find") || action.equals("Find again"))
        {
            String input;
            if(action.equals("Find"))
            {
                input = "Enter text you want to search for";
                String text = JOptionPane.showInputDialog(input);
                findText(outputArea,text);
            }
            else
            {
                input = "Enter text again";
                String text = JOptionPane.showInputDialog(input);
                findText(outputArea,text);
            }
        }
        //
        // Shop item menu
        //
        else if( action.equals("Print shop item...") )
        {
            String inputID = "Please input shopItemId";
            String name = JOptionPane.showInputDialog(inputID);
            shop.getItem(name);
        }
        else if( action.equals("Print all shop items") )
        {
            shop.printItemDetails();
        }
        //
        // customer menu
        //
        else if( action.equals("Print customer...") )
        {
            String inputID = "Please input customerId";
            String name = JOptionPane.showInputDialog(inputID);
            shop.getCustomer(name);
        }
        else if( action.equals("Print all customers") )
        {
            shop.printAllCustomers();
        }
        else if( action.equals("Load customers...") )
        {
            shop.readCustomerData();
            checkEnableStatusOfCommands();
        }

        //
        // reservation menu
        //
        else if( action.equals("Make reservation...") )
        {
            checkEnableStatusOfCommands();
            String inputID = "Please input CustomerId";
            String customerID = JOptionPane.showInputDialog(inputID);
            String inputID1 = "Please input itemId";
            String itemID = JOptionPane.showInputDialog(inputID1);
            String inputID2 = "Please input start Date";
            String startDate = JOptionPane.showInputDialog(inputID2);
            String inputID3 = "Please input noOfDays";
            String noOfDays = JOptionPane.showInputDialog(inputID3);
            int days = Integer.parseInt(noOfDays);
            shop. makeItemReservation(customerID, itemID, startDate, days);
            checkEnableStatusOfCommands();
        }

        else if( action.equals("Print all reservations") )
        {
            shop.printItemReservation();
        }

        else if(action.equals("Print all reservations between"))
        {
            String inputID = "Please input startDate";
            String startDate = JOptionPane.showInputDialog(inputID);
            String inputID1 = "Please input endDate";
            String endDate = JOptionPane.showInputDialog(inputID1);
            shop.printDiaryEntries(startDate, endDate);
        }

        else if(action.equals("Delete Reservation"))
        {
            String inputID1 = "Please input ReservationId";
            String reservationID = JOptionPane.showInputDialog(inputID1);
            shop.deleteItemReservation(reservationID);
        }

        //
        // Help menu
        //
        else if( action.equals("Help contents") )
        {
            errorPrintln("\nAction \"" + action + "\" not fully implemented");
        }
        else if( action.equals("About") )
        {
            String version = "Version: 4.11 (released 15 April, 2010)"; 
            JOptionPane.showMessageDialog(this, version, "About the shop",
                JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            // not recognised
            errorPrintln("\n*** Warning");
            errorPrintln("*** Action \"" + e.getActionCommand() + "\" not recognised");
        }
    }

    private void checkEnableStatusOfCommands()
    {
        // called after every action that might affect the state of the shop

        if( shop!=null )
        {
            // disable New shop... and Open shop... and enable Close shop commands on Shop menu
            menuItemSetEnabled(false, shopMenu, "New shop...");
            menuItemSetEnabled(false, shopMenu, "Open shop...");
            menuItemSetEnabled(true, shopMenu, "Close shop");

            // enable Load commands
            menuItemSetEnabled(true, customerMenu, "Load customers...");

            boolean hasItems = shop.getNumberOfItems()>0;
            boolean hasCustomers = shop.getNumberOfCustomers()>0;
            boolean hasReservations = shop.getNumberOfItemReservations()>0;

            if( hasItems )
            {
                // enable other items on shop item menu
                menuItemSetEnabled(true, itemMenu, "Print shop item...");
                menuItemSetEnabled(true, itemMenu, "Print all shop items");
                menuItemSetEnabled(true,customerMenu, "Load customers...");
            }

            if( hasCustomers )
            {
                // enable other items on customer menu
                menuItemSetEnabled(true, customerMenu, "Print customer...");
                menuItemSetEnabled(true, customerMenu, "Print all customers");
            }

            if( hasItems && hasCustomers )
            {
                // enable Make reservation... item on shop menu
                menuItemSetEnabled(true, reservationMenu, "Make reservation...");
            }

            if(hasReservations)
            {
                //enable items on Reservation menu
                menuItemSetEnabled(true, reservationMenu, "Print all reservations");
                menuItemSetEnabled(true, reservationMenu, "Print all reservations between");
                menuItemSetEnabled(true, reservationMenu, "Delete Reservation");
            }
        }
        else
        {
            // no shop model

            // enable New shop... and Open shop... commands
            menuItemSetEnabled(true, shopMenu, "New shop...");
            menuItemSetEnabled(true, shopMenu, "Open shop...");
            menuItemSetEnabled(false, shopMenu, "Close shop");

            // make sure all menu items in shop item, customer and reservation menus are disabled
            menuItemSetEnabled(false, itemMenu, "Print shop item...");
            //menuItemSetEnabled(false, itemMenu, "Print vehicles");
            menuItemSetEnabled(false, customerMenu, "Load customers...");
            menuItemSetEnabled(false, customerMenu, "Print customer...");
            menuItemSetEnabled(false, customerMenu, "Print all customers");
            menuItemSetEnabled(false, reservationMenu, "Make reservation...");
            menuItemSetEnabled(false, reservationMenu, "Print all reservations");
            menuItemSetEnabled(false, reservationMenu, "Print all reservations between");
        }
    }

    private void menuItemSetEnabled(boolean enable, JMenu menu, String actionCommand)
    {
        for(int i=0; i<menu.getItemCount(); i++)
        {
            JMenuItem item = menu.getItem(i) ;
            if( item.getActionCommand().equalsIgnoreCase(actionCommand) )
            {
                item.setEnabled(enable);
                return;
            }
        }

        // actionCommand not recognised
        errorPrintln("\n*** Warning -- Unexpected Error");
        errorPrintln("***\t Action \"" + actionCommand + "\" not recognised in method menuItemIsEnabled()");
        String enableAction;
        if( enable )
            enableAction = "enabled";
        else
            enableAction = "disabled";
        errorPrintln("***\t The action has NOT been " + enableAction);
        errorPrintln("***\t Please report this error to the System Administrator");
        errorPrintln("\n*** Note: The System Administrator is YOU if you have added ");
        errorPrintln("*** erroneous actionCommands.");
    }

    private void errorPrintln(String message)
    {
        // convenience method so can write error mesages to both System.err and System.out
        System.err.println(message);
        System.out.println(message);      
    }

    // inner class
    private class TextAreaOutputStream extends OutputStream
    {
        private final JTextArea textArea;
        private final StringBuilder sb = new StringBuilder();
        private boolean streamOpen = true;
        private boolean noError = true;

        public TextAreaOutputStream(JTextArea textArea)
        {
            this.textArea = textArea;
        }

        @Override
        public void flush()
        {
            // ignore
        }

        @Override
        public void close()
        {
            streamOpen = false;
        }

        @Override
        public void write(int b) throws IOException
        {
            // The print() and println() methods of the PrintStream class ultimately depend
            // on the abstract method write(int b) of the OutputStream class.  This subclass
            // overrides write(int b) and appends the character corresponding to b to a
            // StringBuffer object sb, unless that character corresponds to a line feed
            // character.  In the latter case, the contents of sb are appended to the JTextArea
            // and sb is cleared ready for the next call to write().
            if( !streamOpen )
            {
                if( noError )
                {
                    // Only output a message first time write() invoked when stream closed.
                    // Cannot (usefully) throw an IOException because the PrintStream that
                    // uses TextAreaOutputStream merely sets its error state true and then
                    // relies on checkError() being invoked.
                    System.err.println("\n*** Unexpected error: TextAreaOutputStream closed when attempting to use write()");
                    noError = false;
                }
                else
                    return; // ignore call
            }

            if (b == '\r') // carriage return, do nothing
                return;

            if (b == '\n') // line feed, first copy string to textArea
            {
                textArea.append(sb.toString());
                sb.setLength(0);
            }
            sb.append((char)b);
        }
    }

    private void triggerPopMenu(MouseEvent e)
    {
        JMenuItem cut = new JMenuItem("Cut");
        cut.setActionCommand("Cut");
        cut.addActionListener(this);
        JMenuItem copy = new JMenuItem("Copy");
        copy.setActionCommand("Copy");
        copy.addActionListener(this);
        JMenuItem paste = new JMenuItem("Paste");
        paste.setActionCommand("Paste");
        paste.addActionListener(this);
        JPopupMenu popMenu = new JPopupMenu();
        popMenu.add(copy);
        popMenu.add(cut);
        popMenu.add(paste);
        popMenu.show(e.getComponent(), e.getX(), e.getY());
    }
}