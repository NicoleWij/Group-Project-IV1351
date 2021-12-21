package java.se.kth.iv1351.soundgoodmusicschool.startup;
import java.se.kth.iv1351.soundgoodmusicschool.view.BlockingInterpreter;
import java.se.kth.iv1351.soundgoodmusicschool.controller.Controller;

/**
 * Starts the SoundGood Client.
 */
public class Main {
    public static void main(String[] args) {
        try {
            new BlockingInterpreter(new Controller()).handleCmds();
        } catch(SoundGoodDBException sdbe) {
            System.out.println("Could not connect to Sound Good.");
            sdbe.printStackTrace();
        }
    }
}