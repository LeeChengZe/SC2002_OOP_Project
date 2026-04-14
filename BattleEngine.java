import java.util.ArrayList;
import java.util.List;

/*
*   This class manages the overall flow of the game
*/
public class BattleEngine {
    private final Player player;
    private final LevelManager levelManager;
    private final TurnOrderStrategy turnOrderStrategy;
    private final GameCLI gameCLI;
    private final BattleLog battleLog;
    private final List<Enemy> enemies;
    private int roundNumber;
    private int smokeBombTurnsRemaining;

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
    *   Runs the battle loop until it is over, then display the final game results
    */
    public void startBattle() {
        while (!isBattleOver()) {
            processRound();

            if (isBattleOver()) {
                break;
            }

            handleBackupSpawnIfNeeded();
            processRoundEndEffects();
            gameCLI.displayRoundOutput(this);
            roundNumber++;
        }
        gameCLI.displayGameResult(this);
    }
    /*
    *   Processes a single round, build the combatant list, determine the turn order
    *   Execute each combatant action while handling status effects
    */
    private void processRound() {
        List<Combatant> activeCombatants = new ArrayList<>();
        if (player.isAlive()) {
            activeCombatants.add(player);
        }
        activeCombatants.addAll(getAliveEnemies());

        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(activeCombatants);
        for (Combatant combatant : turnOrder) {
            if (isBattleOver() || !combatant.isAlive()) {
                continue;
            }

            combatant.processTurnStartEffects(this);
            if (!combatant.isAlive() || isBattleOver()) {
                combatant.cleanupExpiredEffects(this);
                continue;
            }

            if (combatant.isStunned()) {
                combatant.cleanupExpiredEffects(this);
                continue;
            }

            Action action;
            if (combatant instanceof Player) {
                action = ((Player) combatant).getPlayerAction(gameCLI, this);
            } else {
                action = ((Enemy) combatant).chooseAction(this, player);
            }

            if (action != null) {
                action.execute(combatant, this);
            }

            combatant.reduceSpecialSkillCooldown();
            combatant.cleanupExpiredEffects(this);

            if (!player.isAlive()) {
                break;
            }

            handleBackupSpawnIfNeeded();
        }
    }

    private void processRoundEndEffects() {
        player.processRoundEndEffects(this);
        for (Enemy enemy : enemies) {
            enemy.processRoundEndEffects(this);
        }
    }

    /*
    *   Spawns backup enemies if all current enemies are defated and backup are available
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

    public boolean isBattleOver() {
        return !player.isAlive() || (getAliveEnemies().isEmpty() && !levelManager.hasBackupEnemies());
    }

    public boolean isPlayerVictorious() {
        return player.isAlive() && getAliveEnemies().isEmpty() && !levelManager.hasBackupEnemies();
    }

    /*
    *   Returns the list of enemies that are still alive
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
        return new ArrayList<>(enemies);
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