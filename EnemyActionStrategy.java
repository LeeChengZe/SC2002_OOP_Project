// to choose which action to perform for an enemy, allowing for different strategies to be implemented for different types of enemies.
public interface EnemyActionStrategy{
    Action decide(Enemy enemy);
}
