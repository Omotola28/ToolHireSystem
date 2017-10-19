import java.util.Scanner;
import java.io.PrintWriter;
/**
 * The class Workwear stores properties of the workwear items like
 * gloves and other protective clothing
 * 
 * This class extends the Accesory class
 * 
 * @author (Omotola Matilda Shogunle) 
 * @version (14-03-2017)
 */
public class Workwear extends Accessory
{
    private String manStandard;
    private String colour;
    private String size;
    
    /**
     * Accessor methods for the class WorkWear
     */

    public String getManStandard()
    {
        return manStandard;
    }

    public String getColour()
    {
        return colour;
    }

    public String getSize()
    {
        return size;
    }

    /**
     * Mutator methods for the class Perishable
     */

    public void setManStandard(String manStandard)
    {
        this.manStandard = manStandard;
    }

    /**
     * Mutator methods for the class Perishable
     */

    public void setUseByDate(String colour)
    {
        this.colour = colour;
    }

    public void setVolume(String size)
    {
        this.size = size;
    }
    
    public void printDetails()
    {
        System.out.printf("%-24s%-30s%n","Manufacturing Standard:",manStandard);
        System.out.printf("%-30s%13s%n","Colour:",colour);
        System.out.printf("%-30s%15s%n","Size:",size);
        super.printDetails();
    }

    public void extractData(Scanner s)
    {
        manStandard = s.next();
        colour = s.next();
        size = s.next();
        super.extractData(s);
    }
    
    public void writeData(PrintWriter pWriter)
    {
        String lineOfOutput = manStandard+","+colour+","+size+","+getPosition()+","+getItemName()+","+getItemCode()+","+getCost();
        pWriter.println(lineOfOutput);
    }
}
