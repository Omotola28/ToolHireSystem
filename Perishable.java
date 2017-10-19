import java.util.Scanner;
import java.io.PrintWriter;
/**
 * The Perishable class stores the properties of perishable items in
 * the shop.
 * Perishable items are assumed to be liquids such as cleaning agents etc
 * 
 * @author Omotola Shogunle 
 * @version 4
 */
public class Perishable extends Accessory
{
    private boolean isIrritant;
    private String useByDate;
    private int volume;

    /**
     * Accessor methods for the class Perishable
     */

    public boolean getIsIrritant()
    {
        return isIrritant;
    }

    public String getUseByDate()
    {
        return useByDate;
    }

    public int getVolume()
    {
        return volume;
    }

    /**
     * Mutator methods for the class Perishable
     */

    public void setUseByDate(String useByDate)
    {
        this.useByDate = useByDate;
    }
    
     public void setVolume(int volume)
    {
        this.volume = volume;
    }
    
     public void setIsIrritant(boolean isIrritant)
    {
        this.isIrritant = isIrritant;
    }

    public void printDetails()
    {
        String confirmation;
        if(isIrritant == false)
        {
            confirmation = " is not";
        }
        else
        {
            confirmation = "is";
        }
        System.out.println("This item " +confirmation+ " perishable");
        System.out.printf("%-30s%12s%n","UseByDate:",useByDate);
        System.out.printf("%-30s%10d%n","Capacity:",volume);
        super.printDetails();
    }

    public void extractData(Scanner s)
    {
        isIrritant = s.nextBoolean();
        useByDate = s.next().trim();
        volume = s.nextInt();
        super.extractData(s);
    }
    
    public void writeData(PrintWriter pWriter)
    {
        String lineOfOutput = isIrritant+","+useByDate+","+volume+","+getPosition()+","+ getItemName()+","+getItemCode()+","+getCost();
        pWriter.println(lineOfOutput);
    }
}
