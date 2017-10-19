import java.util.Scanner;
import java.io.PrintWriter;
/**
 * ShopItem class is the super class for subclass Tool and Accessory 
 * and also their indiviual subclasses 
 * 
 * The come fields for these three classes are itemCode,itemName and cost
 * 
 * @author  Omotola Shogunle 
 * @version 4
 */
public abstract class ShopItem
{
    private String itemCode; 
    private String itemName;
    private int cost;
    
    /**
     * Accessor method for superClass ShopItem
     */
    protected String getItemCode()
    {
        return itemCode;
    }

    protected String getItemName()
    {
        return itemName;
    }

    protected int getCost()
    {
        return cost;
    }

    /**
     * Mutator methods for the superClass ShopItem
     */

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public void printDetails()
    {
        System.out.printf("%-30s%-40s%n","Name of Device:",itemName);
        System.out.printf("%-30s%14s%n","ItemCode:",itemCode);
        System.out.printf("%-30s%15.2f%n","Cost:",cost/100.0);//Displays cost in 2 d.p
    }

    public void extractData(Scanner s)// extract data for ShopItem class
    {
        itemName = s.next();
        itemCode = s.next();
        cost = s.nextInt();
    }
    
    abstract public void writeData(PrintWriter pWriter);
}
