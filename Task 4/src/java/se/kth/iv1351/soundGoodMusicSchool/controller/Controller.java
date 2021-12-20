package java.se.kth.iv1351.soundgoodmusicschool.controller;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

import java.se.kth.iv1351.soundgoodmusicschool.integration.SchoolDAO;
import java.se.kth.iv1351.soundgoodmusicschool.integration.SchoolDBException;
import java.se.kth.iv1351.soundgoodmusicschool.model.Instrument;
import java.se.kth.iv1351.soundgoodmusicschool.model.InstrumentDTO;
import java.se.kth.iv1351.soundgoodmusicschool.model.InstrumentException;

/**
 * This is the application's only controller, all calls to the model pass here.
 * The controller is also responsible for calling the DAO. Typically, the
 * controller first calls the DAO to retrieve data (if needed), then operates on
 * the data, and finally tells the DAO to store the updated data (if any).
 */
public class Controller {
    private final SchoolDAO schoolDb;

    /**
     * Creates a new instance, and retrieves a connection to the database.
     * 
     * @throws(SchoolDBException If unable to connect to the database.
     */
    public Controller() throws SchoolDBException {
        schoolDb = new SchoolDAO();
    }

    /**
     * Lists all students in the whole bank.
     * 
     * @return A list containing all students. The list is empty if there are no
     *         students.
     * @throws InstrumentException If unable to retrieve students.
     */
    public List<? extends InstrumentDTO> getAllRentableInstruments() throws InstrumentException {
        try {
            return schoolDb.findAllRentableInstruments();
        } catch (Exception e) {
            throw new InstrumentException("Unable to list students.", e);
        }
    }

    /**
     * Creates a new student for the specified student.
     * 
     * @param studentID The student's id.
     * @throws InstrumentException If unable to find student.
     */
    public void rentInstrument(String studentID, String instrumentID) throws InstrumentException {
        String failureMsg = "Could not rent instrument "+instrumentID+"for student: " + studentID;

        try {
            Instrument instrument = new Instrument(instrumentID,studentID,getReturnDate());
            if(schoolDb.getStudentInstrumentRented(instrument)<2)
                schoolDb.rentInstrument(instrument);
        } catch (Exception e) {
            throw new InstrumentException(failureMsg, e);
        }
    }


    /**
     * Terminates the instrument given.
     * @param instrumentID The ID of the instrument.
     * @throws InstrumentException  If failed to terminate.
     */
    public void terminateRental(String instrumentID) throws InstrumentException {
        String failureMsg = "Could not terminte instrument: " + instrumentID;

        try {
            schoolDb.terminateRental(instrumentID);
            System.out.println("Instrument " + instrumentID + " rental has now been terminated.");

        } catch (InstrumentException e) {
            throw new InstrumentException(failureMsg, e);
        } 
    }
    private String getReturnDate(){
        LocalDate year = LocalDate.now();
        LocalDate nextYear = year.plusYears(1);
        return nextYear.toString();
    }
}
