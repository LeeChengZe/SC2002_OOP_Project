import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * GameCLI handles:
 * 
 *   1. Displaying the main menu and collecting player/item/level selections at game start
 *   2. Prompting the player to choose an action each turn during combat
 *   3. Displaying round summaries (actions taken, player and enemy statuses)
 *   4. Displaying the final game result (victory or defeat)
 *   5. saves the last game's settings to support the "Replay with same settings" feature
 */
public class GameCLI {
    private final Scanner scanner;

    // Settings from the last game, used for "Replay with same settings"
    private int lastPlayerChoice = -1;
    private int[] lastItemChoices = null;
    private Level lastLevel = null;

    // Constructor
    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    // main loop of the game
    public void start() {
        boolean playAgain = true;
        boolean useSameSettings = false;
        while (playAgain) { //continue game until condition to end hits
            System.out.println("====== Turn-Based Combat Arena ======");

            Player player;
            Level level;

            if (useSameSettings && lastPlayerChoice != -1) {
                // Rebuild player and items from last game's choices
                player = createPlayer(lastPlayerChoice);
                for (int itemChoice : lastItemChoices) {
                    player.getInventory().addItem(createItem(itemChoice));
                }
                level = lastLevel;
                System.out.println("Replaying with same settings...");
                showSetupSummary(player, level);
            } else {
                player = choosePlayer();
                chooseItems(player);
                level = chooseLevel();
                showSetupSummary(player, level);
            }

            BattleEngine engine = new BattleEngine(player, new LevelManager(level), new SpeedTurnOrderStrategy(), this);
            engine.startBattle();

            int replayChoice = promptReplay();
            playAgain = replayChoice != 3;
            useSameSettings = replayChoice == 2;
        }
        System.out.println("Thanks for Playing, See you soon!");
    }

    // Prompts player to select character class and return chosen player
    private Player choosePlayer() {
        System.out.println("===== Choose your player =====");
        System.out.println("1. Warrior (HP: 260, ATK: 40, DEF: 20, SPD: 30 \n" +
                "Special Skill: Shield Bash - Deal Basic Attack damage to selected enemy. Selected enemy is unable to take actions for the current turn and the next turn.)");
        System.out.println("2. Wizard (HP: 200, ATK: 50, DEF: 10, SPD: 20 \n" +
                "Special Skill: Arcane Blast Effect - Deal Basic Attack damage to all enemies currently in combat. Each enemy defeated by Arcane Blast adds 10 to the Wizard's Attack, lasting until end of the level.)");
        System.out.println("3. Assassin (HP: 220, ATK: 45, DEF: 15, SPD: 40 \n" +
                "Special Skill: Shadow Veil - Become untargetable; enemy attacks deal 0 damage for the current turn and the next turn.)");

        lastPlayerChoice = readIntInRange(1, 3);
        return createPlayer(lastPlayerChoice);
    }

    //Instantiates a player based on user choice
    private Player createPlayer(int choice) {
        switch (choice) {
            case 1: return new Warrior();
            case 2: return new Wizard();
            default: return new Assassin();
        }
    }

    // Prompt user to choose 2 items and adds them to player inventory
    // (Duplicates are allowed)
    private void chooseItems(Player player) {
        System.out.println("Choose 2 single-use items. Duplicates are allowed.");
        lastItemChoices = new int[2];
        for (int i = 1; i <= 2; i++) {
            System.out.println("Pick item " + i + ":");
            System.out.println("1. Potion (Heal 100HP)");
            System.out.println("2. Power Stone (Free extra use of special skill. No change to the cooldown timer)");
            System.out.println("3. Smoke Bomb (Enemy attacks deal 0 damage for current turn and next turn)");
            int choice = readIntInRange(1, 3);
            lastItemChoices[i - 1] = choice;
            player.getInventory().addItem(createItem(choice));
        }
    }

    // Instantiates and returns an item based on user choice
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

