import java.util.ArrayList;
import java.util.List;

public class WizardSkillAction implements Action {
    private final boolean consumeCooldown;

    public WizardSkillAction(boolean consumeCooldown) {
        this.consumeCooldown = consumeCooldown;
    }

    @Override
    public String getName() {
        return "Arcane Blast";
    }

    @Override
    public void execute(Combatant actor, BattleEngine engine) {
        List<Enemy> targets = new ArrayList<Enemy>(engine.getAliveEnemies());
        if (targets.isEmpty()) {
            engine.getBattleLog().addMessage("There are no enemies to hit.");
            return;
        }

        int defeats = 0;
        for (Enemy enemy : targets) {
            int damage = actor.calculateDamageAgainst(enemy);
            enemy.takeDamage(damage);
            engine.getBattleLog().addMessage(actor.getName() + " hits " + enemy.getName() + " with Arcane Blast for "
                    + damage + " damage. " + enemy.getName() + " HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp());
            if (!enemy.isAlive()) {
                defeats++;
                engine.getBattleLog().addMessage(enemy.getName() + " is eliminated.");
            }
        }

        if (actor instanceof Wizard && defeats > 0) {
            Wizard wizard = (Wizard) actor;
            int bonus = defeats * 10;
            wizard.increaseAttack(bonus);
            engine.getBattleLog().addMessage(wizard.getName() + " gains +" + bonus + " ATK from Arcane Blast.");
        }

        if (consumeCooldown) {
            actor.setSpecialSkillCooldown(3);
        }
    }
}
