package coursework3;

public class Sales {
    
        final private String Region, Vehicle;
        final private int QTR, Quantity, Year;
        
    public Sales (int QTR, int Quantity, String Region, String Vehicle, int Year){
            this.QTR = QTR;
            this.Quantity = Quantity;
            this.Region = Region;
            this.Vehicle = Vehicle;
            this.Year = Year;
        }
    
    @Override
    public String toString() {
        return String.format("%s%s%s", ("Qtr:" + QTR + " "), ("Quantity:" + Quantity + " "), ("Region:" + Region + " "),("Vehicle:" + Vehicle + " "),("Year:" + Year + " "));
    }
   
    public int getQTR(){
        return QTR;
    }
    
    public int getQuantity(){
        return Quantity;
    }
    
    
    public String getRegion(){
        return Region;
    }
    
    
    public String getVehicle(){
        return Vehicle;
    }
    
    public int getYear(){
        return Year;
    }
    
    
}