    // Prompts player to choose difficulty level and saves choice
    private Level chooseLevel() {
        System.out.println("Choose difficulty level:");
        System.out.println("1. Easy    - Initial Spawn: 3 Goblins");
        System.out.println("2. Medium  - Initial Spawn: 1 Goblin, 1 Wolf | Backup: 2 Wolves");
        System.out.println("3. Hard    - Initial Spawn: 2 Goblins | Backup: 1 Goblin, 2 Wolves");
        int choice = readIntInRange(1, 3);
        switch (choice) {
            case 1:
                lastLevel = Level.EASY;
                break;
            case 2:
                lastLevel = Level.MEDIUM;
                break;
            case 3:
                lastLevel = Level.HARD;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        return lastLevel;
    }

    // prints a summary of the player's chosen class, stats, special skills
    // and level before the battle starts
    private void showSetupSummary(Player player, Level level) {
        System.out.println("\nSelected Player: " + player.getName());
        System.out.println("HP: " + player.getMaxHp() + " | ATK: " + player.getAttack() + " | DEF: " + player.getDefense() + " | SPD: " + player.getSpeed());
        System.out.println("Special Skill: " + player.getSpecialSkillName());
        System.out.println("Items:");
        for (Item item : player.getInventory().getItems()) {
            System.out.println("- " + item.getName());
        }
        System.out.println("Level: " + level);
        System.out.println();
    }

    // Prompt player to choose their desired action
    public Action promptAction(Player player, BattleEngine engine) {
        while (true) {
            System.out.println("\nChoose action for " + player.getName() + " (Round " + engine.getRoundNumber() + "): ");
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
                    Combatant target = needsEnemyTarget(item, player) ? selectEnemyTarget(engine.getAliveEnemies()) : player;
                    return new UseItemAction(item, target); // instantiate 
                case 4:
                    if (!player.isSpecialSkillReady()) {
                        System.out.println("Special skill is on cooldown.");
                        continue;
                    }
                    Combatant skillTarget = player instanceof Warrior ? selectEnemyTarget(engine.getAliveEnemies()) : null;
                    return player.createSpecialSkillAction(engine, skillTarget, true);
                default:
                    break;
            }
        }
    }

    // check if chosen item requires enemy target 
    private boolean needsEnemyTarget(Item item, Player player) {
        return item instanceof PowerStone && player instanceof Warrior;
    }


    // Display list of alive enemies and prompt player to select one target
    private Enemy selectEnemyTarget(List<Enemy> enemies) {
        System.out.println("Choose target:");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP " + enemy.getCurrentHp() + "/" + enemy.getMaxHp() + ")");
        }
        int choice = readIntInRange(1, enemies.size());
        return enemies.get(choice - 1);
    }


    //Prints summary of each round after all actions ended
    public void displayRoundOutput(BattleEngine engine) {
        System.out.println("\n========== Round " + engine.getRoundNumber() + "! ==========");

        System.out.println("\nActions:");
        for (String message : engine.getBattleLog().getMessages()) {
            System.out.println(message);
        }
        engine.getBattleLog().clearMessages(); 

        Player player = engine.getPlayer();
        System.out.println("\nPlayer Status:");
        System.out.println("Player: " + player.getName() +
                " | HP " + player.getCurrentHp() + "/" + player.getMaxHp() +
                " | ATK " + player.getAttack() +
                " | DEF " + player.getDefense() +
                " | SPD " + player.getSpeed() +
                " | Cooldown " + player.getSpecialSkillCooldown() +
                " | Status " + player.getStatusSummary());

        if (!player.getInventory().isEmpty()) {
            System.out.print("Inventory: ");
            for (Item item : player.getInventory().getItems()) {
                System.out.print(item.getName() + "  ");
            }
            System.out.println();
        }

        if (engine.isSmokeBombActiveAgainstEnemies()) {
            System.out.println("Smoke Bomb active for " + engine.getSmokeBombTurnsRemaining() + " more turn(s).");
        }

        System.out.println("\nEnemies:");
        for (Enemy enemy : engine.getAliveEnemies()) {
            System.out.println("- " + enemy.getName() +
                    " | HP " + enemy.getCurrentHp() + "/" + enemy.getMaxHp() +
                    " | ATK " + enemy.getAttack() +
                    " | DEF " + enemy.getDefense() +
                    " | SPD " + enemy.getSpeed() +
                    " | Status " + enemy.getStatusSummary() +
                    " | Alive");
        }
    }


    // Displays game result at the end of the battle
    public void displayGameResult(BattleEngine engine) {
        if (!engine.getBattleLog().getMessages().isEmpty()) {
            displayRoundOutput(engine);
        }

        System.out.println("\n=== Game Complete ===");
        if (engine.isPlayerVictorious()) {
            System.out.println("Congratulations, you have defeated all your enemies.");
            System.out.println("Statistics: Remaining HP: " + engine.getPlayer().getCurrentHp() +
                    " | Total Rounds: " + engine.getRoundNumber());
        } else {
            System.out.println("Defeated. Don't give up, try again!");
            System.out.println("Statistics: Enemies remaining: " + engine.getAliveEnemies().size() +
                    " | Total Rounds Survived: " + engine.getRoundNumber());
        }
    }

    // Prompt player to choose what to do after game ends
    private int promptReplay() {
        System.out.println("What would you like to do next?");
        System.out.println("1. Replay with new settings");
        System.out.println("2. Replay with same settings"); //additional feature :)
        System.out.println("3. Exit");
        return readIntInRange(1, 3);
    }


    // Displays the player's current inventory 
    // and prompts them to select an item to use.
    private Item selectItem(List<Item> items) {
        System.out.println("Choose item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
        int choice = readIntInRange(1, items.size());
        return items.get(choice - 1);
    }


    // A safety measure for when user inputs a invalid entry 
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