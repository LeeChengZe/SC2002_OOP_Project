import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameCLI {
    private final Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean playAgain = true;
        while (playAgain) {
            System.out.println("======Turn-Based Combat Arena======");
            Player player = choosePlayer();
            choseItems(player);
            Level level = chooseLevel();
            showSetupSummary(player, level);

            BattleEngine engine = new BattleEngine(player, new LevelManager(level), new SpeedTurnOrderStrategy(), this);
            engine.StartBattle();
            playAgain = promptReplay();
        }
        System.out.println("Thanks for Playing, See you soon!");
    }
    /* 
       Player selection function 
       Allows Player the to choose between 1. Warrior or 2. Wizard
    */
    private Player choosePlayer() {
        System.out.println("===== Choose your player =====");
        System.out.println("1. Warrior (HP: 260, ATK: 40, DEF: 20, SPD: 30 \n" +
                            "Special Skill: Shield Bash - Deal Basic Attack damage to selected enemy. Selected enemy is unable to take actions for the current turn and the next turn.)");
        System.out.println("2. Wizard (HP: 200, ATK: 50, DEF: 10, SPD: 20 \n" +
                            "Special Skill: Arcane Blast Effect - Deal Basic Attack damage to all enemies currently in combat. Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until end of the level.)");
        
        int choice = readIntInRange(1, 2);
        return choice == 1  new Warrior() : new Wizard();
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