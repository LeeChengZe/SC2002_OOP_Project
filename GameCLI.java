import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameCLI {
    private final Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    /* 
       User input checking function 
       Function takes in a min and max value and ensures that the value stays within range
       Function ensures that only integer data type are accepted 
    */
    private int readIntInRange(int min, int max) {
        while (true) {
            try {
                System.out.print("Input: ");
                int inputValue = scanner.nextInt();
                scanner.nextLine();

                if (inputValue < min || inputValue > max) {
                    System.out.println("Invalid input range. Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return inputValue;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter a number.");
                scanner.nextLine();
            }
        }   
    }
}