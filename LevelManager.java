import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private Level selectedLevel;
    private List<Enemy> initialEnemies;
    private List<Enemy> backupEnemies;

    public LevelManager(Level selectedLevel) {
        this.selectedLevel = selectedLevel;
        this.initialEnemies = createInitialEnemies();
        this.backupEnemies = createBackupEnemies();
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public List<Enemy> getInitialEnemies() {
        return initialEnemies;
    }

    public List<Enemy> getBackupEnemies() {
        return backupEnemies;
    }

    public boolean hasBackupEnemies() {
        return !backupEnemies.isEmpty();
    }

    public List<Enemy> spawnbackupEnemies() {
        List<Enemy> spawned = new ArrayList<>(backupEnemies);
        backupEnemies.clear();
        return spawned;
    }

    private List<Enemy> createInitialEnemies() {
        List<Enemy> enemies = new ArrayList<>();

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

    private List<Enemy> createBackupEnemies() {
        List<Enemy> enemies = new ArrayList<>();
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
