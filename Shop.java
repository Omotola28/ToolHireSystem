
import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.io.*;
import java.util.Iterator;
/**
 * The Shop class stores different types of items.
 * 
 * Details of each item in the shop is displayed to the Terminal 
 * Data for each file is read off a text file with the help of flags 
 * item types are stored in appropraite classes.
 * 
 * @author Omotola Shogunle
 * @version 1.0
 */
public class Shop
{
    private Map<String,ShopItem> itemsMap;
    private Map<String,Customer> customerMap;
    private Map<String,ShopItemReservation> shopItemReservationMap;
    private Set<String> uniqueNo;
    private Random randomGenerator;
    private Diary diary;
    private int variable;
    private String dumpCustomerDataFileName;
    private String dumpItemReservationDataFileName;
    /**
     * Constructor for Shop Class
     */
    public Shop(String shopName)
    {
        itemsMap = new HashMap<String,ShopItem>();
        customerMap = new HashMap<String,Customer>();
        shopItemReservationMap = new HashMap<String, ShopItemReservation>(); 
        randomGenerator = new Random();
        uniqueNo = new HashSet<String>();
        diary = new Diary(); 
        readItemData("new_item_data.txt");
        dumpCustomerDataFileName = shopName + "Customer dump.txt";
        dumpItemReservationDataFileName = shopName+ "Shop item reservation dump.txt";
        reloadShop();
        variable = shopItemReservationMap.size();
    }

    /**
     * Returns the number of ShopItems that are in the Shop Class 
     * 
     * @retun int 
     */
    public int getNumberOfItems()
    {
        return itemsMap.size();
    }

    /**
     * Returns the number of Customers of Shop Class 
     * 
     * @retun int 
     */
    public int getNumberOfCustomers()
    {
        return customerMap.size();
    }

    /**
     * Returns the number of Reserved items in the Shop Class 
     * 
     * @retun int 
     */
    public int getNumberOfItemReservations()
    {
        return shopItemReservationMap.size();
    }

    /**
     * Store items to the itemsMap
     * 
     * @param  item   a ShopItem object
     */
    public void storeItem(ShopItem item)
    {
        if (!item.getItemCode().startsWith("AB-") || itemsMap.containsKey(item.getItemCode()))
        {
            item.setItemCode(generateCustomerID("AB-", 6));
        }
        itemsMap.put(item.getItemCode(),item);
    }

    /**
     * Returns item based on uniqueID from the itemsMap
     * 
     * @param uniqueID is a String object
     * 
     * @return a ShopItem object
     */
    public ShopItem getItem(String uniqueID)
    {
        ShopItem item = null;
        if(itemsMap.containsKey(uniqueID))
        {
            item = itemsMap.get(uniqueID);
            item.printDetails();
        }
        else 
        {
            System.out.println("Item does not exits!");
        }
        return item;
    }

    /**
     * Store customers to the customersMap
     * 
     * @param customer   A Customer Object
     */
    public void storeCustomer(Customer customer)
    {
        if (customer.getCustomerID().equals("unknown") || customerMap.containsKey(customer.getCustomerID()))
        {
            customer.setID(generateCustomerID("AB-", 6));
        }
        customerMap.put(customer.getCustomerID(),customer);
    }

    /**
     * Returns customer object based on uniqueId for customer 
     * 
     * @param uniqueID is a String object
     * 
     * @return Customer, a Customer Object
     */
    public Customer getCustomer(String uniqueID)
    {
        Customer customer = null;
        if(customerMap.containsKey(uniqueID)) 
        {
            customer = customerMap.get(uniqueID);
            customer.printDetails();
        }
        else
        {
            System.out.println("Customer does not exit");
        }
        return customer;
    }

