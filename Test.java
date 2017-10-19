
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
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * Testing to see if code works properly 
 * Testing classes Shop, Customer and ShopItem
 * 
 * @author (Omotola M Shogunle) 
 * @version (Version 6)
 */
public class Test
{
    public Test()
    {
        //Step 1 Testing 
        //This is testing for if Customer that is not store in the data file is added succesfully to the map when print details is called 
        Shop shop = new Shop("Havens");
        //         Customer customer = new Customer("Roberts","John","T","Mr");
        //         shop.storeCustomer(customer);
        //         shop.printAllCustomers();
        //         shop.readCustomerData("customer_data.txt");
        //         shop.printAllCustomers();

        //Step 2 Testing 
        //This is testing that show that all customer data is written succesful to a data file 
        //Shop shop = new Shop("Havens");
        //         shop.readCustomerData("customer_data.txt");
        //         Customer customer1 = new Customer("Shogunle", "Omotola", "M", "Miss");
        //         shop.storeCustomer(customer1);
        //         shop.writeCustomerData("new_customer_data.txt");
        //         Shop shop1 = new Shop("Havens");
        //         shop.readCustomerData("new_customer_data.txt");
        //         shop.printAllCustomers();

        //Step 3 Testing 
        //Testing to check that customer with id's that are unknown are given ID'S
        //Shop shop = new Shop("Havens");
        //         Customer customer2 = new Customer("Okorotie", "Emmanuella", "T", "Mrs");
        //         shop.storeCustomer(customer2);
        //         shop.printAllCustomers();

        //Step 4 Testing 
        //Testing to see if generateID generates unique IDs
        //Shop shop = new Shop("Havens");
        //         Customer customer = new Customer("Okorotie", "Emmanuella", "T", "Mrs");
        //         customer.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer);
        //         Customer customer1 = new Customer("Paul", "Andrew", "M", "Mr");
        //         customer1.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer1);
        //         Customer customer2 = new Customer("Duncan", "Olu", "W", "Mr");
        //         customer2.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer2);
        //         Customer customer3 = new Customer("Larry", "Oduwale", "O", "Mr");
        //         customer3.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer3);
        //         Customer customer4 = new Customer("Dola", "Akintola", "Y", "Ms");
        //         customer4.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer4);
        //         Customer customer5 = new Customer("Omotola", "Shogunle", "M", "Ms");
        //         customer5.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer5);
        //         Customer customer6 = new Customer("Olu", "Martins", "P", "Mr");
        //         customer6.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer6);
        //         Customer customer7 = new Customer("David", "Ajala", "K", "Mr");
        //         customer7.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer7);
        //         Customer customer8 = new Customer("Uncle", "Emma", "M", "Ms");
        //         customer8.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer8);
        //         Customer customer9 = new Customer("Alex", "Akubo", "B", "Mr");
        //         customer9.setID(shop.generateCustomerID("AB-", 1));
        //         shop.storeCustomer(customer9);
        //         shop.printAllCustomers();

        //Step 6 Testing
        //Testing to see that the way that data is saved works perfectly as previously 
        //Shop shop = new Shop("Havens");
        //shop.readCustomerData("customer_data.txt");
        //shop.printAllCustomers();

        //Part 4, Step 1 Testing 
        //This test calls the method convertToStringToDate() twice and then uses the two date objects to call the days between() method 
        //And prints out the number of days between the two dates chosen
        //         Date today = DateUtil.convertStringToDate("05-05-2017");
        //         Date twoWeeks = DateUtil.convertStringToDate("19-05-2017");
        //         System.out.println("The difference between " +DateUtil.convertDateToShortString(today)+ " and " 
        //                             +DateUtil.convertDateToShortString(twoWeeks)+ " is " +DateUtil.daysBetween(today,twoWeeks)+ " day(s)");

        //Part4, Step 2 Testing
        //This is to confirm that the reservation method works and that items that have been reserved would be printed to the screen 
        //Data items that should be read in for testing are files that already have uniqueID e.g new_customer_data.txt, new_item_data.txt
        //Shop shop = new Shop("Havens");
        //         shop.readCustomerData("new_customer_data.txt");
        //         shop.readItemData("new_item_data.txt");
        //         shop.makeItemReservation("AB-611220","AB-311926","10-05-2017", 10);
        //         shop.makeItemReservation("AB-668281","AB-267950","14-05-2017", 2);
        //         shop.makeItemReservation("AB-215280","AB-163159","17-05-2017", 10);
        //         shop.makeItemReservation("AB-018811","AB-861631","24-05-2017", 6);
        //         shop.makeItemReservation("AB-815778","AB-747250","1-06-2017", 5);
        //         shop.printItemReservation();
        //         System.out.println("Number of Reserved items: "+shop.getNumberOfItemReservations());

        //Part4, Step 3 Testing 
        //Testing to printDiaryEntries of reserved items over a course of days specified days 
        //Note that for the purposes of this project, a reservation for one day period(i.e noOfDays is equal to 1)
        //will have the same start and end date
        //Shop shop = new Shop("Havens");
        //         shop.readCustomerData("new_customer_data.txt");
        //         shop.readItemData("new_item_data.txt");
        //         shop.makeItemReservation("AB-611220","AB-311926","10-05-2017", 10);
        //         shop.makeItemReservation("AB-668281","AB-267950","14-05-2017", 2);
        //         shop.makeItemReservation("AB-215280","AB-163159","17-05-2017", 10);
        //         shop.makeItemReservation("AB-018811","AB-861631","24-05-2017", 6);
        //         shop.makeItemReservation("AB-815778","AB-747250","1-06-2017", 5);
        //         System.out.println("");
        //         shop.printDiaryEntries("10-05-2017", "5-06-2017");
        //
        //
        //Testing so that items that have alread been reserved for a period cannot be reserved 
        //if the item is alread reserved for all or part of the reservation period!
        //One item has already been reserved, in the list of reservation, expecting prompt to say that
        //Shop shop = new Shop("Havens");
        //         shop.readCustomerData("new_customer_data.txt");
        //         shop.readItemData("new_item_data.txt");
        //         shop.makeItemReservation("AB-611220","AB-311926","10-05-2017", 10);
        //         shop.makeItemReservation("AB-668281","AB-311926","10-05-2017", 2);
        //         shop.makeItemReservation("AB-215280","AB-311926","25-05-2017", 5);
        //         shop.makeItemReservation("AB-018811","AB-861631","24-05-2017", 6);
        //         shop.makeItemReservation("AB-815778","AB-747250","1-06-2017", 5);
        //         shop.printItemReservation();
        //
        //
        //Testing for when a reservation No is passed and Item is deleted from reservation List 
        //Reservation no is passed and therefore item is deleted from reservation list
        //Shop shop = new Shop("Havens");
        //         shop.readCustomerData("new_customer_data.txt");
        //         shop.readItemData("new_item_data.txt");
        //         shop.makeItemReservation("AB-611220","AB-311926","10-05-2017", 10);
        //         shop.makeItemReservation("AB-668281","AB-267950","10-05-2017", 2);
        //         shop.makeItemReservation("AB-215280","AB-311926","25-05-2017", 5);
        //         shop.makeItemReservation("AB-018811","AB-861631","24-05-2017", 6);
        //         shop.makeItemReservation("AB-815778","AB-747250","1-06-2017", 5);
        //         shop.deleteItemReservation("000001");
        //         shop.printDiaryEntries("09-05-2017", "20-05-2017");

        //Part4 Step4 Testing 
        //Testing to see if refactored methods are still working as previous before being refactored 
        //Shop shop = new Shop("Havens");
        //         shop.readCustomerData();
        //         shop.readItemReservationData();
        //         shop.writeCustomerData();
        //         shop.writeItemReservationData();
        //
        //Part4 Step5 Testing 
        //Futher testing would be done with the GUI to see if methods work appropraitely
        //ShopWindow shopWindow = new ShopWindow();
    }
}
