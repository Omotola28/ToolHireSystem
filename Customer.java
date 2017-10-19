import java.util.Scanner;
import java.io.*;
/**
 * Write a description of class Customer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customer
{
    private String customerID;
    private String surname;
    private String firstName;
    private String otherInitials;
    private String title;

    /**
     * Constructor for objects of class Customer
     */
    public Customer(String surname, String firstName, String otherInitials, String title)
    {
        // initialise instance variables
        customerID = "unknown";
        this.surname = surname;
        this.firstName = firstName;
        this.otherInitials = otherInitials;
        this.title = title;
    }

    /**
     * Constructor for objects of class Customer
     * For testing purposes only 
     */
    public Customer()
    {
        // initialise instance variables
        customerID = "unknown";
        surname = "";
        firstName = "";
        otherInitials = "";
        title = "";
    }

    /**
     * Accessor Methods for the the class Customers
     */
    public String getCustomerID()
    {
        return customerID;
    }

    public String getSurname()
    {
        return surname;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getOtherInitials()
    {
        return otherInitials;
    }

    public String getTitle()
    {
        return title;
    }
    
    /**
     * Mutator methods for the class Customer 
     */
    public void setID(String customerID)
    {
        this.customerID = customerID;
    }
    
    public void printDetails()
    {
        System.out.printf("%-30s%14s%n","CustomerID:",customerID);
        System.out.printf("%-30s%14s%n","Surname:",surname);
        System.out.printf("%-30s%13s%n","FirstName:",firstName);
        System.out.printf("%-30s%12s%n","OtheInitials:",otherInitials);
        System.out.printf("%-30s%18s%n","Title:",title);
    }

    /**
     * This method enables Shop class to read datafile according to the way the 
     * Data is being stored in the datafile 
     * 
     * @param scan      is a Scanner object, aids read of data from a data file
     */
    public void readData(Scanner scan) 
    {
        customerID = scan.next();
        surname = scan.next();
        firstName = scan.next();
        otherInitials = scan.next();
        title = scan.next();
    }
    
    /**
     * This method enables the Shop class to write data to a data file 
     * from the way the data is being stored in the Customer Class 
     * 
     * @param pWriter       is a PrintWriter object used to write data to a data file
     */
    public void writeData(PrintWriter pWriter)
    {
       String lineOfOutput = customerID+", "+surname+", "+firstName+", "+
       otherInitials+", "+title;
       pWriter.println(lineOfOutput);
    }
}
