import java.util.Date;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Class ShopItemResearvation 
 * 
 *      This class specifies which customer, shop item, start date of the reservation 
 *      and the number of days the item is hired for.
 *      Reservations are given uniqueIds 
 * 
 * @author (Omotola M Shogunle) 
 */
public class ShopItemReservation
{
    private String reservationNo;
    private String itemID;
    private String customerID;
    private Date startDate;
    private int noOfDays;

    /**
     * Constructor for objects of class ShopItemReservation
     */
    public ShopItemReservation(String reservationNo, String itemID, String customerID, String startDate, int noOFDays)
    {
        // initialise instance variables
        this.reservationNo = reservationNo;
        this.itemID = itemID;
        this.customerID = customerID;
        this.startDate = DateUtil.convertStringToDate(startDate); 
        this.noOfDays = noOFDays;
    }
    
    /**
     * Constructor for the class ShopItemReservation 
     * 
     * This constructor has been implicity initialised to aid fast testing 
     */
    public ShopItemReservation()
    {
        reservationNo = "000005";
        itemID="AB-928197";
        customerID="AB-477392";
        startDate=DateUtil.convertStringToDate("09-05-2017");
        noOfDays=5;
    }
    
    /**
     * Class accessor methods 
     * 
     *      getReservationNo()  returns unique reservation number
     *      getItemID()         returns unique itemID
     *      getCustomerID()     returns unique customerID
     *      getStartDate()      returns first day of reservation
     *      getNoOfDays()       returns the duration of the reservation
     *      
     */
    public String getReservationNo()
    {
        return reservationNo;
    }
    
    public String getitemID()
    {
        return itemID;
    }
    
    public String getCustomerID()
    {
        return customerID;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }
    
    public int getNoOfDays()
    {
        return noOfDays;
    }
    
    /**
     * Class modifying methods 
     * 
     *      @param num  takes a String parameter to set reservationNo setReservationNo(num) 
     *      @param date is a Date object sets the startdate for when item was reserved setStartDate(date)
     */
    public void setReservationNo(String num)
    {
        reservationNo = num;
    }
    
    public void setStartDate(Date date)
    {
        startDate = date;
    }
    
    /**
     * Print all items in the shopItemReservation class 
     */
    public void printDetails()
    {
        System.out.println("This is reservation: " +reservationNo+ " for");
        System.out.format("%-30s%40s%n","ITEM:",itemID);
        System.out.format("%-30s%30s%n","CUSTOMER:",customerID);
        System.out.format("%-30s%14s%n","RESERVATION START DATE:",DateUtil.convertDateToShortString(startDate));
        System.out.format("%-30s%24d%n","DURATION:",noOfDays);
    }
    
     /**
     * This method enables the Shop class to write data to a data file 
     * from the way the data is being stored in the ShopItemReservation Class 
     * 
     * @param pWriter       is a PrintWriter object used to write data to a data file
     */
     public void writeData(PrintWriter pWriter)
    {
       String lineOfOutput = reservationNo+", "+itemID+", "+customerID+", "+
       DateUtil.convertDateToShortString(startDate)+", "+noOfDays;
       pWriter.println(lineOfOutput);
    }
    
    /**
     * This method enables ShopItemReservation class to read datafile according to the way the 
     * Data is being stored in the datafile 
     * 
     * @param scan      is a Scanner object, aids read of data from a data file
     */
    public void readData(Scanner scan) 
    {
        reservationNo = scan.next();
        itemID = scan.next();
        customerID = scan.next();
        startDate = DateUtil.convertStringToDate(scan.next());
        noOfDays = scan.nextInt();
    }
    
     /**
    * Returns a string representing the <code>ShopItemReservation</code> object.  For a 
    * reserved object with reservationNo 000001, customerID AB-467289, itemID AB-829182 will return the <code>String</code> 
    * 
    * "ReservationNo: 0000001, for Customer = AB-467289, and Item = AB-829182 "
    * 
    * @return   a <code>String</code> representation of the shopItemReservation
    */
   public String toString()
   {
      return "\nReservationNo:" +reservationNo+
             "\nCustomer:"+customerID+
             "\nItem:" +itemID;
   }
}
