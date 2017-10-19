import java.util.Scanner;
import java.io.PrintWriter;
/**
 * This class models the electrical devices in the shop Class
 * It contains the tools properties such as name, itemCode, number of times Borrowed
 * onLoan, rechargeble, cost, weight and power.
 * 
 * Some of the classes unique properties are declared in this class while the common
 * Properties are inherited from the Tool superClass
 * 
 * @author Omotola Shogunle
 * @version 4.0
 */
public class ElectricTool extends Tool
{
    private boolean rechargable;  //Tells if the electric device can be recharged or not
    private String power;  //Shows the capacity of power that can be stored in the device

    /**
     * Accessor method for Electric Tool
     * 
     * @return The Electric fields values.
     */
    public boolean getRechargable()
    {
        return rechargable;
    }

    public String getPower()
    {
        return power;
    }

    /**
     * Mutator methods for ElectricTool 
     * 
     */
    public void setRechargable(boolean recharge)
    {
        rechargable = recharge;
    }

    public void setPower(String volts)
    {
        power = volts;
    }

    /**
     * Print details of Electric tool fields to the terminal
     * 
     * @param super calls the printDetails in the superClass Tools
     *        and print those values out before it prints out fields in this field 
     *        The positioning of super does not matter 
     */
    public void printDetails()
    {
        String confirmation;
        if(rechargable == false)
        {
            confirmation = " is not";
        }
        else
        {
            confirmation = "is";
        }
        super.printDetails();
        System.out.printf("%-30s%10s%n","Capacity:",power);
        System.out.println("This item " +confirmation+ " rechargeable");
    }

    /**
     * This is intended to help ElectricTool extract its own data 
     * to store in its class fields
     */
    public void extractData(Scanner s)
    {
        rechargable = s.nextBoolean();
        power = s.next().trim();
        super.extractData(s);
    }

    public void writeData(PrintWriter pWriter)
    {
        String lineOfOutput = rechargable+","+power+","+getTimesBorrowed()+","+getOnLoan()+","
                                +getItemName()+","+getItemCode()+","+getCost()+","+getWeight();
        pWriter.println(lineOfOutput);
    }
}
