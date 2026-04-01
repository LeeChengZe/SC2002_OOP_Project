public class BasicAttackAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        int damage = Math.max(0, actor.attack - target.defense);
        target.takeDamage(damage);
    }
}