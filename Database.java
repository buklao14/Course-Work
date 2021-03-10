import java.util.ArrayList;

public class Database
{
   private ArrayList<Product> list;

   private Product product;
   private int index;
   private boolean found;

   public Database()
   {
       list = new ArrayList<Product>();
       product = null;
       index = 0;
       found = false;
   }

   public void search(String key)
   {
       found = false;
       int i = 0;

       while (!found && i < list.size())
       {
           Product b = list.get(i);
           if (b.getProductName().equalsIgnoreCase(key))
           {
               product = b;
               found = true;
               index = i;
           }
           else
               i++;
       }
   }

   public void add(Product newProduct)
   {
       list.add(newProduct);
   }

   public Product delete(int i)
   {
       return list.remove(i);
   }

   public int getIndex()
   {
       return index;
   }

   public boolean inList()
   {
       return found;
   }

   public Product getProduct()
   {
       return product;
   }

   public int size()
   {
       return list.size();
   }

   public boolean isEmpty()
   {
       return list.isEmpty();
   }

   public ArrayList<Product> getList()
   {
       return list;
   }
}

