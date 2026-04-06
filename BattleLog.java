import java.util.ArrayList;
import java.util.List;

public class BattleLog {
    private final List<String> messages;

    /*
    * Constructor - Create an Array List to store the message
    */
    public BattleLog() {
        this.messages = new ArrayList<>();
    }
    /*
    * Function to add the message into the list
    */
    public void addMessage(String message) {
        messages.add(message);
    }

    public void clearMessages() {
        messages.clear();
    }
    /*
    * Function to return the list of stored message
    */
    public List<String> getMessages() {
        return messages;
    }
}