// This class implements the EnemyActionStrategy interface and defines a strategy where the enemy will always choose to perform a basic attack action. The chooseAction method returns a new instance of BasicAttackAction, which can be executed by the enemy during combat.
public class BasicAttackOnlyStrategy implements EnemyActionStrategy {
    public Action chooseAction(Enemy enemy) {
        return new BasicAttackAction();
    }
}