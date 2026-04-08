// This class represents a basic attack action in a combat system. It calculates damage based on the attacker's attack stat and the target's defense stat, ensuring that damage cannot be negative.
public class BasicAttackAction implements Action {
    private final Combatant target;

    public BasicAttackAction(Combatant target) {
        this.target = target;
    }

    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public void execute(Combatant actor, BattleEngine engine) {
        if (target == null || !target.isAlive()) {
            engine.getBattleLog().addMessage(actor.getName() + " has no valid target.");
            return;
        }

        int damage = actor.calculateDamageAgainst(target);
        boolean blockedBySmokeBomb = actor instanceof Enemy && engine.isSmokeBombActiveAgainstEnemies();
        if (blockedBySmokeBomb) {
            damage = 0;
        }

        target.takeDamage(damage);
        if (blockedBySmokeBomb) {
            engine.getBattleLog().addMessage(actor.getName() + " attacks through Smoke Bomb, but deals 0 damage.");
        } else {
            engine.getBattleLog().addMessage(actor.getName() + " uses Basic Attack on " + target.getName()
                    + " for " + damage + " damage. " + target.getName() + " HP: "
                    + target.getCurrentHp() + "/" + target.getMaxHp());
        }

        if (!target.isAlive()) {
            engine.getBattleLog().addMessage(target.getName() + " is eliminated.");
        }
    }
}
