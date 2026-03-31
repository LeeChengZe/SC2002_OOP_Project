import java.util.ArrayList;
import java.util.List;

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

    public void startBattle() {


    private void processRound() {


    private void decrementRoundBasedEffects() {


    private void handleBackupSpawnIfNeeded() {


    public boolean isBattleOver() {


    public boolean isPlayerVictorious() {

    public List<Enemy> getAliveEnemies() {


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
