import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private level selectedLevel;
    private List<Enemy> initialEnemies;
    private list<Enemy> backupEnemies;
    
    public levelManager(level selectedLevel) {
        this.selectedLevel = selectedLevel;
        this.initialEnemies = createInitialEnemies();
        this.backupEnemies = createBackupEnemies();
    }

    public level getSelectedLevel() {
        return selectedLevel;
    }

    public List<Enemy> getInitialEnemies() {
        return initialEnemeies;
    }


    public List<Enemy> getBackupEnemies() {
        return backupEnemies;
    }

    public boolean hasBackupEnemies() {
        return !backupEnemies.isEmpty();
    }

    public List<Enemy> spawnbackupEnemies() {
        List<Enemy> spawned = new Array<>(backupEnemies); //?
        backupEnemies.clear();
        return spawned;
    }

    private List<Enemy> createInitialEnemies() {
        List<enemy> enemies = new ArrayList<>(); //?

        switch (selectedLevel) {
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
        List<Enemy> enemies = new ArrayList<>(); //?
        switch (selectedLevel) {
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
