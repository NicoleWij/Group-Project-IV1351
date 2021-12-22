package main.java.se.kth.iv1351.soundgoodmusicschool.model;

public class Instrument implements InstrumentDTO{
    private int instrumentID;
    private String type;
    private int price;
    private String brand;
    private boolean isAvalible;


public Instrument(int instrumentID, String type,int price,String brand,boolean isAvalible) {
    this.instrumentID = instrumentID;
    this.type = type;
    this.price = price;
    this.brand = brand;
    this.isAvalible=isAvalible;
}
   /**
     * @return Sets availability
     */
    public void setIsAvalible(boolean avalible){
        this.isAvalible= avalible;
    }

    /**
     * @return The Instrument availability
     */
    public boolean getIsAvalible(){
        return isAvalible;
    }
    /**
     * @return The Instrument ID
     */
    public int getInstrumentID(){
        return instrumentID;
    }
    /**
     * @return The Instrument Type
     */
    public String getType(){
        return type;
    }
    /**
     * @return The Instruments Monthly Price
     */
    public int getPrice(){
        return price;
    }
    /**
     * @return The Instruments Brand
     */
    public String getBrand(){
        return brand;
    }
}