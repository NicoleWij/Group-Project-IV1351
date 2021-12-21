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
    public List<? extends InstrumentDTO> getAllRentableInstruments(String type) throws InstrumentException {
        try {
            return schoolDb.findAllRentableInstruments(type);
        } catch (Exception e) {
            throw new InstrumentException("Unable to list students.", e);
        }
    }

    /**
     * Creates a new rentable instrument for the specified student/instrument.
     * 
     * @param studentID The student's id.
     * @throws InstrumentException If unable to find student.
     */
    public void rentInstrument(int studentID, int instrumentID) throws InstrumentException {
        String failureMsg = "Could not rent instrument "+instrumentID+"for student: " + studentID;

        try {
            if(schoolDb.getStudentRentedInstrumentAmount(studentID)<2)
                schoolDb.rentInstrument(studentID,instrumentID);
        } catch (Exception e) {
            throw new InstrumentException(failureMsg, e);
        }
    }


    /**
     * Terminates the instrument given.
     * @param instrumentID The ID of the instrument.
     * @throws InstrumentException  If failed to terminate.
     * @throws SchoolDBException
     */
    public void terminateRental(int instrumentID) throws SchoolDBException, InstrumentException {
        String failureMsg = "Could not terminte instrument: " + instrumentID;

        try {
            schoolDb.terminateRental(instrumentID);
            System.out.println("Instrument " + instrumentID + " rental has now been terminated.");

        } catch (Exception e) {
            throw new InstrumentException(failureMsg, e);
        } 
    }

}