    /**
     * Generates UniqueIds for itemsId and CustomerId
     * 
     * @param       prefix is a String object that should contain values "AB-"
     * @param       noOfDigits is an int, this sets the number of random digits to be generated 
     *              from 0-9
     *              
     * @return      Returns a String number as the generated UNIQUEID
     * 
     * @param       check, this checks to see if a unique number has been added to the randomNumber set
     *              it returns true if the number has been added, therefore number is unique
     *              or false if number has not been added, therefore another id is generated 
     */
    public String generateCustomerID(String prefix, int noOfDigits)
    {
        String number = ""; boolean check = false;
        while(check == false)
        {
            for(int i =0; i < noOfDigits; i++)
            {
                number += randomGenerator.nextInt(10);
            }
            check = isUnique(prefix + number);
        }
        return prefix + number;
    }

    /**
     * GenerateReservationNo method, generates unique reservation numbers for items 
     * that have been reserved.
     * 
     *  @param      check, this checks to see if a unique number has been added to the reservationNo set
     *              it returns true if the number has been added, therefore number is unique
     *              or false if number has not been added, therefore another id is generated 
     *              
     *  @param      variable, this is a field that is used to keep track of the sequence of numbers generated
     *              for every call to this method the variable is increased by 1
     *              
     *  @return     Returns a String number as the generated UNIQUEID
     */
    public String generateReservationNo()
    {
        String s = ""; boolean check = false;
        while(check == false)
        {
            for (int i = 0; i <= variable; i++) {
                s = String.format("%06d", i);// gives you leading zeros plus whatever the value of i is
            }
            check = isUnique(s);
        }
        variable++;
        return s;
    }

    /**
     * Strore Reserved items to the shopItemsReservationMap
     * 
     * @param item  A shopItemReservation object
     */
    public void storeItemReservation(ShopItemReservation item)
    {
        shopItemReservationMap.put(item.getReservationNo(),item);
        diary.addItemReservation(item);
    }

    /**
     * Retrives reserved items from the shopItemReservationMap
     * 
     * @param uniqueID is the key for the particular item you want to recall
     * 
     * @return ShopItemReservation Object
     * 
     */
    public ShopItemReservation getItemReservation(String uniqueID)
    {
        ShopItemReservation item = null;
        if(shopItemReservationMap.containsKey(uniqueID))
        {
            item = shopItemReservationMap.get(uniqueID);
        }
        else 
        {
            System.out.println("Item does not exits!");
        }
        return item;
    }

