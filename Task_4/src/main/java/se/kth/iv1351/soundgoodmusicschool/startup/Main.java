package main.java.se.kth.iv1351.soundgoodmusicschool.startup;
import main.java.se.kth.iv1351.soundgoodmusicschool.view.BlockingInterpreter;
import main.java.se.kth.iv1351.soundgoodmusicschool.controller.Controller;
import main.java.se.kth.iv1351.soundgoodmusicschool.integration.SchoolDBException;

/**
 * Starts the School Client.
 */
public class Main {
    public static void main(String[] args) {
        try {
            new BlockingInterpreter(new Controller()).handleCmds();
        } catch(SchoolDBException sdbe) {
            System.out.println("Could not connect to School Database.");
            sdbe.printStackTrace();
        }
    }
}