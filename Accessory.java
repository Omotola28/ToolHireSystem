import java.util.Scanner;
import java.io.PrintWriter;
/**
 * This superClass stores common fields between the the subclasses 
 * Perishable and Workwear 
 * 
 * Items that are similar are position, itemName, itemCode, cost
 * 
 * @author (Omotola) 
 * @version (14-3-2017)
 */
public abstract class Accessory extends ShopItem
{
    private String position;
    /**
     * Accessor methods for the Accessory Class
     */

    protected String getPosition()
    {
        return position;
    }

    /**
     * Mutator methods for the Accessory Class
     */

    public void setPosition(String position)
    {
        this.position = position;
    }

    public void printDetails()
    {
        System.out.printf("%-30s%12b%n","Position:",position);
        super.printDetails();
    }

    public void extractData(Scanner s)
    {
        position = s.next();
        super.extractData(s);
    }
    
    abstract public void writeData(PrintWriter pWriter);
}

