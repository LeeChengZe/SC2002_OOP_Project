public class BasicAttackOnlyStrategy implements EnemyActionStrategy {
    public Action chooseAction(Enemy enemy) {
        return new BasicAttackAction();
    }
}