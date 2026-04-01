import java.util.*;

public abstract class Enemy extends Combatant {
    public Enemy(String name, int maxHp, int attack, int defense, int speed) {
        super(name, maxHp, attack, defense, speed);
    }
    private EnemyActionStrategy enemyActionStrategy;
    public abstract Action decideAction();
    public abstract void useSpecialSkill(Combatant[] targets);
}
