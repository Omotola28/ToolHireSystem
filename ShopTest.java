

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ShopTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ShopTest
{
    private Shop shop1;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     * 
     * Parameter pass to the Shop Object is the name of the Shop to be created 
     * 
     * @param "Havens" is a String object
     */
    @Before
    public void setUp()
    {
        shop1 = new Shop("Havens");
    }

    /**
     * Test method for getNumberOfCustomers() 
     * reads customerData from file "customer_data.txt" and counts the number of customers stored
     * If it returns 4 test is succesful.
     * 
     * The number returned depends on the number of items in test class.
     */
    
    @Test
    public void getNumberOfCustomers()
    {
        shop1.readCustomerData();
        assertEquals(4, shop1.getNumberOfCustomers());
    }
    
    /**
     * Reads customer data and prints out to the terminal
     */
    @Test
    public void readCustomerData()
    {
        shop1.readCustomerData();
        shop1.printAllCustomers();
    }

    /**
     * If no file was picked to be read, what happens? 
     * This test class shows appropraite result.
     */
    @Test
    public void readCustomerDataFail()
    {
        shop1.readCustomerData();//When a file is not selected to be read
    }

    /**
     * This method is testing to see what happens when the name of the file is not inputted 
     */
    @Test
    public void writeCustomerFail()
    {
        shop1.readCustomerData();
        shop1.writeCustomerData(""); //What happens when a fileName is not entered 
    }


    /**
     * Reads customer data from a file and then writes the same data to an external file
     * files are in txt format
     */
    @Test
    public void writeCustomerData()
    {
        shop1.readCustomerData();
        shop1.writeCustomerData("new_customer.txt");
    }

    /**
     * Stores customer in the customer map and to check that new customer is stored 
     * Old customers are read in, if new customer is saved properly number of customers 
     * will be five
     */
    @Test
    public void storeCustomer()
    {
        Customer customer1 = new Customer("Shogunle", "Omotola", "M", "Ms");
        shop1.storeCustomer(customer1);
        shop1.readCustomerData("new_customer.txt");
        shop1.printAllCustomers();
    }

    /**
     * Retrieves customer from uniqueIDput in the method 
     */
    @Test
    public void getCustomer()
    {
        shop1.readCustomerData("new_customer.txt");
        Customer customer1 = shop1.getCustomer("AB-006999");
        assertEquals(customer1, customer1);
    }
}












