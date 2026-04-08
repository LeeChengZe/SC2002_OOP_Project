public class AssassinSkillAction implements Action {
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
        actor.addStatusEffect(new ShadowVeilEffect(), engine);

        if (consumeCooldown) {
            actor.setSpecialSkillCooldown(SKILL_COOLDOWN);
        }
    }
}
