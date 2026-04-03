import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;

public class BattleEngine {
    private final Player player;
    private final LevelManager levelManager;
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameCLI gameCLI;
    private final BattleLog battleLog;
    private final List<Enemy> enemies;
    private int roundNumber;
    private int smokeBombTurnsRemaining;

    /*
        Constructor to initialise the battle engine
    */
    public BattleEngine(Player player, LevelManager levelManager, TurnOrderStrategy turnOrderStrategy, GameCLI gameCLI) {
        this.player = player;
        this.levelManager = levelManager;
        this.turnOrderStrategy = turnOrderStrategy;
        this.gameCLI = gameCLI;
        this.battleLog = new BattleLog();
        this.enemies = new ArrayList<>(levelManager.getInitialEnemies());
        this.roundNumber = 1;
        this.smokeBombTurnsRemaining = 0;
    }

    /*
        Controls game loop until win or lose
    */
    public void startBattle() {
        battleLog.addMessage("Start Battle");
        while (!isBattleOver()) {
            battleLog.addMessage("\n=== Round " + roundNumber + "! ===");
            gameCLI.displayBattleStatus(this);
            processRound();
            handleBackupSpawnIfNeeded();
            decrementRoundBasedEffects();
            roundNumber++;
        }
        gameCLI.displayGameResult(this);
    }

    /*
        Processes one round (1. turn order 2. execute actions)
    */
    private void processRound() {
        List<Combatant> activeCombatants = new ArrayList<>();
        if (player.isAlive()) {
            activeCombatants.add(player);
        }
        activeCombatants.addAll(getAliveEnemies());

        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(activeCombatants);
        // lops through every combatant in turn order
        for (Combatant combatant : turnOrder) { 
            if (isBattleOver() || !combatant.isAlive()) {
                continue;
            }

            combatant.processStatusEffects(this);
            if (!combatant.isAlive()) {
                continue;
            }

            if (!combatant.canAct()) {
                combatant.onTurnTaken();
                continue;
            }

            Action action;
            if (combatant instanceof Player) {
                action = ((Player) combatant).getPlayerAction(gameCLI, this); //?
            } else {
                action = ((Enemy) combatant).chooseAction(player); //?
            }

            if (action != null) {
                action.execute(combatant, this);
            }
            combatant.onTurnTaken();
            displayAliveOrEliminatedStatus();
        }
    }

    /*
        Updates round based variables (e.g. smoke bomb)
    */
    private void decrementRoundBasedEffects() {
        if (smokeBombTurnsRemaining > 0) {
            smokeBombTurnsRemaining--;
        }

    /*
        Display alive or eliminated status (where?)
    */
    private void displayAliveOrEliminatedStatus() {
        battleLog.addMessage(player.getName() + ": " + (player.isAlive() ? "Alive" : "Eliminated"));
        for (Enemy enemy : enemies) { 
            battleLog.addMessage(enemy.getName() + ": " + (enemy.isAlive() ? "Alive" : "Eliminated"));
        }
    }
    /*
        Spawns backup enemies
    */
    private void handleBackupSpawnIfNeeded() {
        if (getAliveEnemies().isEmpty() && levelManager.hasBackupEnemies()) {
            List<Enemy> spawned = levelManager.spawnBackupEnemies();
            enemies.addAll(spawned);
            battleLog.addMessage("Backup Spawn triggered!");
            for (Enemy enemy : spawned) { 
                battleLog.addMessage(enemy.getName() + " enters the battle.");
            }
        }
    }
    /*
    Helper: Check if battle over
    */
    public boolean isBattleOver() {
        return !player.isAlive() || getAliveEnemies().isEmpty() && !levelManager.hasBackupEnemies();
    }


    /*
    Helper: Check if player won
    */
    public boolean isPlayerVictorious() {
        return player.isAlive() && getAliveEnemies().isEmpty() && !levelManager.hasBackupEnemies();
    }

    /*
    Returns a list of currently alive enemies
    */
    public List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy enemy : enemies) { 
            if (enemy.isAlive()) {
                alive.add(enemy);
            }
        }
        return alive;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public BattleLog getBattleLog() {
        return battleLog;
    }

    public boolean isSmokeBombActiveAgainstEnemies() {
        return smokeBombTurnsRemaining > 0;
    }

    public void setSmokeBombTurnsRemaining(int turns) {
        this.smokeBombTurnsRemaining = turns;
    }

    public int getSmokeBombTurnsRemaining() {
        return smokeBombTurnsRemaining;
    }
}
