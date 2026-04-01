// This class represents a basic attack action in a combat system. It calculates damage based on the attacker's attack stat and the target's defense stat, ensuring that damage cannot be negative. The target's takeDamage method is then called to apply the damage.
public class BasicAttackAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        int damage = Math.max(0, actor.attack - target.defense);
        target.takeDamage(damage);
    }
}
