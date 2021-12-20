package java.se.kth.iv1351.soundgoodmusicschool.startup;
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