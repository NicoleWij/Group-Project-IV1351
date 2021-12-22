package main.java.se.kth.iv1351.soundgoodmusicschool.integration;

import java.sql.PreparedStatement;
import main.java.se.kth.iv1351.soundgoodmusicschool.model.Instrument;
import main.java.se.kth.iv1351.soundgoodmusicschool.model.InstrumentDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO {
    private static final String RENTED_INSTRUMENT_TABLE_NAME = "rented_instrument";
    private static final String RENTED_INSTRUMENT_PK_COLUMN_NAME = "rental_id";
    private static final String TIME_OF_RENTAL_COLUMN_NAME = "TIME_OF_RENTAL";
    private static final String PREVIOUSLY_RENTED_COLUMN_NAME = "previously_rented";
    private static final String RENTED_STUDENT_FK_COLUMN_NAME = "student_id";
    private static final String RENTED_INSTRUMENT_FK_COLUMN_NAME = "instrument_id";

    private static final String INSTRUMENT_TABLE_NAME = "instrument";
    private static final String INSTRUMENT_PK_COLUMN_NAME = "instrument_id";
    private static final String TYPE_COLUMN_NAME = "type";
    private static final String BRAND_COLUMN_NAME = "brand";
    private static final String IS_AVALIBLE_COLUMN_NAME = "is_avalible";
    private static final String PRICE_COLUMN_NAME = "price";
 

    private Connection connection;
    private PreparedStatement newRentalStmt;
    private PreparedStatement rentalIDStmt;
    private PreparedStatement terminateRentalStmt;
    private PreparedStatement updateRentalInformation;
    private PreparedStatement getStudentInstrumentRentedStmt;
    private PreparedStatement rentInstrumentStmt;
    private PreparedStatement findAllRentableInstrumentsStmt;

    public SchoolDAO() throws SchoolDBException{
        try{
            connectToSchoolDB();
            prepareStatements();
        } catch(ClassNotFoundException | SQLException exception){
            throw new SchoolDBException("Couldnt connect to database",exception);
        }
    }

    /** terminateRentalStmt updates the instruments avalibality so its rentable again
     *  updateRentalInformation updates the previously rented so its logged into rented instruments
     */
    private void prepareStatements() throws SQLException {
        terminateRentalStmt = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
            + " SET " +IS_AVALIBLE_COLUMN_NAME  + " = true WHERE instrument_id=? ");
        
        updateRentalInformation= connection.prepareStatement("UPDATE "+ RENTED_INSTRUMENT_TABLE_NAME+" SET "+PREVIOUSLY_RENTED_COLUMN_NAME+" = false WHERE instrument_id=?");
        
        rentInstrumentStmt= connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
        + " SET " +IS_AVALIBLE_COLUMN_NAME  + " = false WHERE instrument_id=? ");

        newRentalStmt = connection.prepareStatement("INSERT INTO " + RENTED_INSTRUMENT_TABLE_NAME
            + "("+RENTED_INSTRUMENT_PK_COLUMN_NAME+ ", "+TIME_OF_RENTAL_COLUMN_NAME + ", " + RENTED_INSTRUMENT_FK_COLUMN_NAME + ", " + RENTED_STUDENT_FK_COLUMN_NAME + ", "
            + PREVIOUSLY_RENTED_COLUMN_NAME + ") VALUES (?, ?, ?, ?, ?)");

        getStudentInstrumentRentedStmt = connection.prepareStatement("SELECT  COUNT(" + RENTED_INSTRUMENT_PK_COLUMN_NAME
            + ") FROM " + RENTED_INSTRUMENT_TABLE_NAME + " WHERE " + RENTED_STUDENT_FK_COLUMN_NAME + " = ?");

        findAllRentableInstrumentsStmt = connection.prepareStatement("SELECT * FROM " +INSTRUMENT_TABLE_NAME + " WHERE is_avalible  = true AND type=?");
        rentalIDStmt = connection.prepareStatement("SELECT COUNT(*) FROM " + RENTED_INSTRUMENT_TABLE_NAME);

    }
    public void terminateRental(int instrumentID)  throws SchoolDBException{
        String failureMsg = "Could not terminate the instrument with id: " + instrumentID;
        try {
            terminateRentalStmt.setInt(1, instrumentID);
            int updatedRows = terminateRentalStmt.executeUpdate();

            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }
            updateRentalInformation.setBoolean(1, false);

            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }

            connection.commit();

        } catch (SQLException e) {
            handleException(failureMsg, e);
        }
        
    }

    public void rentInstrument(int studentID,int instrumentID)  throws SchoolDBException{
        String failureMsg = "Could not rent the instrument with id: " + instrumentID;
        try {
            rentInstrumentStmt.setInt(1, instrumentID);
            int updatedRows = rentInstrumentStmt.executeUpdate();

            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }
            ResultSet rentalID = rentalIDStmt.executeQuery();

            newRentalStmt.setInt(1,rentalID.getInt("count"));
            newRentalStmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            newRentalStmt.setInt(3, instrumentID);
            newRentalStmt.setInt(4, studentID);
            newRentalStmt.setBoolean(5, false);
            updatedRows = newRentalStmt.executeUpdate();

            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }

            connection.commit();

        } catch (SQLException e) {
            handleException(failureMsg, e);
        }
        
    }

    public List<Instrument> findAllRentableInstruments(String type) throws SchoolDBException{
        String failureMsg = "Could not retrieve instruments";
        List<Instrument> instruments = new ArrayList<>();
        try {
            findAllRentableInstrumentsStmt.setString(1, type);
        } catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        try(ResultSet result = findAllRentableInstrumentsStmt.executeQuery()){
        while(result.next()){
            instruments.add(new Instrument(result.getInt(INSTRUMENT_PK_COLUMN_NAME),
            result.getString(TYPE_COLUMN_NAME),
            result.getInt(PRICE_COLUMN_NAME),
            result.getString(BRAND_COLUMN_NAME),
            result.getBoolean(IS_AVALIBLE_COLUMN_NAME)));
        }
        connection.commit();
        } catch(SQLException sqle){
            handleException(failureMsg, sqle);
    }
        return instruments;
    }

    public int getStudentRentedInstrumentAmount(int studentID) throws SchoolDBException{
        String failureMsg = "Could not retrieve instruments";
        try {
            getStudentInstrumentRentedStmt.setInt(1, studentID);
        } catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        try{
        ResultSet result = getStudentInstrumentRentedStmt.executeQuery();
        connection.commit();
        return result.getInt(1);
        } catch(SQLException sqle){
            handleException(failureMsg, sqle);
    }
        return 0;
    }

    private void connectToSchoolDB() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soundgoodmusicschool",
                                                 "postgres", "admin");

        connection.setAutoCommit(false);
    }

    /**
    * Commits the current transaction.
    * 
    * @throws SchoolDBException If unable to commit the current transaction.
    */
   public void commit() throws SchoolDBException {
       try {
           connection.commit();
       } catch (SQLException e) {
           handleException("Failed to commit", e);
       }
   }

   private void handleException(String failureMsg, Exception cause) throws SchoolDBException {
    String completeFailureMsg = failureMsg;
    try {
        connection.rollback();
    } catch (SQLException rollbackExc) {
        completeFailureMsg = completeFailureMsg + 
        ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
    }

    if (cause != null) {
        throw new SchoolDBException(failureMsg, cause);
    } else {
        throw new SchoolDBException(failureMsg);
    }
    }

}