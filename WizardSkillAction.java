public class WizardSkillAction implements Action {
    public void execute(Combatant actor, List<Combatant> enemies) {
        for (Combatant e : enemies) {
            int damage = Math.max(0, actor.attack - e.defense);
            e.takeDamage(damage);

            if (!e.isAlive()) {
                actor.increaseAttack(10);
            }
        }
        actor.setCooldown(3);
    }
}