    /**
     * Method for making items reservation 
     * 
     * @param       customerID accepts a unique String for the customer wanting to make a reservation
     * @param       itemID the unique string for the item being reserved 
     * @param       startDate sets the day in which item was borrowed
     * @param       noOfDays tells the number of days the reservation is for
     * 
     */
    public boolean makeItemReservation(String customerID, String itemID, String startDate, int noOfDays)
    {
        boolean check = isUnique(itemID);
        if(customerMap.containsKey(customerID) || itemsMap.containsKey(itemID))
        {
            if(DateUtil.isValidDateString(startDate) == true  && noOfDays >= 0) 
            {
                if(diary.getItemReservations(DateUtil.convertStringToDate(startDate))== null)
                {
                    ShopItemReservation item = new ShopItemReservation(generateReservationNo(), itemID, customerID,startDate,noOfDays);
                    storeItemReservation(item);
                }
                else if(diary.getItemReservations(DateUtil.convertStringToDate(startDate))!= null && check == false)
                {
                    System.out.println("This item has been reserved:");
                }
                else if(diary.getItemReservations(DateUtil.convertStringToDate(startDate))!= null && check == true)
                {
                    ShopItemReservation item = new ShopItemReservation(generateReservationNo(), itemID, customerID,startDate,noOfDays);
                    storeItemReservation(item);
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * deleteItemReservation()  this method is passed a reservation number and deletes the corresponding 
     * reservation from the reservation system 
     * 
     * @param   reservationNo is a String object 
     */
    public void deleteItemReservation(String reservationNo)
    {
        if(shopItemReservationMap.containsKey(reservationNo))
        {
            ShopItemReservation r = shopItemReservationMap.get(reservationNo);
            diary.deleteItemReservation(r);
            System.out.println("Reservation:"+reservationNo+" has been deleted");
            System.out.println("===============================================");
        }
        else
        {
            System.out.println("No reservation Found! Check for correct ID");
        }
    }

    /**
     * Show items that have been reserved to the terminal 
     */
    public void printItemReservation()
    {
        if(!shopItemReservationMap.isEmpty()) 
        {
            Collection<ShopItemReservation> collectionValues = shopItemReservationMap.values();
            for(ShopItemReservation item : collectionValues) {
                item.printDetails();
                System.out.println();   // empty line between tools
            }   
        }
        else
        {
            System.out.println("Reservation List is empty!");
        }
    }

    /**
     * Show the shopItems details.Prints the shopItems details to the
     * terminal
     */
    public void printItemDetails()
    {
        if (!itemsMap.isEmpty())
        {
            Collection<ShopItem> collectionValues = itemsMap.values();
            for(ShopItem item : collectionValues) {
                item.printDetails();
                System.out.println();   // empty line between tools
            }   
        }
        else
        {
            System.out.println("ShopList is empty!");
        }
    }

    /**
     * Show the customer details.Prints the customer details to the
     * terminal
     */
    public void printAllCustomers()
    {
        if (!customerMap.isEmpty())
        {
            Collection<Customer> collectionValues = customerMap.values();
            for(Customer customer : collectionValues) {
                customer.printDetails();
                System.out.println();   // empty line between tools
            }    
        }
        else
        {
            System.out.println("CustomerList is empty!");
        }
    }

    /**
     * This method calls the printEntries() method of the Diary class
     * This enables users to of the system to display diary entries for a 
     * specified period 
     * 
     * 
     * @param startDate  is a String object
     * @param endDate    is a String object
     */
    public void printDiaryEntries(String startDate, String endDate)
    {
        diary.printEntries(DateUtil.convertStringToDate(startDate),DateUtil.convertStringToDate(endDate));
    }

    /**
     * Writes reserved items to a file, the name of the file is writen when the 
     * FileDialog box is opened.
     */

    public void writeItemReservationData()
    {
        Frame owner = null;
        FileDialog fileBox = new FileDialog(owner , "Save As" , FileDialog.SAVE);
        fileBox.setVisible(true);
        String directoryPath = fileBox.getDirectory();
        String nameFile = fileBox.getFile();
        System.out.printf("%s%s",directoryPath,nameFile); 
        if(nameFile!= null)
        {
            String filePath = directoryPath + nameFile; // absolute path name
            writeItemReservationData(filePath);
        }
        else
        {
            System.out.println("No file written to. Try again!");
        }
    }

    /**
     * Reads from text file passed from writeItemReservation() with no parameters 
     * 
     * @param fileName is a String object
     */
    public void writeItemReservationData(String fileName)
    {
        PrintWriter pWriter = null;
        try
        {
            pWriter = new PrintWriter(fileName);
        }
        catch (FileNotFoundException ex )
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("No file chosen");
            System.err.println("Please try again");
            fileName = null; //Unsuccessful so no file name is recorded
        }
        catch (NullPointerException ex)
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("No file chosen");
            System.err.println("Please try again");
            fileName = null; //Unsuccessful so no file name is recorded
        }
        if(pWriter != null)
        {
            pWriter.println("// this is a comment, any lines that start with //");
            pWriter.println("// (and blank lines) should be ignore");
            pWriter.println("");
            pWriter.println("// New ReservedItems data");
            pWriter.println("// data is reservationNo, itemID, customerID,startDate,noOfDays");
            Collection<ShopItemReservation> collectionValues = shopItemReservationMap.values();
            for(ShopItemReservation reservedItem : collectionValues)
            {
                reservedItem.writeData(pWriter);
            }
            pWriter.close();
        }
    }

    /**
     * Read reserveditems from a file, the name of the file is selected off the FileDialogBox
     * when is is opened 
     */
    public void readItemReservationData()
    {
        Frame myFrame = null;
        FileDialog fileBox = new FileDialog(myFrame , "open" , FileDialog.LOAD);
        fileBox.setVisible(true);
        String directoryPath = fileBox.getDirectory();
        String fileName = fileBox.getFile();
        System.out.printf("%s%s",directoryPath,fileName);
        if(fileName!= null)
        {
            String filePath = directoryPath + fileName; // absolute path name
            readItemReservationData(filePath);
        }
        else
        {
            System.out.println("No file selected try again!");
        }      
    }

    /**
     * Reads from text file passed from readItemReservationData() with no parameters 
     * 
     * @param filePath is a String object
     */
    public void readItemReservationData(String fileName)
    {
        File inFile = new File(fileName);
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(inFile);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("Data File <" + inFile + "> does not exist");
            System.err.println("Please try again");
            inFile = null; //Unsuccessful so no file name is recorded
        }
        if(scanner != null)
        {
            while(scanner.hasNext())
            {
                String lineOfText = scanner.nextLine().trim();
                boolean checkLine1 = lineOfText.startsWith("//"); // checks to see if line starts with "//"
                boolean checkLine2 = lineOfText.isEmpty(); //checks to see if line is empty
                if(!checkLine1 || checkLine2)
                {
                    Scanner s = new Scanner(lineOfText).useDelimiter(", ");
                    ShopItemReservation item = new ShopItemReservation();
                    while(s.hasNext())
                    {
                        item.readData(s);
                        storeItemReservation(item);
                    }
                    s.close();
                }
            }
        }
    }

    /**
     * Reads items data from a file into the itemsMap, the file to be read from is 
     * specified when the FileDialog Box is opened 
     */
    public void readCustomerData()

    {
        Frame myFrame = null;
        FileDialog fileBox = new FileDialog(myFrame , "open" , FileDialog.LOAD);
        fileBox.setVisible(true);
        String directoryPath = fileBox.getDirectory();
        String fileName = fileBox.getFile();
        System.out.printf("%s%s",directoryPath,fileName);
        if(fileName!= null)
        {
            String filePath = directoryPath + fileName; // absolute path name
            readCustomerData(filePath);
        }
        else
        {
            System.out.println("No file selected try again!");
        }
    }

    /**
     * Reads from text file passed from readCustomerData() with no parameters 
     * 
     * @param filePath is a String object
     */
    public void readCustomerData(String filePath)
    {
        File inFile = new File(filePath);
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(inFile);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("Data File <" + inFile + "> does not exist");
            System.err.println("Please try again");
            inFile = null; //Unsuccessful so no file name is recorded
        }
        if(scanner != null)
        {
            while(scanner.hasNext())
            {
                String lineOfText = scanner.nextLine().trim();
                boolean checkLine1 = lineOfText.startsWith("//"); // checks to see if line starts with "//"
                boolean checkLine2 = lineOfText.isEmpty(); //checks to see if line is empty
                if(!checkLine1 || checkLine2)
                {
                    Scanner s = new Scanner(lineOfText).useDelimiter(", ");
                    Customer customer = new Customer();
                    while(s.hasNext())
                    {
                        customer.readData(s);
                        storeCustomer(customer);
                    }
                    s.close();
                }
            }
        }
    }

