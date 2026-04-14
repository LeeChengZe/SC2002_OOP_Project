import java.util.ArrayList;
import java.util.List;

/*
*   Manages enemy spawning for a selected level, including initial and backup waves
*/
public class LevelManager {
    private final Level selectedLevel;
    private final List<Enemy> initialEnemies;
    private final List<Enemy> backupEnemies;

    /**
    *   @param selectedLevel The level that determines which enemies are spawned
    */
    public LevelManager(Level selectedLevel) {
        this.selectedLevel = selectedLevel;
        this.initialEnemies = createInitialEnemies();
        this.backupEnemies = createBackupEnemies();
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public List<Enemy> getInitialEnemies() {
        return new ArrayList<Enemy>(initialEnemies);
    }

    public List<Enemy> getBackupEnemies() {
        return backupEnemies;
    }

    public boolean hasBackupEnemies() {
        return !backupEnemies.isEmpty();
    }

    public List<Enemy> spawnBackupEnemies() {
        List<Enemy> spawned = new ArrayList<Enemy>(backupEnemies);
        backupEnemies.clear();
        return spawned;
    }

    /*
    *   Builds the initial wave of enemies based on the selected level
    */
    private List<Enemy> createInitialEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>(); 

        switch (selectedLevel) {
            case EASY:
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                enemies.add(new Goblin("Goblin C"));
                break;
            case MEDIUM:
                enemies.add(new Goblin("Goblin"));
                enemies.add(new Wolf("Wolf"));
                break;
            case HARD:
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                break;
            default:
                break;
        }
        return enemies;
    }

    /*
    *   Builds the backup wave of enemies based on the selected level
    */
    private List<Enemy> createBackupEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        switch (selectedLevel) {
            case MEDIUM:
                enemies.add(new Wolf("Wolf A"));
                enemies.add(new Wolf("Wolf B"));
                break;
            case HARD:
                enemies.add(new Goblin("Goblin C"));
                enemies.add(new Wolf("Wolf A"));
                enemies.add(new Wolf("Wolf B"));
                break;
            default:
                break;
        }
        return enemies;
    }
}

