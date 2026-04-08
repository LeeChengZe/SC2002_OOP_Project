public class AssassinSkillAction implements Action {
    private static final int SMOKE_VEIL_DURATION = 2;
    private static final int SKILL_COOLDOWN = 3;

    private final boolean consumeCooldown;

    public AssassinSkillAction(boolean consumeCooldown) {
        this.consumeCooldown = consumeCooldown;
    }

    @Override
    public String getName() {
        return "Shadow Veil";
    }

    @Override
    public void execute(Combatant actor, BattleEngine engine) {
        // Remove any existing smoke bomb / shadow veil effect before re-applying
        if (actor.hasEffect(SmokeBombEffect.class)) {
            engine.getBattleLog().addMessage(actor.getName() + "'s Shadow Veil is refreshed.");
        }

        actor.addStatusEffect(new SmokeBombEffect(SMOKE_VEIL_DURATION), engine);

        engine.getBattleLog().addMessage(
            actor.getName() + " vanishes into the shadows with Shadow Veil! "
            + "Enemy attacks deal 0 damage for this turn and the next " + SMOKE_VEIL_DURATION + " turns."
        );

        if (consumeCooldown) {
            actor.setSpecialSkillCooldown(SKILL_COOLDOWN);
        }
    }
}
