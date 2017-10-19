import java.util.Scanner;
import java.io.PrintWriter;
/**
 *  class stores information that are similar to the tools in the Shop Class
 *  class serves as the superClass to the different tools in the Shop
 * 
 * @author Omotola Shogunle
 * @version 4
 */
public abstract class Tool extends ShopItem
{
    private  int timesBorrowed;
    private  boolean onLoan;
    private int weight;

    /**
     * Accessor methods for the SuperClass Tool
     * 
     * Returns values of what is stored in the SuperClass fields
     */
    protected  int getTimesBorrowed()
    {
        return timesBorrowed;
    }

    protected  boolean getOnLoan()
    {
        return onLoan;
    }

    protected  int getWeight()
    {
        return weight;
    }

    /**
     * Mutator methods for the SuperClass Tool 
     * 
     *  are called in the Shop class to assign values from the text file to the fields
     */
    public  void setOnLoan(boolean onLoan)
    {
        this.onLoan = onLoan;
    }

    public  void setWeight(int weight)
    {
        this.weight = weight;
    }

    public  void setTimesBorrowed(int timesBorrowed)
    {
        this.timesBorrowed = timesBorrowed;
    }
    
    public void printDetails()
    {
        String status;
        if(onLoan == false)
        {
            status="Avaialble";
        }
        else
        {
            status = "Not available";
        }
        System.out.printf("%-24s%-30s%n","NoOfTimesBorrowed:",timesBorrowed);
        System.out.printf("%-30s%14b%n","Status:",status);
        super.printDetails();
        System.out.printf("%-30s%10d%1s%n","Weight:",weight/100,"g"); //Display numbers without trailing zeroes
    }

    public void extractData(Scanner s)
    {
       timesBorrowed = s.nextInt();
       onLoan = s.nextBoolean();
       super.extractData(s);
       weight = s.nextInt();
    }
    
    abstract public void writeData(PrintWriter pWriter);
}
