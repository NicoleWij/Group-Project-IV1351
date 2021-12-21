package java.se.kth.iv1351.soundgoodmusicschool.view;

import java.util.List;
import java.util.Scanner;

import java.se.kth.iv1351.soundgoodmusicschool.controller.Controller;
import java.se.kth.iv1351.soundgoodmusicschool.model.InstrumentDTO;

/**
 * Reads and interprets user commands. This command interpreter is blocking, the user
 * interface does not react to user input while a command is being executed.
 */
public class BlockingInterpreter {
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private Controller ctrl;
    private boolean keepReceivingCmds = false;

    /**
     * Creates a new instance that will use the specified controller for all operations.
     * 
     * @param ctrl The controller used by this instance.
     */
    public BlockingInterpreter(Controller ctrl) {
        this.ctrl = ctrl;
    }

    /**
     * Stops the commend interpreter.
     */
    public void stop() {
        keepReceivingCmds = false;
    }

    /**
     * Interprets and performs user commands. This method will not return until the
     * UI has been stopped. The UI is stopped either when the user gives the
     * "quit" command, or when the method <code>stop()</code> is called.
     * 
     * cmdLine.getParameter(2) = instrumentType, 
     * cmdLine.getParameter(1) = studentID, 
     * cmdLine.getParameter(0) = instrumentID
     */
    public void handleCmds() {
        keepReceivingCmds = true;
        while (keepReceivingCmds) {
            try {
                CmdLine cmdLine = new CmdLine(readNextLine());
                switch (cmdLine.getCmd()) {
                    case HELP:
                        for (Command command : Command.values()) {
                            if (command == Command.ILLEGAL_COMMAND) {
                                continue;
                            }
                            System.out.println(command.toString().toLowerCase());
                        }
                        break;
                    case QUIT:
                        keepReceivingCmds = false;
                        break;
                    case RENT:
                        ctrl.rentInstrument(Integer.parseInt(cmdLine.getParameter(1)), Integer.parseInt(cmdLine.getParameter(0)));
                        break;
                    case TERMINATE:
                        ctrl.terminateRental(Integer.parseInt(cmdLine.getParameter(0)));
                        break;
                    case LIST:
                        List<? extends InstrumentDTO> instruments = null;
                        if (cmdLine.getParameter(0).equals("")) {
                            System.out.println("illegal command. Please enter command:");
                        } else {
                            instruments = ctrl.getAllRentableInstruments(cmdLine.getParameter(2));
                        }
                        for (InstrumentDTO instrument : instruments) {
                            System.out.println("Instrument ID: " + instrument.getInstrumentID() + ", "
                                             + "brand: " + instrument.getBrand() + ", "
                                             + "price: " + instrument.getPrice());
                        }
                        break;
                    default:
                        System.out.println("illegal command");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
