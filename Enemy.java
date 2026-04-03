// the Enemy class is an abstract class that extends Combatant and represents the enemies in the game.
public abstract class Enemy extends Combatant {
    private EnemyActionStrategy actionStrategy;

    protected Enemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
        this.actionStrategy = new BasicAttackOnlyStrategy();
    }

    public Action chooseAction(BattleEngine engine, Player player) {
        return actionStrategy.chooseAction(this, engine, player);
    }

    public void setActionStrategy(EnemyActionStrategy actionStrategy) {
        this.actionStrategy = actionStrategy;
    }
}
