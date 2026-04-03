import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
<<<<<<< HEAD
    private Level selectedLevel;
    private List<Enemy> initialEnemies;
    private List<Enemy> backupEnemies;
=======
    private final Level selectedLevel;
    private final List<Enemy> initialEnemies;
    private final List<Enemy> backupEnemies;
>>>>>>> FixedError-Cz

    public LevelManager(Level selectedLevel) {
        this.selectedLevel = selectedLevel;
        this.initialEnemies = createInitialEnemies();
        this.backupEnemies = createBackupEnemies();
    }

    public Level getSelectedLevel() {
        return selectedLevel;
    }

    public List<Enemy> getInitialEnemies() {
<<<<<<< HEAD
        return initialEnemies;
=======
        return new ArrayList<Enemy>(initialEnemies);
>>>>>>> FixedError-Cz
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
<<<<<<< HEAD
        List<Enemy> enemies = new ArrayList<>();
=======
        List<Enemy> enemies = new ArrayList<>(); 
>>>>>>> FixedError-Cz

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
<<<<<<< HEAD
        List<Enemy> enemies = new ArrayList<>();
=======
        List<Enemy> enemies = new ArrayList<Enemy>();
>>>>>>> FixedError-Cz
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

