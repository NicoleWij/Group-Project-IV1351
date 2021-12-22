package main.java.se.kth.iv1351.soundgoodmusicschool.model;

public interface InstrumentDTO {
    /**
     * @return The Instrument ID
     */
    public int getInstrumentID();
    /**
     * @return The Instrument Type
     */
    public String getType();
    /**
     * @return The Return Date
     */
    /**
     * @return The Instruments Monthly Price
     */
    public int getPrice();
    /**
     * @return The Instruments Brand
     */
    public String getBrand();
    /**
     * @return The Instruments Brand
     */
    public boolean getIsAvalible();
}
