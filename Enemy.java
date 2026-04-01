// the Enemy class is an abstract class that extends Combatant and represents the enemies in the game. It has an EnemyActionStrategy to determine which action to perform and abstract methods for deciding actions and using special skills, which will be implemented by specific enemy types.
public abstract class Enemy extends Combatant {
    public Enemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }
    private EnemyActionStrategy enemyActionStrategy;
    public abstract Action decideAction();
    public abstract void useSpecialSkill(Combatant[] targets);
}

/