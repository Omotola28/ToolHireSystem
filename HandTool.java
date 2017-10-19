import java.util.Scanner;
import java.io.PrintWriter;
/**
 * This class models a different kind of Tool that can be found in the 
 * Shop class. It contains properties that are unique to its operation
 * and inherits other properties from the Tool superClass
 * 
 * @author Omotola Shogunle 
 * @version 4.0
 */
public class HandTool extends Tool
{
    private boolean sharpenable;

    /**
     * Accessor method for HandTools
     * 
     * @return The HandTool fields values.
     */
    public boolean getIsSharpenable()
    {
        return sharpenable;
    }

    /**
     * Mutator methods for HandTools 
     * 
     */
    public void setSharpenable(boolean sharpen)
    {
        sharpenable = sharpen;
    }

    public void printDetails()
    {
        String confirmation;
        if(sharpenable == false)
        {
            confirmation = " is not";
        }
        else
        {
            confirmation = "is";
        }
        super.printDetails();
        System.out.println("This item "+confirmation+ " sharpenable");
    }

    /**
     * This is intended to help HandTool extract its own data 
     * to store in its class fields and pass the remaining data to be stored in the 
     * superClass tool 
     */
    public void extractData(Scanner s)
    {
        sharpenable = s.nextBoolean();
        super.extractData(s);
    }

    public void writeData(PrintWriter pWriter)
    {
        String lineOfOutput = sharpenable+","+getTimesBorrowed()+","+getOnLoan()+","+ getItemName()+","+getItemCode()+","+getCost()+","+getWeight();
        pWriter.println(lineOfOutput);
    }
}