    /**
     * Reads items data from a file into the itemsMap, the file to be read from is 
     * specified when the FileDialog Box is opened 
     */
    public void readItemData()

    {
        Frame myFrame = null;
        FileDialog fileBox = new FileDialog(myFrame , "open" , FileDialog.LOAD);
        fileBox.setVisible(true);
        String directoryPath = fileBox.getDirectory();
        String fileName = fileBox.getFile();
        System.out.printf("%s%s",directoryPath,fileName);
        if(fileName!= null)
        {
            String filePath = directoryPath + fileName; // absolute path name
            readItemData(filePath);
        }
        else
        {
            System.out.println("No file selected try again!");
        }
    }

    /**
     * Reads from text file passed from readItemData() with no parameters 
     * 
     * @param filePath is a String object
     */
    public void readItemData(String filePath)
    {
        File inFile = new File(filePath);
        Scanner scanner = null;
        String typeOfData = "";
        try
        {
            scanner = new Scanner(inFile);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("Data File <" + filePath + "> does not exist");
            System.err.println("Please try again");
            filePath = null; //Unsuccessful so no file name is recorded
        }
        if(scanner != null)
        {
            while( scanner.hasNext())
            {
                String lineOfText = scanner.nextLine().trim();
                if(lineOfText.startsWith("//"))// checks to see if line starts with "//"
                {
                    //ignore
                }
                else if (lineOfText.isEmpty()) //checks to see if line is empty
                {
                    //ignore
                }
                else if (lineOfText.startsWith("["))
                {
                    String output = lineOfText.toLowerCase();
                    if(output.equalsIgnoreCase("[ElectricTool data]"))
                    {
                        typeOfData = "electricTool data";
                    }
                    else if (output.equalsIgnoreCase("[HandTool data]"))
                    {
                        typeOfData = "handTool data";
                    }
                    else if (output.equalsIgnoreCase("[Perishable data]"))
                    {
                        typeOfData = "perishable data";
                    }
                    else if (output.equalsIgnoreCase("[Workwear data]"))
                    {
                        typeOfData = "workwear data";
                    }
                    else
                    {
                        typeOfData = "";
                        System.out.println("");
                        System.out.println("###ERROR###");
                        System.out.println("Unexpected flag");
                    }
                }
                else
                {
                    Scanner s = new Scanner(lineOfText).useDelimiter(",");
                    ShopItem item = null;
                    switch(typeOfData)
                    {
                        case "electricTool data":
                        item = new ElectricTool();
                        break;
                        case "handTool data":
                        item = new HandTool();
                        break;
                        case "perishable data":
                        item = new Perishable();
                        break;
                        case "workwear data":
                        item = new Workwear();
                        break;
                    }
                    if(item!=null)
                    {
                        item.extractData(s);
                        storeItem(item);
                    }
                }
            }
            scanner.close(); 
        }
    }

