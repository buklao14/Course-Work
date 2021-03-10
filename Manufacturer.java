public class Manufacturer
{
   private String ManufactureName;
   private Address ManufactureAddress;

   public Manufacturer()
   {
       this.ManufactureName = "";
       this.ManufactureAddress = null;
   }

   public Manufacturer(String compName, Address address)
   {
       this.ManufactureName = compName;
       this.ManufactureAddress = address;
   }

   public String getCompanyName()
   {
       return ManufactureName;
   }

   public void setCompanyName(String companyName)
   {
       this.ManufactureName = companyName;
   }

   public Address getCompanyAddress()
   {
       return ManufactureAddress;
   }

   public void setCompanyAddress(Address address)
   {
       this.ManufactureAddress = address;
   }
}