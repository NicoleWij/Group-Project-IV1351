package main.java.se.kth.iv1351.soundgoodmusicschool.view;

/**
 * Defines all commands that can be performed by a user of the chat application.
 */
public enum Command {
    /**
     * Creates a new account.
     */
    RENT,
    /**
     * Lists all existing accounts.
     */
    LIST,
    /**
     * Deletes the specified account.
     */
    TERMINATE,
    /**
     * Lists all commands.
     */
    HELP,
    /**
     * Leave the chat application.
     */
    QUIT,
    /**
     * None of the valid commands above was specified.
     */
    ILLEGAL_COMMAND
}