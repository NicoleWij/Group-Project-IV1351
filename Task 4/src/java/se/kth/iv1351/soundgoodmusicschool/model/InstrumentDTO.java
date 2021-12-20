package java.se.kth.iv1351.soundgoodmusicschool.model;

public interface InstrumentDTO {
    /**
     * @return The Instrument ID
     */
    public String getInstrumentID();
    /**
     * @return The Instrument Type
     */
    public String getType();
    /**
     * @return The Return Date
     */
    public String getReturnDate();
    /**
     * @return The Student ID
     */
    public String getStudentID();
    /**
     * @return The Instruments Monthly Price
     */
    public int getPrice();
    /**
     * @return The Instruments Brand
     */
    public String getBrand();

}
