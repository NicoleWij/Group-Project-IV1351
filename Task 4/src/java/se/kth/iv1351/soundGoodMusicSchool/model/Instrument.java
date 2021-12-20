package java.se.kth.iv1351.soundgoodmusicschool.model;

public class Instrument implements InstrumentDTO{
    private String instrumentID;
    private String type;
    private String returnDate;
    private String studentID;
    private int price;
    private String brand;


public Instrument(String instrumentID, String type, String returnDate, String studentID, int price,String brand) {
    this.instrumentID = instrumentID;
    this.type = type;
    this.returnDate = returnDate;
    this.studentID = studentID;
    this.price = price;
    this.brand = brand;
}

    /**
     * Creates an instrument with the specified details.
     * New rentable item is added
     * Null is for returnDate and StudentId
     * @param instrumentID  The ID of the instrument
     * @param type    The kind of instrument
     * @param price  The monthly price of renting the instrument
     */
    public Instrument(String instrumentID, String type, int price, String brand){
        this(instrumentID, type, null, null, price, brand);
    }
    /**
     * Creates an instrument with the specified details.
     * instrument is rented
     * @param instrumentID  The ID of the instrument
     * @param returnDate    The date which the instrument can be rented until
     * @param studentID     The ID of the student renting
     */
    public Instrument(String instrumentID, String studentID, String returnDate){
        this(instrumentID, null, returnDate, studentID, 0, null);
    }

    /**
     * @param returnDate Sets the return date.
     */
    public void setReturnDate(String returnDate){
        this.returnDate = returnDate;
    }

    /**
     * @param studentID Sets the student ID.
     */
    public void setStudentID(String studentID){
        this.studentID = studentID;
    }

    /**
     * @return The Instrument ID
     */
    public String getInstrumentID(){
        return instrumentID;
    }
    /**
     * @return The Instrument Type
     */
    public String getType(){
        return type;
    }
    /**
     * @return The Return Date
     */
    public String getReturnDate(){
        return returnDate;
    }
    /**
     * @return The Student ID
     */
    public String getStudentID(){
        return studentID;
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