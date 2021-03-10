import java.text.*;
import javax.swing.*;
import java.util.*;

public class TestProduct
{
	
   static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

   public static void main(String args[]) throws ParseException
   {
       String state;
       String companyName;
       String productName;
       int quantity;
       String dateManu;
       double unitPrice;
       Product product = new Product();
       Manufacturer manufacture = new Manufacturer();
       Address address = null;
       Database productDB = new Database();
       Database deletedProdDB = new Database();
       Date present = new Date();
       boolean isDone = false;
       int subMenu = 0;

       while (!isDone)
       {
           int selectedMenu = IO.getInt("Welcome to Product Inventory\n\t1. Add Product\n"
                   + "\t2. Update Product (Quantity/Price)\n\t3. Delete Product\n"
                   + "\t4. Locate single product\n\t5. Product List\n"
                   + "\t6. List of deleted Products\n\t7. Exit");
           switch (selectedMenu)
           {
           case 1:

               productName = IO.getWord("Enter the product name: ");
               quantity = IO.getInt("Enter the quantity of the product: ");
               unitPrice = IO.getDouble("Enter the price of the product: ");
               companyName = IO.getWord("Enter the name of the manufacturing: ");
               state = IO.getWord("Enter state of the company: ");
               dateManu = IO.getWord("Enter the date of manufacturing in the form of(mm/dd/yyyy): ");
               
               address = new Address(state);
               manufacture = new Manufacturer(companyName, address);

               present = sdf.parse(dateManu);
               product = new Product(productName, quantity, unitPrice, present, manufacture);
               productDB.add(product);
               break;

           case 2:
               productName = IO.getWord("Enter product name to update: ");
               subMenu = IO.getInt("Select option to update: \n1. To update quantity\n2. To update price\n");
               productDB.search(productName);
               if (!productDB.inList())
               {
                   JOptionPane.showMessageDialog(null, "Product not found.");
               }
               else
               {

                   switch (subMenu) 
                   {
                                
                   case 1:
                       int menuOpt = IO.getInt("Select option to update: \n1. To add quantity\n2. To deduct quantity\n");
                       int addRDeductQuant = 0;

                       if (menuOpt < 1 || menuOpt > 2)
                       {
                           JOptionPane.showMessageDialog(null, "Please chose the correct choice");
                       }
                       else
                       {
                           switch (menuOpt)
                           {
                           case 1:
                               addRDeductQuant = IO.getInt("Enter the amount of quantity to add: ");
                               product = productDB.getProduct();
                               product.upDateQuantity(addRDeductQuant);
                               break;

                           case 2:

                               addRDeductQuant = IO.getInt("Enter the amount of quantity to deduct: ");
                               product = productDB.getProduct();
                               product.upDateQuantity(addRDeductQuant * -1);
                               break;
                           }

                           JOptionPane.showMessageDialog(null,"Quantity of the product is uupdated successfully.");
                       }
                       break;

                   case 2:
                       double priceToUpdate = IO.getDouble("Enter the unit price of product to update: ");

                       if (priceToUpdate < 0)
                       {
                           JOptionPane.showMessageDialog(null, "Price of the product is not updated.");
                       }
                       else
                       {
                           product = productDB.getProduct();
                           product.upDatePrice(priceToUpdate);

                           JOptionPane.showMessageDialog(null, "Price of the product is updated successfully.");
                       }
                       break;
                   default:
                       JOptionPane.showMessageDialog(null,
                               "Unable to process the option selected from the update list.");
                   }
               }
               break;

           case 3:
               productName = IO.getWord("Enter product name to update: ");
               productDB.search(productName);

               if (productDB.inList())
               {
                   int index = productDB.getIndex();
                   deletedProdDB.add(productDB.getProduct());
                   productDB.delete(index);
                   JOptionPane.showMessageDialog(null,
                           "The \"" + productName + "\" product is deleted successfully.");
               }

               else
               {
                   JOptionPane.showMessageDialog(null, "Product not found.");
               }
               break;

           case 4:

               productName = IO.getWord("Enter name of the product to display about: ");
               productDB.search(productName);
               
               if (productDB.inList())
               {
                   displaySingleProduct(productDB.getProduct(), JOptionPane.INFORMATION_MESSAGE);
               }

               else
               {
                   JOptionPane.showMessageDialog(null, "Product not found.");
               }
               break;

           case 5:
               if (productDB.getList() != null)
               {
                   displayInventory(productDB, JOptionPane.INFORMATION_MESSAGE);
               }

               else
               {
                   JOptionPane.showMessageDialog(null, "There are no products present in the inventory.");
               }
               break;

           case 6:
               if (deletedProdDB.getList() != null)
               {
                   displayInventory(deletedProdDB, JOptionPane.INFORMATION_MESSAGE);
               }

               else
               {
                   JOptionPane.showMessageDialog(null, "There are no products present in the inventory.");
               }
               break;

           case 7:
               isDone = true;
               break;

           default:
               JOptionPane.showMessageDialog(null, "Unable to process the option selected from the menu list.");
           }
       }
   }

   public static String getFormatedProductInfo(Product info)
   {
       String result = String.format("%30s", info.getProductName());
       result += String.format("%30s", sdf.format(info.getProductCreated()));
       result += String.format("%30s", info.getManufacture().getCompanyName());
       return result;
   }

   public static void displayDeletedInventory(Database productDB, int Type_Message)
   {
       String inventResult = "";
       ArrayList<Product> prodList = productDB.getList();
       inventResult += String.format("%30s %30s %30s", "Product", "Purchase Date", "Manufacturer");
       for (int i = 0; i < productDB.size(); i++)
       {
           inventResult += getFormatedProductInfo(prodList.get(i)) + "\n";
       }
       JTextArea text = new JTextArea(inventResult, 10, 50);

       JScrollPane pane = new JScrollPane(text);

       JOptionPane.showMessageDialog(null, pane, "Deleted Inventory Details", Type_Message);
   }

   public static void displayInventory(Database productDB, int Type_Message)
   {
       String inventResult = "";
       ArrayList<Product> prodList = productDB.getList();
       inventResult += String.format("%-30s \t%s %10s %20s %25s %30s\n", "Product", "Purchase Date", "Quantity",
               "Price", "Manufacturer", "State");
       for (int i = 0; i < productDB.size(); i++)
       {
           inventResult += prodList.get(i).getProductInfomation() + "\n";
       }
       JTextArea text = new JTextArea(inventResult, 10, 60);

       JScrollPane pane = new JScrollPane(text);

       JOptionPane.showMessageDialog(null, pane, "Inventory Details", Type_Message);
   }

   public static void displaySingleProduct(Product product, int Type_Message)
   {
       String productInfo = "Product Name: " + product.getProductName() + "\n";
       productInfo += String.format("Product's Unit Price: $%.2f", product.getUnitPrice()) + "\n";
       productInfo += "Quantity of product: " + product.getQuantity() + "\n";
       JTextArea text = new JTextArea(productInfo, 10, 30);

       JScrollPane pane = new JScrollPane(text);

       JOptionPane.showMessageDialog(null, pane, product.getProductName() + " Details", Type_Message);
   }
}