    /**
     * Writes items data to a new file, writes data to a new name file that 
     * would be written by user when FileDialogBox is opened
     * 
     *Electric tool data was read in from two different subheadings but data is writen back out under one sub-heading [ElectricTool]
     */
    public void writeItemData()
    {
        Frame owner = null;
        FileDialog fileBox = new FileDialog(owner , "Save As" , FileDialog.SAVE);
        fileBox.setVisible(true);
        String directoryPath = fileBox.getDirectory();
        String nameFile = fileBox.getFile();
        System.out.printf("%s%s",directoryPath,nameFile); 
        if(nameFile!= null)
        {
            String filePath = directoryPath + nameFile; // absolute path name
            writeItemData(filePath);
        }
        else
        {
            System.out.println("No file written to. Try again!");
        }
    }

    /**
     * Aids the writeItemData method to write items to a file 
     * the name of the file and the directory in which it would be stored is passed 
     * as a parameter to this method
     * 
     *  Each item data is divided into four 
     *          @ElectricTool
     *          @HandTool
     *          @Perishable
     *          @Workwear
     * *Electric tool data was read in from two different subheadings but data is writen back out under one sub-heading [ElectricTool]
     */
    public void writeItemData(String fileName)
    {
        PrintWriter pWriter = null;
        try
        {
            pWriter = new PrintWriter(fileName);
        }
        catch (FileNotFoundException ex )
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("No file chosen");
            System.err.println("Please try again");
            fileName= null; //Unsuccessful so no file name is recorded
        }
        catch (NullPointerException ex)
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("No file chosen");
            System.err.println("Please try again");
            fileName = null; //Unsuccessful so no file name is recorded
        }
        if(pWriter != null)
        {
            Collection<ShopItem> collectionValues = itemsMap.values();
            pWriter.println("// this is a comment, any lines that start with //");
            pWriter.println("// (and blank lines) should be ignore");
            pWriter.println("");
            pWriter.println("[ElectricTool Data]");
            pWriter.println("// data is rechargeable, power, timesBorrowed, onLoan, itemName, itemCode, cost, weight");
            for(ShopItem item : collectionValues)
            {
                if(item instanceof ElectricTool){
                    item.writeData(pWriter);
                }
            }
            pWriter.println("");
            pWriter.println("[HandTool Data]");
            pWriter.println("// data is sharpenable, timesBorrowed, onLoan, itemName, itemCode, cost, weight");
            for(ShopItem item : collectionValues)
            {
                if(item instanceof HandTool){
                    item.writeData(pWriter);
                }
            }
            pWriter.println("");
            pWriter.println("[Perishable Data]");
            pWriter.println("// data is isIrritant, useByDate, volume, position, itemName, itemCode, cost");
            for(ShopItem item : collectionValues)
            {
                if(item instanceof Perishable){
                    item.writeData(pWriter);
                }
            }
            pWriter.println("");
            pWriter.println("[WorkWear Data]");
            pWriter.println("// data is manufacturing standard, colour, size, position, itemName, itemCode, cost");
            for(ShopItem item : collectionValues)
            {
                if(item instanceof Workwear){
                    item.writeData(pWriter);
                }
            }
            pWriter.close();
        }
    }

    /**
     * Writes Customer data to a new file, using the FileDialog box to retrived 
     * pathName which is sent to the writeCustomerData() with the name the file to 
     * be written to and its directory
     */
    public void writeCustomerData()
    {
        Frame owner = null;
        FileDialog fileBox = new FileDialog(owner , "Save As" , FileDialog.SAVE);
        fileBox.setVisible(true);
        String directoryPath = fileBox.getDirectory();
        String nameFile = fileBox.getFile();
        System.out.printf("%s%s",directoryPath,nameFile); 
        if(nameFile!= null)
        {
            String filePath = directoryPath + nameFile; // absolute path name
            writeCustomerData(filePath);
        }
        else
        {
            System.out.println("No file written to. Try again!");
        }
    }

    /**
     * Writes to text file passed from writeCustomerData() with no parameters 
     * 
     * @param fileName is a String object
     */
    public void writeCustomerData(String fileName)
    {
        PrintWriter pWriter= null;
        try
        {
            pWriter = new PrintWriter(fileName);
        }
        catch (FileNotFoundException ex )
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("No file chosen");
            System.err.println("Please try again");
            fileName = null; //Unsuccessful so no file name is recorded
        }
        catch (NullPointerException ex)
        {
            System.err.println("\n\n*** FileNotFoundException ***");
            System.err.println("No file chosen");
            System.err.println("Please try again");
            fileName = null; //Unsuccessful so no file name is recorded
        }
        if(pWriter != null)
        {
            pWriter.println("// this is a comment, any lines that start with //");
            pWriter.println("// (and blank lines) should be ignore");
            pWriter.println("");
            pWriter.println("// New customer data");
            pWriter.println("// data is customerID, surname, firstName, otherInitials, title");
            Collection<Customer> collectionValues = customerMap.values();
            for(Customer customer : collectionValues)
            {
                customer.writeData(pWriter);
            }
            pWriter.close();
        }
    }

    /**
     * This method calls the writeCustomerData() and the writeItemReservationData() with parameters 
     * this call is so that the customer and item reservation data is written to the "dump" files
     * pointed to by the variables 
     * 
     *      dumpCustomerDataFileName
     *      dumpItemReservationDataFileName
     *      
     * respectively 
     */
    public void closeDownSystem()
    {
        writeCustomerData(dumpCustomerDataFileName);
        writeItemReservationData(dumpItemReservationDataFileName);
    }

    /**
     * This method calls the readCustomerData() and the readItemReservationData() with parameters 
     * this call is so that customers and itemReservation data is read from the two dump files 
     * 
     *      dumpCustomerDataFileName
     *      dumpItemReservationDataFileName
     *      
     * respectively 
     */
    public void reloadShop()

    {
        File f = new File("..//Final1 ToolHireSystem Part4 Step2i//"+dumpCustomerDataFileName);
        File f1 = new File("..//Final1 ToolHireSystem Part4 Step2i//"+dumpItemReservationDataFileName);
        if(f.exists() && f1.exists())// Checks to see if file exist or not before call the read methods 
        {
            readCustomerData(dumpCustomerDataFileName);
            readItemReservationData(dumpItemReservationDataFileName);
        }
    }

    public boolean isUnique(String unique)
    {
        boolean check = false;
        check = uniqueNo.add(unique);
        if(check == true)
            return true;
        else
            return false;
    }
}
