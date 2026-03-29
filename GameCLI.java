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
        return choice == 1 ? new Warrior() : new Wizard();
    }

    /* 
       Item choosing function 
       Allows users to choose two items
    */
    private void chooseItems(Player player) {
        System.out.println("Choose 2 single-use items. Duplicates are allowed.");
        for (int i = 1; i <= 2; i++) {
            System.out.println("Pick item " + i + ":");
            System.out.println("1. Potion (Heal 100HP)");
            System.out.println("2. Power Stone (Free extra use of special skill. No change to the cooldown timer)");
            System.out.println("3. Smoke Bomb (Enemy attacks deal 0 damage for current turn and next turn)");
            int choice = readIntInRange(1, 3);
            player.getInventory().addItem(createItem(choice));
        }
    }

    private Item createItem(int choice) {
        switch (choice) {
            case 1:
                return new Potion();
            case 2:
                return new PowerStone();
            case 3:
                return new SmokeBomb();
            default:
                throw new IllegalArgumentException("Invalid item choice");
        }
    }

    /*
        Level selection function
        Allow users to select their level of difficulty
    */
    private Level chooseLevel() {
        System.out.println("Choose difficulty level:");
        System.out.println("1. Easy (Initial Spawn: 3 Goblins)");
        System.out.println("2. Medium (Initial Spawn: 1 Goblin, 1 Wolf. Backup Spawn: 2 Wolves)");
        System.out.println("3. Hard (Initial Spawn: 2 Goblins. Backup Spawn: 1 Goblin, 2 Wolves)");
        int choice = readIntInRange(1, 3);
        switch (choice) {
            case 1:
                return Level.EASY;
            case 2:
                return Level.MEDIUM;
            case 3:
                return Level.HARD;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }

    /*
        Player setup summary function
        Shows an overview of the player stats and items in inventory
    */
    private void showSetupSummary(Player player, Level level) {
        System.out.println("\nSelected Player: " + player.getName());
        System.out.println("HP: " + player.getMaxHp() + " | ATK: " + player.getAttack() + " | DEF: " + player.getBaseDefense() + " | SPD: " + player.getSpeed());
        System.out.println("Items:");
        for (Item item : player.getInventory().getItems()) {
            System.out.println("- " + item.getName());
        }
        System.out.println("Level: " + level);
        System.out.println();
    }

    /*
        Action selection function
        Allow user to choose their player action during gameplay
    */
    public Action promptAction(Player player, BattleEngine engine) {
        while (true) {
            System.out.println("\nChoose action for " + player.getName() + ":");
            System.out.println("1. Basic Attack");
            System.out.println("2. Defend");
            System.out.println("3. Item");
            System.out.println("4. Special Skill (" + player.getSpecialSkillName() + ") - Cooldown: " + player.getSpecialSkillCooldown());

            int choice = readIntInRange(1, 4);
            switch (choice) {
                case 1:
                    return new BasicAttackAction(selectEnemyTarget(engine.getAliveEnemies()));
                case 2:
                    return new DefendAction();
                case 3:
                    if (player.getInventory().isEmpty()) {
                        System.out.println("No items left.");
                        continue;
                    }
                    Item item = selectItem(player.getInventory().getItems());
                    Combatant target = needsTarget(item, player) ? selectEnemyTarget(engine.getAliveEnemies()) : player;
                    return new UseItemAction(item, target);
                case 4:
                    if (!player.isSpecialSkillReady()) {
                        System.out.println("Special skill is on cooldown.");
                        continue;
                    }
                    Combatant skillTarget = player instanceof Warrior ? selectEnemyTarget(engine.getAliveEnemies()) : null;
                    return new SpecialSkillAction(skillTarget);
                default:
                    break;
            }
        }
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