// This class implements the EnemyActionStrategy interface and defines a strategy where the enemy will always choose to perform a basic attack action.
public class BasicAttackOnlyStrategy implements EnemyActionStrategy {
    @Override
    public Action chooseAction(Enemy enemy, BattleEngine engine, Player player) {
        return new BasicAttackAction(player);
    }
}
