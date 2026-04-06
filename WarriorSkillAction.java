public class WarriorSkillAction implements Action {
    private final Enemy target;
    private final boolean consumeCooldown;

    public WarriorSkillAction(Enemy target, boolean consumeCooldown) {
        this.target = target;
        this.consumeCooldown = consumeCooldown;
    }

    @Override
    public String getName() {
        return "Shield Bash";
    }

    @Override
    public void execute(Combatant actor, BattleEngine engine) {
        if (target == null || !target.isAlive()) {
            engine.getBattleLog().addMessage(actor.getName() + " has no valid target for Shield Bash.");
            return;
        }

        int damage = actor.calculateDamageAgainst(target);
        target.takeDamage(damage);

        if (target.isAlive() && !target.hasEffect(StunEffect.class)) {
            target.addStatusEffect(new StunEffect(2), engine);
        }

        if (consumeCooldown) {
            actor.setSpecialSkillCooldown(3);
        }

        engine.getBattleLog().addMessage(
            actor.getName() + " uses Shield Bash on " + target.getName()
            + " for " + damage + " damage and stuns the target for the current turn and the next turn."
        );

        if (!target.isAlive()) {
            target.clearStatusEffects(engine);
            engine.getBattleLog().addMessage(target.getName() + " is eliminated.");
        }
    }
}